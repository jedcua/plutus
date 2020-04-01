CREATE TABLE store (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NULL,
    tin VARCHAR(255) NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT id_pk PRIMARY KEY (id)
);

CREATE TABLE product (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    barcode VARCHAR(255) NULL,
    price DECIMAL NOT NULL,
    unit VARCHAR(255) NOT NULL,
    store_id BIGINT NOT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT id_pk PRIMARY KEY (id),
    FOREIGN KEY (store_id) REFERENCES store(id)
);

CREATE TABLE adjustment (
    id BIGINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(255) NOT NULL,
    amount VARCHAR(255) NOT NULL,
    store_id BIGINT NOT NULL,
    created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT id_pk PRIMARY KEY (id),
    FOREIGN KEY (store_id) REFERENCES store(id)
);