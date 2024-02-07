package com.gowine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWineGrape is a Querydsl query type for WineGrape
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineGrape extends EntityPathBase<WineGrape> {

    private static final long serialVersionUID = -251687865L;

    public static final QWineGrape wineGrape = new QWineGrape("wineGrape");

    public final StringPath grapeName = createString("grapeName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QWineGrape(String variable) {
        super(WineGrape.class, forVariable(variable));
    }

    public QWineGrape(Path<? extends WineGrape> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWineGrape(PathMetadata metadata) {
        super(WineGrape.class, metadata);
    }

}

