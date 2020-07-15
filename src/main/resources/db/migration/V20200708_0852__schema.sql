CREATE TABLE IF NOT EXISTS ip_blacklist (
  ip Bigint(11) unsigned NOT NULL,
  PRIMARY KEY (ip)
);

INSERT INTO ip_blacklist VALUES (0);
INSERT INTO ip_blacklist VALUES (2130706433);
INSERT INTO ip_blacklist VALUES (2147967295);