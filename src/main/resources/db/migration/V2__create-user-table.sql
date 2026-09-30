CREATE table if not exists users (
    id bigserial PRIMARY KEY,
    email varchar(100) NOT NULL UNIQUE,
    password_hash varchar(255) NOT NULL,
    role varchar(20) NOT NULL DEFAULT 'customer'
    CHECK (role IN ('customer', 'admin')),
    created_at timestamptz default now()
)