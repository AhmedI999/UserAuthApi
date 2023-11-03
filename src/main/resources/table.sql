DROP TABLE IF EXISTS user_details;
CREATE TABLE user_details (
            id                  TEXT        NOT NULL,
            firstname           TEXT        NOT NULL,
            lastname            TEXT        NOT NULL,
            email               TEXT,
            password            TEXT        NOT NULL,
            marketing_consent   BOOLEAN     NOT NULL,
            role                TEXT
);

-- Populate the 'role' column with a default value, for example 'USER'
UPDATE user_details SET role = 'USER';

-- Create an ENUM type 'role' with allowed values
DO $$
    BEGIN
        IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'role') THEN
            CREATE TYPE role AS ENUM ('USER', 'ADMIN');
        END IF;
    END $$;