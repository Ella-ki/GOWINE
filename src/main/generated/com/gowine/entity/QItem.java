package com.gowine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = -1455518098L;

    public static final QItem item = new QItem("item");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> acidityPercent = createNumber("acidityPercent", Integer.class);

    public final NumberPath<Double> avgRating = createNumber("avgRating", Double.class);

    public final NumberPath<Integer> bodyPercent = createNumber("bodyPercent", Integer.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath itemNm = createString("itemNm");

    public final EnumPath<com.gowine.constant.ItemSellStatus> itemSellStatus = createEnum("itemSellStatus", com.gowine.constant.ItemSellStatus.class);

    public final ListPath<Member, QMember> member = this.<Member, QMember>createList("member", Member.class, QMember.class, PathInits.DIRECT2);

    //inherited
    public final StringPath modifiedBy = _super.modifiedBy;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regTime = _super.regTime;

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final NumberPath<Integer> stockNumber = createNumber("stockNumber", Integer.class);

    public final NumberPath<Integer> sweetnessPercent = createNumber("sweetnessPercent", Integer.class);

    public final NumberPath<Integer> tanninPercent = createNumber("tanninPercent", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final StringPath vivinoRate = createString("vivinoRate");

    public final StringPath winary = createString("winary");

    public final EnumPath<com.gowine.constant.WineGrape> wineGrape = createEnum("wineGrape", com.gowine.constant.WineGrape.class);

    public final EnumPath<com.gowine.constant.WineRegion> wineRegion = createEnum("wineRegion", com.gowine.constant.WineRegion.class);

    public final EnumPath<com.gowine.constant.WineType> wineType = createEnum("wineType", com.gowine.constant.WineType.class);

    public QItem(String variable) {
        super(Item.class, forVariable(variable));
    }

    public QItem(Path<? extends Item> path) {
        super(path.getType(), path.getMetadata());
    }

    public QItem(PathMetadata metadata) {
        super(Item.class, metadata);
    }

}

