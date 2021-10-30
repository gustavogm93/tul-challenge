CREATE TABLE IF NOT EXISTS product(
    `id` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `price` DECIMAL(8,2) DEFAULT NULL,
    `sku` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
DELETE FROM product;

INSERT INTO product(id, name, price, sku, description) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Pintura', 200.10 ,'PRA-52', 'pintura en balde');
INSERT INTO product(id, name, price, sku, description) VALUES ('123e4567-e89b-12d3-a456-426614174001', 'Cemento', 540.50 ,'CRE-52', 'cemento en balde');


CREATE TABLE IF NOT EXISTS Shopping_Cart(
    `id` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO Shopping_Cart(id) VALUES ('123e4567-e89b-12d3-a456-426614174001');
