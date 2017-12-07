USE CryptoWallet;

INSERT INTO users VALUES (1, 'DELORME', 'Loïc', 'loic.delorme@test.fr', 'loic.delorme', true, true, now(), now(), now());
INSERT INTO users VALUES (2, 'KASPRZYK', 'Nicolas', 'nicolas.kasprzyk@test.fr', 'nicolas.kasprzyk', true, true, now(), now(), now());

INSERT INTO favorites VALUES (1, 1);
INSERT INTO favorites VALUES (2, 1);
INSERT INTO favorites VALUES (3, 1);
INSERT INTO favorites VALUES (1, 2);

INSERT INTO logs VALUES (1, '127.0.0.1', now(), now(), 1);
INSERT INTO logs VALUES (2, '127.0.0.1', now(), now(), 2);

INSERT INTO tokens VALUES (1, 'b42f7b33-fc74-4c45-b2c3-c9717cffe439', now(), now(), now(), now(), 1);
INSERT INTO tokens VALUES (2, '462d5496-a82b-4e0b-8f6b-f2bc0768cdca', now(), now(), now(), now(), 2);

INSERT INTO settings VALUES (1, 'Light theme', 'light', now(), now(), 1);
INSERT INTO settings VALUES (2, 'Dark theme', 'dark', now(), now(), 2);

INSERT INTO alerts VALUES (1, '0.0002', true, false, now(), now(), 1, 2, 1);
INSERT INTO alerts VALUES (2, '0.0002', true, true, now(), now(), 1, 3, 1);

INSERT INTO wallets VALUES (1, 'Bittrex', now(), now(), 1);
INSERT INTO wallets VALUES (2, 'Bitfinex', now(), now(), 2);

INSERT INTO assets VALUES (1, 1, '1.25', '1.25');
INSERT INTO assets VALUES (1, 2, '10000', '0.4874');