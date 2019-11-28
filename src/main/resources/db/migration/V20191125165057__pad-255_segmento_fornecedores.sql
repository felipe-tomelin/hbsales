create table seg_fornecedores
(
    id    BIGINT IDENTITY (1, 1)        PRIMARY KEY NOT NULL,
    razao_social VARCHAR(100)            NOT NULL,
    cnpj VARCHAR(18)                    NOT NULL,
    nome_fantasia VARCHAR(70)            NOT NULL,
    endereco VARCHAR(70)                NOT NULL,
    telefone VARCHAR(9)                 NOT NULL,
    email VARCHAR(70)                   NOT NULL

);

create unique index ix_seg_fornecedores_01 on seg_fornecedores (razao_social asc);
create unique index ix_seg_fornecedores_02 on seg_fornecedores (cnpj asc);
create unique index ix_seg_fornecedores_03 on seg_fornecedores (nome_fantasia asc);
create unique index ix_seg_fornecedores_04 on seg_fornecedores (endereco asc);
create unique index ix_seg_fornecedores_05 on seg_fornecedores (telefone asc);
create unique index ix_seg_fornecedores_06 on seg_fornecedores (email asc);

