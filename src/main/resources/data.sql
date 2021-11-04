
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE Product;
DROP TABLE Cart_Item;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS Product(
    `id` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `price` DECIMAL(8,2) DEFAULT NULL,
    `sku` varchar(255) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `discount` bool DEFAULT FALSE NULL,
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
DELETE FROM product;

INSERT INTO product(id, name, price, sku, description, discount) VALUES ('123e4567-e89b-12d3-a456-426614174000', 'Pintura', 200.10 ,'PRA-52', 'pintura en balde', true);
INSERT INTO product(id, name, price, sku, description, discount) VALUES ('123e4567-e89b-12d3-a456-426614174001', 'Cemento', 540.50 ,'CRE-52', 'cemento en balde', false);

CREATE TABLE IF NOT EXISTS Shopping_Cart(
    `id` varchar(255) NOT NULL,
    `cart_Items` varchar(255) DEFAULT NULL,
    `total_price` INTEGER DEFAULT NULL,
    `state` varchar(255) DEFAULT "Pending",
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS Cart_Item(
    `id` varchar(255) NOT NULL,
    `product_id` varchar(255) DEFAULT NULL,
    `quantity` INTEGER DEFAULT NULL,
    `shopping_cart_id` varchar(255),
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table Cart_Item add foreign key (product_id) references Product(id) on DELETE CASCADE;

INSERT INTO Cart_Item(id, product_id, quantity) VALUES ('223e4567-e89b-12d3-a456-426614174053','123e4567-e89b-12d3-a456-426614174000', 1);




/*
CREATE TABLE IF NOT EXISTS Shopping_Cart(
    `id` varchar(255) NOT NULL,
    `cart_Items` varchar(255) DEFAULT NULL,
    `total_price` INTEGER DEFAULT NULL,
    `state` varchar(255) DEFAULT "Pending",
    PRIMARY KEY (`id`)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
*/