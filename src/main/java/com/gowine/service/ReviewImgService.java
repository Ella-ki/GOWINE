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
        String oriImgName = reviewImgFile.getOriginalFilename(); // 오리지널 이미지 경로
        String imgName = "";
        String imgUrl = "";
        System.out.println(oriImgName);

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)) { // oriImgName 문자열로 비어있지않으면 실행
            System.out.println("******");
            imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes());
            System.out.println(imgName);
            imgUrl = "/images/review/" + imgName; // /images/item/ => WebMvcConfig
        }
        System.out.println("1111");

        reviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        System.out.println("(((((");
        reviewImgRepository.save(reviewImg);
    }

    public void updateReviewImg(Long reviewImgId, MultipartFile reviewImgFile) throws Exception {
        if(!reviewImgFile.isEmpty()) { // 상품의 이미지를 수정한 경우 상품 이미지 업데이트
            ReviewImg savedReviewImg = reviewImgRepository.findById(reviewImgId).orElseThrow(EntityNotFoundException::new); // 기존 엔티티 조회

            // 기존에 등록된 상품 이미지 파일이 있는 경우 파일 삭제
            if(!StringUtils.isEmpty(savedReviewImg.getImgName())){
                fileService.deleteFile(reviewImgLocation+"/"+savedReviewImg.getImgName());
            }

            String oriImgName = reviewImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(reviewImgLocation, oriImgName, reviewImgFile.getBytes()); // 파일 업로드
            String imgUrl = "/images/review/"+imgName;

            savedReviewImg.updateReviewImg(oriImgName, imgName, imgUrl);
        }

    }

    public void deleteReviewImg(Long reviewImgId) throws Exception {
        // 아이템 이미지 ID를 사용하여 해당 이미지를 조회합니다.
        ReviewImg reviewImg = reviewImgRepository.findById(reviewImgId).orElseThrow(EntityNotFoundException::new);

        // 아이템 이미지가 존재하는 경우에만 삭제 작업을 수행합니다.
        if (reviewImg != null) {
            // 이미지 파일을 삭제합니다.
            if (!StringUtils.isEmpty(reviewImg.getImgName())) {
                fileService.deleteFile(reviewImgLocation + "/" + reviewImg.getImgName());
            }

            // 아이템 이미지 엔티티를 삭제합니다.
            reviewImgRepository.delete(reviewImg);
        }
    }
}
