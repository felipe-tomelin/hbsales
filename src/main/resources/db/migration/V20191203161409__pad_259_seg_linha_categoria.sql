create table seg_linha_categoria

(
    id_linha BIGINT IDENTITY (1, 1)                           NOT NULL PRIMARY KEY,
    id_categoria BIGINT REFERENCES seg_categoria(id)          NOT NULL,
    nome_linha VARCHAR(50)                                    NOT NULL,
    codigo_linha VARCHAR(10)                                  NOT NULL
);

create unique index ix_seg_linha_categoria_01 on seg_linha_categoria (nome_linha asc);
create unique index ix_seg_linha_categoria_02 on seg_linha_categoria (codigo_linha asc);