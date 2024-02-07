package com.gowine.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QWineStyle is a Querydsl query type for WineStyle
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWineStyle extends EntityPathBase<WineStyle> {

    private static final long serialVersionUID = -240523091L;

    public static final QWineStyle wineStyle = new QWineStyle("wineStyle");

    public final NumberPath<Integer> acidityPercent = createNumber("acidityPercent", Integer.class);

    public final NumberPath<Integer> bodyPercent = createNumber("bodyPercent", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> sweetnessPercent = createNumber("sweetnessPercent", Integer.class);

    public final NumberPath<Integer> tanninPercent = createNumber("tanninPercent", Integer.class);

    public QWineStyle(String variable) {
        super(WineStyle.class, forVariable(variable));
    }

    public QWineStyle(Path<? extends WineStyle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWineStyle(PathMetadata metadata) {
        super(WineStyle.class, metadata);
    }

}

