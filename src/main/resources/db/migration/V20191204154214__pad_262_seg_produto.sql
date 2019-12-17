create table seg_produto

(
    id BIGINT IDENTITY (1, 1)                                       NOT NULL PRIMARY KEY,
    codigo_produto VARCHAR(10)                                      NOT NULL,
    nome_produto VARCHAR(200)                                       NOT NULL,
    preco_produto FLOAT                                             NOT NULL,
    id_linha BIGINT REFERENCES seg_linha_categoria(id_linha)        NOT NULL,
    unidade_por_caixa BIGINT                                        NOT NULL,
    peso_unidade FLOAT                                              NOT NULL,
    unidade_medida VARCHAR(2)                                       NOT NULL,
    validade DATE                                                   NOT NULL
);

create unique index ix_seg_produto_01 on seg_produto (codigo_produto asc);