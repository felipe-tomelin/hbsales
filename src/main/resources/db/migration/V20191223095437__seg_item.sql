CREATE TABLE seg_item
(
    id BIGINT       IDENTITY(1, 1)                          NOT NULL PRIMARY KEY,
    id_produto BIGINT REFERENCES seg_produto(id)            NOT NULL,
    quantidade_produto BIGINT                               NOT NULL
);


