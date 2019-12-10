create table seg_categoria

(
    id BIGINT IDENTITY (1, 1)                                                NOT NULL PRIMARY KEY,
    id_fornecedor BIGINT REFERENCES seg_fornecedores(id)                     NOT NULL,
    nome_categoria VARCHAR(50)                                               NOT NULL,
    codigo VARCHAR(10)                                                            NOT NULL
);

create unique index ix_seg_categoria_01 on seg_categoria (id_fornecedor asc);
create unique index ix_seg_categoria_02 on seg_categoria (nome_categoria asc);
create unique index ix_seg_categoria_03 on seg_categoria (codigo asc);