package com.gowine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWineRegion is a Querydsl query type for WineRegion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineRegion extends EntityPathBase<WineRegion> {

    private static final long serialVersionUID = 1090698104L;

    public static final QWineRegion wineRegion = new QWineRegion("wineRegion");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath regionName = createString("regionName");

    public QWineRegion(String variable) {
        super(WineRegion.class, forVariable(variable));
    }

    public QWineRegion(Path<? extends WineRegion> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWineRegion(PathMetadata metadata) {
        super(WineRegion.class, metadata);
    }

}

