package com.gowine.repository;

import com.gowine.dto.CartDetailDto;
import com.gowine.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    // CartItem이 item을 가지고있으니 이걸 통해서 ItemImg 가져오려고 join
    // itemid 같은지 비교

    // CartDetailDto
    // CartItem(item) <-> ItemImg(item)
    // 조건1 CartItem(CartId) == 외부 매개변수 CartId
    // 조건2 CartItem(item.id) == ItemImg(item.id)
    // 조건3 ItemImg 대표이미지
    // 정렬 기준 CartItem 등록시간 내림차순
    @Query("select new com.gowine.dto.CartDetailDto(ci.id, i.itemNm, i.price, ci.count, im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "order by ci.regTime desc")
    List<CartDetailDto> findCardDetailDtoList(Long cartId);

}
