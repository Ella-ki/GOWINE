package com.gowine.service;

import com.gowine.entity.ItemImg;
import com.gowine.repository.ItemImgRepository;
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
public class ItemImgService {
    @Value("${itemImgLocation}")
    private String itemImgLocation;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        System.out.println(oriImgName);

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            System.out.println(imgName);
            imgUrl = "/images/item/" + imgName; // /images/item/ => WebMvcConfig
        }

        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if(!itemImgFile.isEmpty()) {
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); // 파일 업로드
            String imgUrl = "/images/item/"+imgName;

            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

    public void deleteItemImg(Long itemImgId) throws Exception {
        ItemImg itemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);
        if (itemImg != null) {
            if (!StringUtils.isEmpty(itemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());
            }

            itemImgRepository.delete(itemImg);
        }
    }
}
