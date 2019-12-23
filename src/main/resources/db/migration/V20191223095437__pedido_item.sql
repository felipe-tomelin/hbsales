CREATE TABLE pedido_item
(
    id_pedido BIGINT REFERENCES seg_pedido(id)              NOT NULL,
    id_item BIGINT REFERENCES item(id)                      NOT NULL
);
