USE CryptoWallet;

CREATE TABLE themes (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL UNIQUE,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL
);

CREATE TABLE currencies (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    symbol VARCHAR(3) NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    UNIQUE (name, symbol)
);

CREATE TABLE chart_periods (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(3) NOT NULL UNIQUE,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL
);

CREATE TABLE cryptocurrencies (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(250) NOT NULL,
    symbol VARCHAR(250) NOT NULL,
    image_url VARCHAR(250) NOT NULL,
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

CREATE TABLE settings (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    theme INT NOT NULL,
    currency INT NOT NULL,
    chart_period INT NOT NULL,
    FOREIGN KEY (theme) REFERENCES themes (id),
    FOREIGN KEY (currency) REFERENCES currencies (id),
    FOREIGN KEY (chart_period) REFERENCES chart_periods (id)
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
    last_activity DATETIME NOT NULL,
    setting INT NOT NULL,
    FOREIGN KEY (setting) REFERENCES settings (id)
);

CREATE TABLE favorites (
    cryptocurrency INT NOT NULL,
    user INT NOT NULL,
    PRIMARY KEY (cryptocurrency, user),
    FOREIGN KEY (cryptocurrency) REFERENCES cryptocurrencies (id),
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE devices (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    platform VARCHAR(250) NOT NULL,
    uuid VARCHAR(250) NOT NULL,
    creation_date DATETIME NOT NULL,
    last_update DATETIME NOT NULL,
    user INT NOT NULL,
    UNIQUE (user, uuid),
    FOREIGN KEY (user) REFERENCES users (id)
);

CREATE TABLE logs (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    ip_address VARCHAR(20) NOT NULL,
    platform VARCHAR(250) NOT NULL,
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
    platform VARCHAR(250) NOT NULL,
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