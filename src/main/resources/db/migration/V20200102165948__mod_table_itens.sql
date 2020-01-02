DROP TABLE pedido_item;
ALTER TABLE seg_item ADD  id_pedido BIGINT REFERENCES seg_pedido(id) NOT NULL;