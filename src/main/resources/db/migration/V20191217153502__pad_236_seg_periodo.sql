create table seg_periodo

(
    id BIGINT IDENTITY(1, 1)                            NOT NULL PRIMARY KEY,
    id_fornecedor BIGINT REFERENCES seg_fornecedores(id)  NOT NULL,
    data_inicio DATE                                    NOT NULL,
    data_final DATE                                     NOT NULL,
    data_retirada DATE                                  NOT NULL,
    descricao VARCHAR(50)                               NOT NULL
);