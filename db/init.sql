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

CREATE TABLE IF NOT EXISTS t_equipment (
    id BIGINT PRIMARY KEY,
    asset_tag VARCHAR(50) NOT NULL,
    name VARCHAR(100) NOT NULL,
    owner_name VARCHAR(100)
);

INSERT INTO users (user_name, user_pass) VALUES ('admin', 'admin123') ON CONFLICT DO NOTHING;
INSERT INTO user_roles (user_name, role_name) VALUES ('admin', 'it_staff') ON CONFLICT DO NOTHING;

INSERT INTO t_equipment (id, asset_tag, name, owner_name) VALUES (1, 'IT-LAP-001', 'Dell Latitude 5540', 'Jan Kowalski') ON CONFLICT DO NOTHING;
INSERT INTO t_equipment (id, asset_tag, name, owner_name) VALUES (2, 'IT-MON-002', 'Dell UltraSharp U2723QE', 'Anna Nowak') ON CONFLICT DO NOTHING;
INSERT INTO t_equipment (id, asset_tag, name, owner_name) VALUES (3, 'IT-SRV-003', 'HPE ProLiant DL380', 'Dział IT') ON CONFLICT DO NOTHING;
