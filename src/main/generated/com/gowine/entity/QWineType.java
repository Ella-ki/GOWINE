package com.gowine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWineType is a Querydsl query type for WineType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineType extends EntityPathBase<WineType> {

    private static final long serialVersionUID = 685012158L;

    public static final QWineType wineType = new QWineType("wineType");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath typeName = createString("typeName");

    public QWineType(String variable) {
        super(WineType.class, forVariable(variable));
    }

    public QWineType(Path<? extends WineType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWineType(PathMetadata metadata) {
        super(WineType.class, metadata);
    }

}

