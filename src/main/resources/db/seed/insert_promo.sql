-- psql -f src/main/resources/db/seed/insert_promo.sql

INSERT INTO promo_codes (code, discount_percentage, is_active)
VALUES ('SAVE10', 10.00, TRUE)
ON CONFLICT (code) DO NOTHING;
