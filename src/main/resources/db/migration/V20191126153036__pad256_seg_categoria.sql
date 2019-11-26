create table seg_categoria

(
    id BIGINT IDENTITY (1, 1)               NOT NULL,
    fornecedor_categoria VARCHAR(70)        NOT NULL,
    nome_categoria VARCHAR()                NOT NULL
);

create unique index ix_seg_categoria_01 on seg_categoria (fornecedor_categoria asc);
create unique index ix_seg_categoria_02 on seg_categoria (nome_categoria asc);