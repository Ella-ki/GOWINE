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

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Double> avgRating = createNumber("avgRating", Double.class);

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

    public final NumberPath<Integer> stockNum = createNumber("stockNum", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateTime = _super.updateTime;

    public final QVivinoRating vivinoRating;

    public final QWineGrape wineGrape;

    public final QWineRegion wineRegion;

    public final QWinery winery;

    public final QWineStyle wineStyle;

    public final QWineType wineType;

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vivinoRating = inits.isInitialized("vivinoRating") ? new QVivinoRating(forProperty("vivinoRating")) : null;
        this.wineGrape = inits.isInitialized("wineGrape") ? new QWineGrape(forProperty("wineGrape")) : null;
        this.wineRegion = inits.isInitialized("wineRegion") ? new QWineRegion(forProperty("wineRegion")) : null;
        this.winery = inits.isInitialized("winery") ? new QWinery(forProperty("winery")) : null;
        this.wineStyle = inits.isInitialized("wineStyle") ? new QWineStyle(forProperty("wineStyle")) : null;
        this.wineType = inits.isInitialized("wineType") ? new QWineType(forProperty("wineType")) : null;
    }

}

