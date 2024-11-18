DROP TABLE IF EXISTS tbl_token_blacklist;
CREATE TABLE IF NOT EXISTS tbl_token_blacklist (
    token VARCHAR(155) PRIMARY KEY
);


INSERT INTO tbl_user (id, email, password, created_at, updated_at)
SELECT gen_random_uuid(),
    'tyrion@casterlyrock.com',
    '$2a$10$iaqQHgOAJs06D/odeszYbOQwCqzrVKN3rlSsaEKw3N0jCm8S.7ym2',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM tbl_user
    WHERE email = 'tyrion@casterlyrock.com'
);


INSERT INTO tbl_user (id, email, password, created_at, updated_at)
SELECT gen_random_uuid(),
    'danaerys@dragonstone.com',
    '$2a$10$dfBHi1JlNw6YD3p4O4vZ7ezoLMLenirbI5Lmc2l/ylHPsNo6QPri6',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM tbl_user
    WHERE email = 'danaerys@dragonstone.com'
);

INSERT INTO tbl_vendor (id, name, location, created_at, updated_at)
SELECT gen_random_uuid(),
       'PT Pro Sigmaka Mandiri',
       'Mampang Prapatan, Jakarta Selatan',
       CURRENT_TIMESTAMP,
       CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM tbl_vendor
    WHERE name = 'PT Pro Sigmaka Mandiri'
);

INSERT INTO tbl_vendor (id, name, location, created_at, updated_at)
SELECT gen_random_uuid(),
       'PT Sinergi Informatika Semen Indonesia',
       'Graha Aktiva, Setiabudi, Jakarta Selatan',
       CURRENT_TIMESTAMP,
       CURRENT_TIMESTAMP
WHERE NOT EXISTS (
    SELECT 1
    FROM tbl_vendor
    WHERE name = 'Bank Syariah Indonesia'
);
