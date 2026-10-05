create extension if not exists pg_trgm;

drop table if exists products;

create table products (
    id bigserial primary key,
    title varchar(100) not null,
    description varchar(2000) not null,
    price numeric(8, 2) not null,
    rating numeric(3, 2),
    images jsonb not null default '[]'::jsonb,
    stock_quantity int not null default 0,
    created_at timestamptz not null default now()
);

create index idx_products_name_lower on products(LOWER(title));
create index idx_products_price on products(price);
create index idx_products_rating on products(rating);
CREATE INDEX idx_products_title_trgm ON products USING gist (title gist_trgm_ops);
CREATE INDEX idx_products_name_gin ON products
    USING gist (description gist_trgm_ops);