CREATE TABLE IF NOT EXISTS user_role (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_detail (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_address (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS user_payment (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS bucket (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS app_user (
  id BIGINT NOT NULL,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(45) NOT NULL,
  email VARCHAR(45) NOT NULL,
  role_id BIGINT NOT NULL,
  user_detail_id BIGINT NOT NULL,
  user_address_id BIGINT NOT NULL,
  user_payment_id BIGINT NOT NULL,
  bucket_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_user_role1 FOREIGN KEY (role_id) REFERENCES user_role (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_user_user_detail1 FOREIGN KEY (user_detail_id) REFERENCES user_detail (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_user_user_address1 FOREIGN KEY (user_address_id) REFERENCES user_address (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_user_user_payment1 FOREIGN KEY (user_payment_id) REFERENCES user_payment (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_user_bucket1 FOREIGN KEY (bucket_id) REFERENCES bucket (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS category (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS product_type (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS discount (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS brand (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
  id BIGSERIAL PRIMARY KEY,
  category_id BIGINT NOT NULL,
  product_type_id BIGINT NOT NULL,
  discount_id BIGINT NOT NULL,
  brand_id BIGINT NOT NULL,
  CONSTRAINT fk_product_category1 FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_product_type1 FOREIGN KEY (product_type_id) REFERENCES product_type (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_product_discount1 FOREIGN KEY (discount_id) REFERENCES discount (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_product_brand1 FOREIGN KEY (brand_id) REFERENCES brand (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS shop_address (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS shop (
  id BIGSERIAL PRIMARY KEY,
  shop_address_id BIGINT NOT NULL,
  CONSTRAINT fk_shop_shop_address1 FOREIGN KEY (shop_address_id) REFERENCES shop_address (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS liked (
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT fk_liked_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_liked_user1 FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS custom_order_status (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS custom_order_address (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS custom_order_details (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS custom_order (
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  custom_order_status_id BIGINT NOT NULL,
  custom_order_address_id BIGINT NOT NULL,
  custom_order_details_id BIGINT NOT NULL,
  CONSTRAINT fk_custom_order_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_custom_order_user1 FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_custom_order_custom_order_status1 FOREIGN KEY (custom_order_status_id) REFERENCES custom_order_status (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_custom_order_custom_order_address1 FOREIGN KEY (custom_order_address_id) REFERENCES custom_order_address (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_custom_order_custom_order_details1 FOREIGN KEY (custom_order_details_id) REFERENCES custom_order_details (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS store_address (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS store (
  id BIGSERIAL PRIMARY KEY,
  store_address_id BIGINT NOT NULL,
  CONSTRAINT fk_store_store_address1 FOREIGN KEY (store_address_id) REFERENCES store_address (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS store_has_shop (
  store_id BIGINT NOT NULL,
  shop_id BIGINT NOT NULL,
  PRIMARY KEY (store_id, shop_id),
  CONSTRAINT fk_store_has_shop_store1 FOREIGN KEY (store_id) REFERENCES store (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_store_has_shop_shop1 FOREIGN KEY (shop_id) REFERENCES shop (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS viewed (
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT fk_viewed_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_viewed_user1 FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS custom_order_history (
  id BIGSERIAL PRIMARY KEY,
  custom_order_id BIGINT NOT NULL,
  user_id BIGINT NOT NULL,
  CONSTRAINT fk_custom_order_history_custom_order1 FOREIGN KEY (custom_order_id) REFERENCES custom_order (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_custom_order_history_user1 FOREIGN KEY (user_id) REFERENCES app_user (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS option_group (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL
);

CREATE TABLE IF NOT EXISTS option (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(45) NOT NULL,
  option_group_id BIGINT NOT NULL,
  CONSTRAINT fk_option_option_group1 FOREIGN KEY (option_group_id) REFERENCES option_group (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS product_option (
  id BIGSERIAL PRIMARY KEY,
  option_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  CONSTRAINT fk_product_option_option1 FOREIGN KEY (option_id) REFERENCES option (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_product_option_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS post (
  id BIGSERIAL PRIMARY KEY,
  product_id BIGINT NOT NULL,
  CONSTRAINT fk_post_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS post_comment (
  id BIGSERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS post_comment_has_post (
  post_comment_id BIGINT NOT NULL,
  post_id BIGINT NOT NULL,
  PRIMARY KEY (post_comment_id, post_id),
  CONSTRAINT fk_comment_has_post_comment1 FOREIGN KEY (post_comment_id) REFERENCES post_comment (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_comment_has_post_post1 FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS store_has_product (
  store_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  PRIMARY KEY (store_id, product_id),
  CONSTRAINT fk_store_has_product_store1 FOREIGN KEY (store_id) REFERENCES store (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_store_has_product_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS shop_has_product (
  shop_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  PRIMARY KEY (shop_id, product_id),
  CONSTRAINT fk_shop_has_product_shop1 FOREIGN KEY (shop_id) REFERENCES shop (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_shop_has_product_product1 FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);
