CREATE TABLE IF NOT EXISTS ua_blacklist (
  `ua` varchar(255) NOT NULL,
  PRIMARY KEY (ua)
);

INSERT INTO ua_blacklist VALUES ('A6-Indexer');
INSERT INTO ua_blacklist VALUES ('Googlebot-News');
INSERT INTO ua_blacklist VALUES ('Googlebot');