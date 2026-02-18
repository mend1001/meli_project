
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

-- Insertar productos faltantes 
INSERT OR IGNORE INTO product (product_id, domain_id, category_id, vertical_id) VALUES
('MCO18659176', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18330776', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18902592', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18072574', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18701474', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18897546', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18964047', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18278082', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18274680', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE'),
('MCO18820391', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18113668', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18425800', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18154739', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18566390', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18352457', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18683414', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18820652', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18836587', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18756321', 'MCO-MICE', 'MCO1714', 'CORE'),
('MCO18193319', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18002805', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18459023', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18264659', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18872565', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18197816', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18559543', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18716318', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18415032', 'MCO-MONITORS', 'MCO14407', 'CORE'),
('MCO18309478', 'MCO-MONITORS', 'MCO14407', 'CORE');

-- Insertar items faltantes
INSERT INTO item (item_id, product_id, seller_id, title, state, available_quantity) VALUES
-- Keyboards & Mouse Kits
('MCO289056647601', 'MCO18659176', 'S_LOGITECH', 'Kit teclado y mouse inalámbrico Genius Blanco', 'VISIBLE', 25),
('MCO286441134702', 'MCO18330776', 'S_LOGITECH', 'Kit teclado y mouse inalámbrico Microsoft Negro', 'VISIBLE', 30),
('MCO259402178203', 'MCO18902592', 'S_HP', 'Kit teclado y mouse inalámbrico HP Negro', 'VISIBLE', 20),
('MCO287619829604', 'MCO18072574', 'S_LOGITECH', 'Kit teclado y mouse inalámbrico Redragon Blanco', 'VISIBLE', 15),
('MCO229901268605', 'MCO18701474', 'S_LOGITECH', 'Kit teclado y mouse inalámbrico Corsair Gris', 'VISIBLE', 40),
('MCO292486655306', 'MCO18897546', 'S_RAZER', 'Kit teclado y mouse inalámbrico Razer Negro', 'VISIBLE', 35),
('MCO253993148107', 'MCO18964047', 'S_LOGITECH', 'Kit teclado y mouse inalámbrico Lenovo Negro', 'VISIBLE', 28),
('MCO254318955508', 'MCO18278082', 'S_HP', 'Kit teclado y mouse inalámbrico Acer Azul', 'VISIBLE', 22),
('MCO268409527709', 'MCO18274680', 'S_LOGITECH', 'Kit teclado y mouse inalámbrico Dell Blanco', 'VISIBLE', 18),

-- Mouse
('MCO235668142500', 'MCO18820391', 'S_LOGITECH', 'Mouse inalámbrico Logitech Láser Compacto', 'VISIBLE', 50),
('MCO204107835201', 'MCO18113668', 'S_LOGITECH', 'Mouse inalámbrico Genius Láser Compacto', 'VISIBLE', 45),
('MCO232314247002', 'MCO18425800', 'S_LOGITECH', 'Mouse inalámbrico Microsoft Láser Ergonómico', 'VISIBLE', 30),
('MCO226197630103', 'MCO18154739', 'S_HP', 'Mouse inalámbrico HP Óptico Ambidiestro', 'VISIBLE', 60),
('MCO295888026304', 'MCO18566390', 'S_RAZER', 'Mouse inalámbrico Redragon Láser Compacto', 'VISIBLE', 55),
('MCO241753224305', 'MCO18352457', 'S_LOGITECH', 'Mouse inalámbrico Corsair Láser Compacto', 'VISIBLE', 25),
('MCO283884259906', 'MCO18683414', 'S_RAZER', 'Mouse inalámbrico Razer Láser Ambidiestro', 'VISIBLE', 40),
('MCO260766544807', 'MCO18820652', 'S_LOGITECH', 'Mouse inalámbrico Lenovo Láser Ergonómico', 'VISIBLE', 35),
('MCO238099040108', 'MCO18836587', 'S_HP', 'Mouse inalámbrico Acer Óptico Ambidiestro', 'VISIBLE', 48),
('MCO212955326509', 'MCO18756321', 'S_LOGITECH', 'Mouse inalámbrico Dell Láser Ergonómico', 'VISIBLE', 32),

-- Monitors
('MCO236583021500', 'MCO18193319', 'S_SAMSUNG', 'Monitor Samsung 24" QHD 144Hz', 'VISIBLE', 10),
('MCO216807562201', 'MCO18002805', 'S_LG', 'Monitor LG 29" Full HD 60Hz', 'VISIBLE', 8),
('MCO253020857902', 'MCO18459023', 'S_SAMSUNG', 'Monitor AOC 29" 4K UHD 60Hz', 'VISIBLE', 5),
('MCO233771022403', 'MCO18264659', 'S_SAMSUNG', 'Monitor Dell 24" Full HD IPS 100Hz', 'VISIBLE', 12),
('MCO269547298004', 'MCO18872565', 'S_HP', 'Monitor HP 24" Full HD TN 144Hz', 'VISIBLE', 7),
('MCO293372770105', 'MCO18197816', 'S_SAMSUNG', 'Monitor ASUS 29" HD IPS 144Hz', 'VISIBLE', 6),
('MCO292271164706', 'MCO18559543', 'S_SAMSUNG', 'Monitor Acer 23.8" Full HD VA 100Hz', 'VISIBLE', 9),
('MCO266540893807', 'MCO18716318', 'S_SAMSUNG', 'Monitor Lenovo 23.8" QHD TN 144Hz', 'VISIBLE', 4),
('MCO270676888408', 'MCO18415032', 'S_SAMSUNG', 'Monitor ViewSonic 27" Full HD IPS 144Hz', 'VISIBLE', 3),
('MCO286596042709', 'MCO18309478', 'S_SAMSUNG', 'Monitor BenQ 23.8" QHD IPS 165Hz', 'VISIBLE', 2);


INSERT INTO price (item_id, amount, currency) VALUES

('MCO289056647601', 199900, 'COP'),
('MCO286441134702', 109900, 'COP'),
('MCO259402178203', 169900, 'COP'),
('MCO287619829604', 199900, 'COP'),
('MCO229901268605', 89900, 'COP'),
('MCO292486655306', 149900, 'COP'),
('MCO253993148107', 89900, 'COP'),
('MCO254318955508', 119900, 'COP'),
('MCO268409527709', 149900, 'COP'),


('MCO235668142500', 89900, 'COP'),
('MCO204107835201', 69900, 'COP'),
('MCO232314247002', 89900, 'COP'),
('MCO226197630103', 59900, 'COP'),
('MCO295888026304', 59900, 'COP'),
('MCO241753224305', 89900, 'COP'),
('MCO283884259906', 59900, 'COP'),
('MCO260766544807', 89900, 'COP'),
('MCO238099040108', 49900, 'COP'),
('MCO212955326509', 69900, 'COP'),


('MCO236583021500', 1599900, 'COP'),
('MCO216807562201', 1299900, 'COP'),
('MCO253020857902', 1299900, 'COP'),
('MCO233771022403', 1299900, 'COP'),
('MCO269547298004', 499900, 'COP'),
('MCO293372770105', 799900, 'COP'),
('MCO292271164706', 999900, 'COP'),
('MCO266540893807', 1299900, 'COP'),
('MCO270676888408', 599900, 'COP'),
('MCO286596042709', 999900, 'COP');


INSERT INTO shipping (item_id, free, promise_type, label) VALUES
('MCO289056647601', 1, 'none', 'Envío gratis'),
('MCO286441134702', 1, 'none', 'Envío gratis'),
('MCO259402178203', 0, 'none', 'Envío con costo'),
('MCO287619829604', 1, 'none', 'Envío gratis'),
('MCO229901268605', 1, 'none', 'Envío gratis'),
('MCO292486655306', 1, 'none', 'Envío gratis'),
('MCO253993148107', 1, 'none', 'Envío gratis'),
('MCO254318955508', 1, 'none', 'Envío gratis'),
('MCO268409527709', 1, 'none', 'Envío gratis'),
('MCO235668142500', 1, 'none', 'Envío gratis'),
('MCO204107835201', 1, 'none', 'Envío gratis'),
('MCO232314247002', 1, 'none', 'Envío gratis'),
('MCO226197630103', 0, 'none', 'Envío con costo'),
('MCO295888026304', 1, 'none', 'Envío gratis'),
('MCO241753224305', 0, 'none', 'Envío con costo'),
('MCO283884259906', 1, 'none', 'Envío gratis'),
('MCO260766544807', 1, 'none', 'Envío gratis'),
('MCO238099040108', 1, 'none', 'Envío gratis'),
('MCO212955326509', 1, 'none', 'Envío gratis'),
('MCO236583021500', 0, 'none', 'Envío con costo'),
('MCO216807562201', 1, 'none', 'Envío gratis'),
('MCO253020857902', 1, 'none', 'Envío gratis'),
('MCO233771022403', 1, 'none', 'Envío gratis'),
('MCO269547298004', 0, 'none', 'Envío con costo'),
('MCO293372770105', 0, 'none', 'Envío con costo'),
('MCO292271164706', 1, 'none', 'Envío gratis'),
('MCO266540893807', 1, 'none', 'Envío gratis'),
('MCO270676888408', 1, 'none', 'Envío gratis'),
('MCO286596042709', 1, 'none', 'Envío gratis');


INSERT INTO picture (picture_id, item_id, ratio, scale, alt_text, square) VALUES
('PIC003', 'MCO289056647601', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC004', 'MCO286441134702', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC005', 'MCO259402178203', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC006', 'MCO287619829604', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC007', 'MCO229901268605', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC008', 'MCO292486655306', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC009', 'MCO253993148107', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC010', 'MCO254318955508', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC011', 'MCO268409527709', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC012', 'MCO235668142500', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC013', 'MCO204107835201', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC014', 'MCO232314247002', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC015', 'MCO226197630103', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC016', 'MCO295888026304', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC017', 'MCO241753224305', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC018', 'MCO283884259906', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC019', 'MCO260766544807', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC020', 'MCO238099040108', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC021', 'MCO212955326509', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC022', 'MCO236583021500', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC023', 'MCO216807562201', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC024', 'MCO253020857902', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC025', 'MCO233771022403', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC026', 'MCO269547298004', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC027', 'MCO293372770105', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC028', 'MCO292271164706', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC029', 'MCO266540893807', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC030', 'MCO270676888408', '1.00', 'FILL', 'Imagen', 'Q'),
('PIC031', 'MCO286596042709', '1.00', 'FILL', 'Imagen', 'Q');


INSERT INTO item_attribute (item_id, name, value) VALUES
('MCO289056647601', 'marca', 'Genius'),
('MCO289056647601', 'color', 'Blanco'),
('MCO286441134702', 'marca', 'Microsoft'),
('MCO286441134702', 'color', 'Negro'),
('MCO259402178203', 'marca', 'HP'),
('MCO259402178203', 'color', 'Negro'),
('MCO287619829604', 'marca', 'Redragon'),
('MCO287619829604', 'color', 'Blanco'),
('MCO229901268605', 'marca', 'Corsair'),
('MCO229901268605', 'color', 'Gris'),
('MCO292486655306', 'marca', 'Razer'),
('MCO292486655306', 'color', 'Negro');