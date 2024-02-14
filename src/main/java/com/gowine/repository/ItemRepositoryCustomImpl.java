package com.gowine.repository;

import com.gowine.constant.WineGrape;
import com.gowine.dto.ItemFormDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.gowine.constant.ItemSellStatus;
import com.gowine.dto.ItemSearchDto;
import com.gowine.dto.MainItemDto;
import com.gowine.dto.QMainItemDto;
import com.gowine.entity.Item;
import com.gowine.entity.QItem;
import com.gowine.entity.QItemImg;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
    private JPAQueryFactory queryFactory; // 동적쿼리 사용하기 위해 JPAQueryFactory 변수 선언

    // 생성자
    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em); // JPAQueryFactory 실질적인 객체가 만들어집니다
    }

    // BooleanExpression => 메소드화 할 수 있음. 재사용성 좋아짐 ( BooleanBuilder 대신에 씀)
    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
        // ItemSellStatus null 이면 null 리턴, 아니면 SELL,SOLD 둘 중 하나 리턴
    }

    private BooleanExpression regDtsAfter(String searchDateType){ // all, 1d, 1w, 1m, 6m
        LocalDateTime dateTime = LocalDateTime.now(); // 현재 시간을 추출해서 변수에 대입

        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)) {
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)) {
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)) {
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)) {
            dateTime = dateTime.minusMonths(6);
        }

        return QItem.item.regTime.after(dateTime);
        // dateTime 을 시간에 맞게 세팅 후 시간에 맞는 등록된 상품이 조회하도록 조건값 반환

    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemNm", searchBy)){ // 상품명
            return QItem.item.itemNm.like("%"+searchQuery+"%");
        } else if(StringUtils.equals("winary", searchBy)){ // 와이너리
            return QItem.item.winary.like("%"+searchQuery+"%");
        }

        /*
        else if(StringUtils.equals("createdBy", searchBy)) { // 작성자
            return QItem.item.createdBy.like("%"+searchQuery+"%");
        }
        */

        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        QueryResults<Item> results = queryFactory.selectFrom(QItem.item).
                where(regDtsAfter(itemSearchDto.getSearchDateType()),
                        searchSellStatusEq(itemSearchDto.getSearchSellStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery()))
                .orderBy(QItem.item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<Item> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable,total);
    }

    private BooleanExpression itemNmLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.itemNm.like("%"+searchQuery+"%");
    }

    private BooleanExpression wineGrapeLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QItem.item.wineGrape.loe(WineGrape.valueOf(searchQuery));
    }

    @Override
    public Page<MainItemDto> getListItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory.select(new QMainItemDto(item.id, item.itemNm, item.winary, itemImg.imgUrl, item.price))
                .from(itemImg).join(itemImg.item, item)
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<MainItemDto> getSearchItemPage(String keyword){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory.select(new QMainItemDto(item.id, item.itemNm, item.winary, itemImg.imgUrl, item.price))
                .from(itemImg).join(itemImg.item, item)
                .where(itemNmLike(keyword))
                .orderBy(item.id.desc()).fetchResults();
        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return content;
    }

    @Override
    public Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        // QMainItemDto @QueryProjection을 허용하면 DTO 로 바로 조회 가능
        QueryResults<MainItemDto> results = queryFactory.select(new QMainItemDto(item.id, item.itemNm,item.winary, itemImg.imgUrl, item.price))
                .from(itemImg).join(itemImg.item, item)
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize()).fetchResults();
        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<MainItemDto> getMbtiItemPage(String mbti){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        System.out.println(wineGrapeLike(mbti));

        QueryResults<MainItemDto> results = queryFactory.select(new QMainItemDto(item.id, item.itemNm, item.winary, itemImg.imgUrl, item.price))
                .from(itemImg).join(itemImg.item, item)
                .where(wineGrapeLike(mbti))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3).fetchResults();
        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return content;
    }

}

