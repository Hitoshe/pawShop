CREATE TABLE if not exists refresh_tokens(
    id bigserial primary key,
    token varchar(255) not null unique,
    user_id bigint not null references users(id) on delete cascade,
    expiry_date timestamptz not null,
    created_at timestamptz default now()
);

CREATE INDEX idx_refresh_token_value ON refresh_tokens(token);