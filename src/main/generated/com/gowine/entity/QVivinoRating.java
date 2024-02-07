package com.gowine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVivinoRating is a Querydsl query type for VivinoRating
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVivinoRating extends EntityPathBase<VivinoRating> {

    private static final long serialVersionUID = 1078482367L;

    public static final QVivinoRating vivinoRating = new QVivinoRating("vivinoRating");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath rating = createString("rating");

    public QVivinoRating(String variable) {
        super(VivinoRating.class, forVariable(variable));
    }

    public QVivinoRating(Path<? extends VivinoRating> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVivinoRating(PathMetadata metadata) {
        super(VivinoRating.class, metadata);
    }

}

