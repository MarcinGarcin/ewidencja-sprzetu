CREATE TABLE IF NOT EXISTS users (
    user_name VARCHAR(50) PRIMARY KEY,
    user_pass VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_name VARCHAR(50) NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_name, role_name),
    FOREIGN KEY (user_name) REFERENCES users(user_name)
);

INSERT INTO users (user_name, user_pass) VALUES ('admin', 'admin123') ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'it_staff') ON CONFLICT DO NOTHING;
