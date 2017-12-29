USE CryptoWallet;

CREATE TABLE cryptocurrencies (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    symbol VARCHAR(250) NOT NULL,
    image_url VARCHAR(250) NOT NULL,
    base_url VARCHAR(250) NOT NULL,
    resource_url VARCHAR(250) NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    UNIQUE (name, symbol)
);

CREATE TABLE alert_types (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL UNIQUE,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL
);

CREATE TABLE users (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    lastname VARCHAR(250) NOT NULL,
    firstname VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL UNIQUE,
    password VARCHAR(250) NOT NULL,
    is_enabled BOOLEAN NOT NULL DEFAULT false,
    is_administrator BOOLEAN NOT NULL DEFAULT false,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    last_activity DATETIME NOT NULL
);

CREATE TABLE favorites (
    cryptocurrency INT NOT NULL,
    user INT NOT NULL,
    PRIMARY KEY (cryptocurrency, user),
    FOREIGN KEY (cryptocurrency) REFERENCES cryptocurrencies (id),
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE logs (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ip_address VARCHAR(20) NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    user INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE tokens (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    value VARCHAR(36) NOT NULL UNIQUE,
    begin_date DATETIME NOT NULL,
    end_date DATETIME NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    user INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE settings (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    theme VARCHAR(250) NOT NULL,
    chart_period VARCHAR(3) NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    user INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE alerts (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    threshold NUMERIC(30, 8) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT false,
    is_one_shot BOOLEAN NOT NULL DEFAULT false,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    user INT NOT NULL,
    cryptocurrency INT NOT NULL,
    type INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users (id),
    FOREIGN KEY (cryptocurrency) REFERENCES cryptocurrencies (id),
    FOREIGN KEY (type) REFERENCES alert_types (id)
);

CREATE TABLE wallets (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    user INT NOT NULL,
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE assets (
    wallet INT NOT NULL,
    cryptocurrency INT NOT NULL,
    amount NUMERIC(30, 8) NOT NULL,
    purchase_price NUMERIC(30, 8) NOT NULL,
    PRIMARY KEY (wallet, cryptocurrency),
    FOREIGN KEY (wallet) REFERENCES wallets (id),
    FOREIGN KEY (cryptocurrency) REFERENCES cryptocurrencies (id)
);