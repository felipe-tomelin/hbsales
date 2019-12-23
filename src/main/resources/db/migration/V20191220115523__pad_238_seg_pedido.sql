CREATE TABLE seg_pedido
(
    id BIGINT       IDENTITY(1, 1)                          NOT NULL PRIMARY KEY,
    id_fornecedor BIGINT REFERENCES seg_fornecedores(id)    NOT NULL,
    codigo          VARCHAR(10)                             NOT NULL,
    status          VARCHAR                                 NOT NULL,
    data_criacao    DATE                                    NOT NULL
);

CREATE UNIQUE index ix_seg_pedido_01 ON seg_pedido (codigo asc);