CREATE DATABASE IF NOT EXISTS mecaniqa
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE mecaniqa;

CREATE TABLE IF NOT EXISTS peca (
    id                       BIGINT        NOT NULL AUTO_INCREMENT,
    nome                     VARCHAR(150)  NOT NULL,
    codigo_barras            VARCHAR(50),
    fornecedor_marca         VARCHAR(100),
    quantidade_em_estoque    INT           NOT NULL DEFAULT 0,
    preco_custo              DECIMAL(10,2),
    preco_venda              DECIMAL(10,2),
    data_cadastro            DATETIME,
    data_ultima_atualizacao  DATETIME,
    categoria                VARCHAR(20),
    tamanho                  VARCHAR(30),
    cor                      VARCHAR(30),
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS servico (
                                       id               BIGINT        NOT NULL AUTO_INCREMENT,
                                       nome_servico     VARCHAR(150)  NOT NULL,
    tempo_minutos    SMALLINT      NOT NULL,
    custo_tabelado   DECIMAL(10,2) NOT NULL,
    data_criacao     DATETIME,
    data_atualizacao DATETIME,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ordem_servico (
    id                 BIGINT        NOT NULL AUTO_INCREMENT,
    placa_veiculo      VARCHAR(10)   NOT NULL,
    descricao_problema VARCHAR(500)  NOT NULL,
    status             VARCHAR(30)   NOT NULL,
    data_abertura      DATETIME,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ordem_servico_servico (
    id               BIGINT NOT NULL AUTO_INCREMENT,
    ordem_servico_id BIGINT NOT NULL,
    servico_id       BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_oss_os      FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico (id) ON DELETE CASCADE,
    CONSTRAINT fk_oss_servico FOREIGN KEY (servico_id)       REFERENCES servico (id)       ON DELETE RESTRICT
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS ordem_servico_peca (
    id               BIGINT NOT NULL AUTO_INCREMENT,
    ordem_servico_id BIGINT NOT NULL,
    peca_id          BIGINT NOT NULL,
    quantidade       INT    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_osp_os   FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico (id) ON DELETE CASCADE,
    CONSTRAINT fk_osp_peca FOREIGN KEY (peca_id)          REFERENCES peca (id)          ON DELETE RESTRICT
    ) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pedido_peca (
    id           BIGINT      NOT NULL AUTO_INCREMENT,
    status       VARCHAR(30) NOT NULL,
    data_criacao DATETIME,
    PRIMARY KEY (id)
    ) ENGINE=InnoDB;


CREATE TABLE IF NOT EXISTS item_pedido_peca (
    id         BIGINT NOT NULL AUTO_INCREMENT,
    pedido_id  BIGINT NOT NULL,
    peca_id    BIGINT NOT NULL,
    quantidade INT    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_item_pedido FOREIGN KEY (pedido_id) REFERENCES pedido_peca (id) ON DELETE CASCADE,
    CONSTRAINT fk_item_peca   FOREIGN KEY (peca_id)   REFERENCES peca (id)        ON DELETE RESTRICT
    ) ENGINE=InnoDB;