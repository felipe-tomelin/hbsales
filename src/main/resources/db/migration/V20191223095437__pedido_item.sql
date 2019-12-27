CREATE TABLE pedido_item
(
    id_pedido BIGINT REFERENCES seg_pedido(id)              NOT NULL,
    id_item BIGINT REFERENCES seg_item(id)                      NOT NULL
);
