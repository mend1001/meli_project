
PRAGMA foreign_keys = ON;

DELETE FROM item_attribute;
DELETE FROM picture;
DELETE FROM installments;
DELETE FROM shipping;
DELETE FROM price;
DELETE FROM item;
DELETE FROM product;
DELETE FROM seller;

-- Sellers
INSERT INTO seller (seller_id, name) VALUES ('S_LOGITECH', 'Logitech Store');
INSERT INTO seller (seller_id, name) VALUES ('S_RAZER', 'Razer Store');
INSERT INTO seller (seller_id, name) VALUES ('S_HP', 'HP Store');
INSERT INTO seller (seller_id, name) VALUES ('S_SAMSUNG', 'Samsung Store');
INSERT INTO seller (seller_id, name) VALUES ('S_LG', 'LG Store');

-- Products
INSERT INTO product (product_id, domain_id, category_id, vertical_id) VALUES ('MCO18031244','MCO-KEYBOARD_AND_MOUSE_KITS','MCO6263','CORE');
INSERT INTO product (product_id, domain_id, category_id, vertical_id) VALUES ('MCO18610706','MCO-KEYBOARD_AND_MOUSE_KITS','MCO6263','CORE');
INSERT INTO product (product_id, domain_id, category_id, vertical_id) VALUES ('MCO20000001','MCO-MICE','MCO1714','CORE');
INSERT INTO product (product_id, domain_id, category_id, vertical_id) VALUES ('MCO30000001','MCO-MONITORS','MCO14407','CORE');

-- Items (consistentes con JSON de ejemplo)
INSERT INTO item (item_id, product_id, seller_id, title, state, available_quantity)
VALUES ('MCO203412639600','MCO18031244','S_LOGITECH','Kit teclado y mouse Logitech Gris Grafito','VISIBLE',50);

INSERT INTO item (item_id, product_id, seller_id, title, state, available_quantity)
VALUES ('MCO2722163664','MCO18610706','S_LOGITECH','Kit Logitech MK235 Gris','VISIBLE',30);

INSERT INTO item (item_id, product_id, seller_id, title, state, available_quantity)
VALUES ('MCO200000011','MCO20000001','S_RAZER','Mouse inalámbrico Razer Black','VISIBLE',100);

INSERT INTO item (item_id, product_id, seller_id, title, state, available_quantity)
VALUES ('MCO300000011','MCO30000001','S_SAMSUNG','Monitor Samsung 24 pulgadas','VISIBLE',20);

-- Price
INSERT INTO price (item_id, amount, currency) VALUES ('MCO203412639600',89900,'COP');
INSERT INTO price (item_id, amount, currency) VALUES ('MCO2722163664',110000,'COP');
INSERT INTO price (item_id, amount, currency) VALUES ('MCO200000011',75000,'COP');
INSERT INTO price (item_id, amount, currency) VALUES ('MCO300000011',650000,'COP');

-- Installments
INSERT INTO installments (item_id, count, amount, currency, no_interest)
VALUES ('MCO203412639600',3,29967,'COP',1);

INSERT INTO installments (item_id, count, amount, currency, no_interest)
VALUES ('MCO2722163664',3,36667,'COP',1);

-- Shipping
INSERT INTO shipping (item_id, free, promise_type, label)
VALUES ('MCO203412639600',1,'none','Envío gratis');

INSERT INTO shipping (item_id, free, promise_type, label)
VALUES ('MCO2722163664',1,'none','Envío gratis');

-- Pictures
INSERT INTO picture (picture_id, item_id, ratio, scale, alt_text, square)
VALUES ('PIC001','MCO203412639600','1.00','FILL','Imagen','Q');

INSERT INTO picture (picture_id, item_id, ratio, scale, alt_text, square)
VALUES ('PIC002','MCO2722163664','1.00','FILL','Imagen','Q');

-- Attributes
INSERT INTO item_attribute (item_id, name, value)
VALUES ('MCO203412639600','marca','Logitech');

INSERT INTO item_attribute (item_id, name, value)
VALUES ('MCO203412639600','conectividad','Bluetooth');

INSERT INTO item_attribute (item_id, name, value)
VALUES ('MCO2722163664','marca','Logitech');

INSERT INTO item_attribute (item_id, name, value)
VALUES ('MCO2722163664','color','Gris');
