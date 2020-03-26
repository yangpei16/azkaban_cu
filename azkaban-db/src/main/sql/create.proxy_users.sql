CREATE TABLE proxy_users (
  id     INT         NOT NULL PRIMARY KEY AUTO_INCREMENT,
  submit_user   VARCHAR(64) NOT NULL,
  proxy_user   VARCHAR(64)         NOT NULL
);

CREATE INDEX idx_proxy_users
  ON proxy_users (submit_user);
