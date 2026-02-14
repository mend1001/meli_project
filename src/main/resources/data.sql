INSERT OR IGNORE INTO seller (seller_id, name) VALUES ('S1', 'TechStore');

INSERT OR IGNORE INTO product (product_id, domain_id, category_id, vertical_id)
VALUES ('MCO18610706', 'MCO-KEYBOARD_AND_MOUSE_KITS', 'MCO6263', 'CORE');

INSERT OR IGNORE INTO item (item_id, product_id, seller_id, title, state, available_quantity)
VALUES ('MCO2722163664', 'MCO18610706', 'S1',
        'Kit de teclado y mouse inalámbrico Logitech MK235 Español de color Gris Grafito',
        'VISIBLE', 10);

INSERT OR IGNORE INTO price (item_id, amount, currency) VALUES ('MCO2722163664', 110000, 'COP');

INSERT OR IGNORE INTO installments (item_id, count, amount, currency, no_interest)
VALUES ('MCO2722163664', 3, 36667, 'COP', 1);

INSERT OR IGNORE INTO shipping (item_id, free, promise_type, label)
VALUES ('MCO2722163664', 1, 'none', 'Envío gratis');

INSERT OR IGNORE INTO picture (picture_id, item_id, ratio, scale, alt_text, square)
VALUES ('707985-MLA99443348186_112025', 'MCO2722163664', '1.00', 'FILL', 'Imagen', 'Q');

INSERT OR IGNORE INTO item_attribute (item_id, name, value) VALUES ('MCO2722163664', 'marca', 'Logitech');
INSERT OR IGNORE INTO item_attribute (item_id, name, value) VALUES ('MCO2722163664', 'conectividad', '2.4 GHz (USB)');
