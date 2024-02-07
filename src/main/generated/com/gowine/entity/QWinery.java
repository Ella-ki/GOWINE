package com.gowine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWinery is a Querydsl query type for Winery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWinery extends EntityPathBase<Winery> {

    private static final long serialVersionUID = 1797359787L;

    public static final QWinery winery = new QWinery("winery");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath wineryName = createString("wineryName");

    public QWinery(String variable) {
        super(Winery.class, forVariable(variable));
    }

    public QWinery(Path<? extends Winery> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWinery(PathMetadata metadata) {
        super(Winery.class, metadata);
    }

}

