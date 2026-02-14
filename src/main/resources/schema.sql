PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS product (
  product_id TEXT PRIMARY KEY,
  domain_id TEXT,
  category_id TEXT,
  vertical_id TEXT
);

CREATE TABLE IF NOT EXISTS seller (
  seller_id TEXT PRIMARY KEY,
  name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS item (
  item_id TEXT PRIMARY KEY,
  product_id TEXT NOT NULL,
  seller_id TEXT NOT NULL,
  title TEXT NOT NULL,
  state TEXT NOT NULL,
  available_quantity INTEGER NOT NULL DEFAULT 0,
  FOREIGN KEY (product_id) REFERENCES product(product_id),
  FOREIGN KEY (seller_id) REFERENCES seller(seller_id)
);

CREATE TABLE IF NOT EXISTS price (
  item_id TEXT PRIMARY KEY,
  amount INTEGER NOT NULL,
  currency TEXT NOT NULL,
  FOREIGN KEY (item_id) REFERENCES item(item_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS installments (
  item_id TEXT PRIMARY KEY,
  count INTEGER NOT NULL,
  amount INTEGER NOT NULL,
  currency TEXT NOT NULL,
  no_interest INTEGER NOT NULL DEFAULT 0,
  FOREIGN KEY (item_id) REFERENCES item(item_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS shipping (
  item_id TEXT PRIMARY KEY,
  free INTEGER NOT NULL DEFAULT 0,
  promise_type TEXT,
  label TEXT,
  FOREIGN KEY (item_id) REFERENCES item(item_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS picture (
  picture_id TEXT PRIMARY KEY,
  item_id TEXT NOT NULL,
  ratio TEXT,
  scale TEXT,
  alt_text TEXT,
  square TEXT,
  FOREIGN KEY (item_id) REFERENCES item(item_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS item_attribute (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  item_id TEXT NOT NULL,
  name TEXT NOT NULL,
  value TEXT,
  FOREIGN KEY (item_id) REFERENCES item(item_id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX IF NOT EXISTS ux_item_attribute
ON item_attribute(item_id, name);
CREATE INDEX IF NOT EXISTS idx_item_product_id ON item(product_id);
CREATE INDEX IF NOT EXISTS idx_item_seller_id ON item(seller_id);
CREATE INDEX IF NOT EXISTS idx_attr_item_id ON item_attribute(item_id);
CREATE INDEX IF NOT EXISTS idx_picture_item_id ON picture(item_id);

