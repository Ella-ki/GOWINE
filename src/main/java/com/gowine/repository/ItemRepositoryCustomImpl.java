package com.gowine.repository;

import com.gowine.constant.WineGrape;
import com.gowine.constant.WineRegion;
import com.gowine.constant.WineType;
import com.gowine.dto.*;
import com.gowine.entity.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.gowine.constant.ItemSellStatus;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{
    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em); // JPAQueryFactory 실질적인 객체가 만들어집니다
    }

    private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellStatus){
        return searchSellStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellStatus);
    }

    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();

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
        QReview review = QReview.review;

        QueryResults<MainItemDto> results = queryFactory.select(
                new QMainItemDto(
                        item.id,
                        item.itemNm,
                        item.winary,
                        item.wineType,
                        item.wineRegion,
                        item.wineGrape,
                        item.vivinoRate,
                        itemImg.imgUrl,
                        item.price,
                        item.itemSellStatus
                    )
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .leftJoin(review).on(item.id.eq(review.id))
                .where(itemNmLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<MainItemDto> getFilteredItemPage(String wineType, String wineGrape, String wineRegion, Integer winePrice,
                                                 Double vivinoRate, String itemSellStatus, Pageable pageable) {
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QReview review = QReview.review;

        BooleanBuilder predicate = new BooleanBuilder();

        // wineType 필터 추가
        if (wineType != null) {
            predicate.and(item.wineType.eq(WineType.valueOf(wineType)));
        }

        // wineGrape 필터 추가
        if (wineGrape != null) {
            predicate.and(item.wineGrape.eq(WineGrape.valueOf(wineGrape)));
        }

        // wineRegion 필터 추가
        if (wineRegion != null) {
            predicate.and(item.wineRegion.eq(WineRegion.valueOf(wineRegion)));
        }

        // winePrice 필터 추가
        if (winePrice != null) {
            if (winePrice == 1) {
                predicate.and(item.price.lt(30000)); // 30,000원 미만
            } else if (winePrice == 2) {
                predicate.and(item.price.between(30000, 50000)); // 30,000원 이상 ~ 50,000원 미만
            } else if (winePrice == 3) {
                predicate.and(item.price.between(50000, 100000)); // 50,000원 이상 ~ 100,000원 미만
            } else if (winePrice == 4) {
                predicate.and(item.price.goe(100000)); // 100,000원 이상
            }
        }

        if (vivinoRate != null) {
            predicate.and(item.vivinoRate.goe(vivinoRate));
        }

        QueryResults<MainItemDto> results = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemNm,
                                item.winary,
                                item.wineType,
                                item.wineRegion,
                                item.wineGrape,
                                item.vivinoRate,
                                itemImg.imgUrl,
                                item.price,
                                item.itemSellStatus
                        )
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .leftJoin(review).on(item.id.eq(review.id))
                .where(predicate)
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<MainItemDto> getSearchItemPage(String keyword){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QReview review = QReview.review;

        QueryResults<MainItemDto> results = queryFactory.select(
                new QMainItemDto(
                        item.id,
                        item.itemNm,
                        item.winary,
                        item.wineType,
                        item.wineRegion,
                        item.wineGrape,
                        item.vivinoRate,
                        itemImg.imgUrl,
                        item.price,
                        item.itemSellStatus
                    )
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .leftJoin(review).on(item.id.eq(review.id))
                .where(itemNmLike(keyword))
                .orderBy(item.id.desc())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        return content;
    }

    @Override
    public Page<MainItemDto> getMainItemPage(Pageable pageable){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QReview review = QReview.review;

        QueryResults<MainItemDto> results = queryFactory.select(
                new QMainItemDto(
                        item.id,
                        item.itemNm,
                        item.winary,
                        item.wineType,
                        item.wineRegion,
                        item.wineGrape,
                        item.vivinoRate,
                        itemImg.imgUrl,
                        item.price,
                        item.itemSellStatus
                    )
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .leftJoin(review)
                .on(item.id.eq(review.id))
                .where(item.stockNumber.loe(5))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<MainItemDto> getMbtiItemPage(String mbti){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        QueryResults<MainItemDto> results = queryFactory.select(
                new QMainItemDto(
                        item.id,
                        item.itemNm,
                        item.winary,
                        item.wineType,
                        item.wineRegion,
                        item.wineGrape,
                        item.vivinoRate,
                        itemImg.imgUrl,
                        item.price,
                        item.itemSellStatus
                    )
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .where(wineGrapeLike(mbti))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(3).fetchResults();

        List<MainItemDto> content = results.getResults();
        return content;
    }

    @Override
    public Page<MainItemDto> getLikeItemPage(Member loginMember, Pageable pageable){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;
        QItemLike itemLike = QItemLike.itemLike;
        QMember member = QMember.member;

        QueryResults<MainItemDto> results = queryFactory.select(
                new QMainItemDto(
                        item.id,
                        item.itemNm,
                        item.winary,
                        item.wineType,
                        item.wineRegion,
                        item.wineGrape,
                        item.vivinoRate,
                        itemImg.imgUrl,
                        item.price,
                        item.itemSellStatus
                    )
                )
                .from(itemImg)
                .join(itemImg.item, item)
                .leftJoin(item.likes, itemLike)
                .where(itemLike.member.eq(loginMember))
                .orderBy(itemLike.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<MainItemDto> getRelatedItemPage(Long itemId, Long excludedItemId){
        QItem item = QItem.item;
        QItemImg itemImg = QItemImg.itemImg;

        Tuple itemInfo = queryFactory.select(item.wineGrape, item.wineRegion, item.wineType)
                .from(item)
                .where(item.id.eq(itemId))
                .fetchOne();

        WineGrape wineGrape = itemInfo.get(item.wineGrape);
        WineRegion wineRegion = itemInfo.get(item.wineRegion);
        WineType wineType = itemInfo.get(item.wineType);

        QueryResults<MainItemDto> results = queryFactory.select(
                new QMainItemDto(
                            item.id,
                            item.itemNm,
                            item.winary,
                            item.wineType,
                            item.wineRegion,
                            item.wineGrape,
                            item.vivinoRate,
                            itemImg.imgUrl,
                            item.price,
                            item.itemSellStatus
                    )
                )
                .from(itemImg)
                .join(itemImg.item, item)
                //.leftJoin(review).on(item.id.eq(review.id))
                .where(item.wineGrape.eq(wineGrape)
                        .or(item.wineRegion.eq(wineRegion))
                        .or(item.wineType.eq(wineType))
                        .and(item.id.ne(excludedItemId)))
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                .limit(10)
                .fetchResults();

        List<MainItemDto> content = results.getResults();
        return content;
    }

}

