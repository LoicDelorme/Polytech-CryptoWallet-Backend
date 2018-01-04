USE CryptoWallet;

INSERT INTO users VALUES (1, 'DELORME', 'Loïc', 'loic.delorme@test.fr', 'loic.delorme', true, true, now(), now(), now());
INSERT INTO users VALUES (2, 'KASPRZYK', 'Nicolas', 'nicolas.kasprzyk@test.fr', 'nicolas.kasprzyk', true, false, now(), now(), now());
INSERT INTO users VALUES (3, 'LAFAYE DE MICHEAUX', 'Yoan', 'yoan.lafayedemicheaux@test.fr', 'yoan.lafayedemicheaux', false, false, now(), now(), now());

INSERT INTO favorites VALUES (1, 1);
INSERT INTO favorites VALUES (2, 1);
INSERT INTO favorites VALUES (3, 1);
INSERT INTO favorites VALUES (1, 2);
INSERT INTO favorites VALUES (2, 2);
INSERT INTO favorites VALUES (3, 2);
INSERT INTO favorites VALUES (4, 2);

INSERT INTO settings VALUES (1, 'Light setting', 'light', '1d', now(), now(), 1);
INSERT INTO settings VALUES (2, 'Dark setting', 'dark', '1y', now(), now(), 1);
INSERT INTO settings VALUES (3, 'Setting n° 1', 'light', '3M', now(), now(), 2);

INSERT INTO alerts VALUES (1, 'ETH = 0.002467', '0.002467', false, false, now(), now(), 1, 2, 1);
INSERT INTO alerts VALUES (2, 'BCH = 0.00467', '0.00467', false, true, now(), now(), 1, 3, 1);
INSERT INTO alerts VALUES (3, 'XRP = 0.0120467', '0.0120467', true, false, now(), now(), 1, 4, 1);
INSERT INTO alerts VALUES (4, 'DASH = 0.012467', '0.012467', true, true, now(), now(), 1, 5, 1);

INSERT INTO wallets VALUES (1, 'Wallet n° 1', now(), now(), 1);
INSERT INTO wallets VALUES (2, 'Wallet n° 2', now(), now(), 1);
INSERT INTO wallets VALUES (3, 'BTC Wallet', now(), now(), 2);
INSERT INTO wallets VALUES (4, 'ETH Wallet', now(), now(), 2);

INSERT INTO assets VALUES (1, 1, '1.25', '1.25');
INSERT INTO assets VALUES (1, 2, '10000', '0.4874');
INSERT INTO assets VALUES (1, 3, '5.765', '0.25');
INSERT INTO assets VALUES (1, 4, '100', '0.4579');
INSERT INTO assets VALUES (2, 3, '1.25', '1.25');
INSERT INTO assets VALUES (2, 4, '10000', '0.4874');