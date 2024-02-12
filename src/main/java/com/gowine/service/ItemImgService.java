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
    @Value("${itemImgLocation}") // application.properties에 itemImgLocation
    private String itemImgLocation;
    private final ItemImgRepository itemImgRepository;
    private final FileService fileService;

    // MultipartFile itemImgFile => 실제 클라이언트가 보낸 이미지 파일
    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
        String oriImgName = itemImgFile.getOriginalFilename(); // 오리지널 이미지 경로
        String imgName = "";
        String imgUrl = "";
        System.out.println(oriImgName);

        // 파일 업로드
        if(!StringUtils.isEmpty(oriImgName)) { // oriImgName 문자열로 비어있지않으면 실행
            System.out.println("******");
            // itemImgLocation => "D:/shop", oriImgName => 이미지파일.jpg, byte[] fileData => 사진 데이터
            imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
            System.out.println(imgName);
            imgUrl = "/images/item/" + imgName; // /images/item/ => WebMvcConfig
        }
        System.out.println("1111");
        // 상품 이미지 정보 저장
        // oriImgName : 상품 이미지 파일의 원래 이름
        // imgName : 실제 로컬에 저장된 상품 이미지 파일의 이름
        // imgUrl : 로컬에 저장된 상품 이미지 파일을 불러오는 경로
        // ★매개변수로 받은 itemImg 실객체 -> 이 시점에는 비어있으므로 updateItemImg로 데이터 넣고 save 해줌
        itemImg.updateItemImg(oriImgName, imgName, imgUrl);
        System.out.println("(((((");
        itemImgRepository.save(itemImg);
    }

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
        if(!itemImgFile.isEmpty()) { // 상품의 이미지를 수정한 경우 상품 이미지 업데이트
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new); // 기존 엔티티 조회

            // 기존에 등록된 상품 이미지 파일이 있는 경우 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes()); // 파일 업로드
            String imgUrl = "/images/item/"+imgName;

            // 변경된 상품 이미지 정보를 세팅
            // 상품 등록을 하는 경우에는 ItemaImgRepository.save() 로직을 호출 하지만
            // 호출을 하지않았습니다.
            // savedItemImg 엔티티는 현재 영속성 상태
            // 그래서 데이터를 변경하는 것만으로 변경을 감지 기능이 동작
            // 트랜잭션이 끝날 때 update 쿼리가 실행된다
            // ※ 영속성 상태여야 사용가능
            savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);
        }
    }

    public void deleteItemImg(Long itemImgId) throws Exception {
        // 아이템 이미지 ID를 사용하여 해당 이미지를 조회합니다.
        ItemImg itemImg = itemImgRepository.findById(itemImgId).orElseThrow(EntityNotFoundException::new);

        // 아이템 이미지가 존재하는 경우에만 삭제 작업을 수행합니다.
        if (itemImg != null) {
            // 이미지 파일을 삭제합니다.
            if (!StringUtils.isEmpty(itemImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + itemImg.getImgName());
            }

            // 아이템 이미지 엔티티를 삭제합니다.
            itemImgRepository.delete(itemImg);
        }
    }
}
