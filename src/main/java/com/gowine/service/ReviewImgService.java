package com.gowine.service;

import com.gowine.entity.ItemImg;
import com.gowine.entity.ReviewImg;
import com.gowine.repository.ItemImgRepository;
import com.gowine.repository.ReviewImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewImgService {
    @Value("${reviewImgLocation}")
    private String reviewImgLocation;
    private final ReviewImgRepository reviewImgRepository;
    private final FileService fileService;

    public void saveReviewImg(ReviewImg reviewImg, MultipartFile reviewImgFile) throws Exception {
        String oriImgName = reviewImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        System.out.println(oriImgName);

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
            System.out.println(imgName);
            imgUrl = "/images/review/" + imgName; // /images/item/ => WebMvcConfig
        }

        reviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        reviewImgRepository.save(reviewImg);
    }

    public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception {
        if(!reviewImgFile.isEmpty()) {
            ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId).orElseThrow(EntityNotFoundException::new);
            if(!StringUtils.isEmpty(savedReviewImg.getImgName())){
                fileService.deleteFile(reviewImgLocation+"/"+savedReviewImg.getImgName());
            }

            String oriImgName = reviewImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
            String imgUrl = "/images/review/"+imgName;

            savedReviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        }
    }

    public void deleteReviewImg(Long reviewImgId) throws Exception {
        ReviewImg reviewImg = reviewImgRepository.findById(reviewImgId).orElseThrow(EntityNotFoundException::new);

        if (reviewImg != null) {
            if (!StringUtils.isEmpty(reviewImg.getImgName())) {
                fileService.deleteFile(reviewImgLocation + "/" + reviewImg.getImgName());
            }

            reviewImgRepository.delete(reviewImg);
        }
    }
}
