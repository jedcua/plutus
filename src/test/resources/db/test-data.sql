INSERT INTO store(name, address, tin) VALUES ('Name1', 'Address1', 'Tin1');
INSERT INTO store(name, address, tin) VALUES ('Name2', 'Address2', NULL);
INSERT INTO store(name, address, tin) VALUES ('Name3', 'Address3', 'Tin3');

INSERT INTO product(name, barcode, price, unit, store_id) VALUES ('Product1', 'Barcode', 123.45, 'pcs', 1);
INSERT INTO product(name, barcode, price, unit, store_id) VALUES ('Product2',  NULL, 123.45, 'pcs', 1);
INSERT INTO product(name, barcode, price, unit, store_id) VALUES ('Product3', 'Barcode', 123.45, 'pcs', 1);
