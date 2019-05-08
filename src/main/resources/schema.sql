drop table if exists oauth_client_token;
CREATE TABLE oauth_client_token (
  token_id VARCHAR(512),
  token LONGBLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255)
);

drop table if exists partner_resources;
CREATE TABLE partner_resources (
    partner_id VARCHAR(512) NOT NULL,
    client_id VARCHAR(512) NOT NULL,
    client_secret VARCHAR(512) NOT NULL,
    access_token_uri VARCHAR(255) NOT NULL,
    user_authorization_uri VARCHAR(255),
    grant_type VARCHAR(255),
    scope VARCHAR(255),
    primary key (client_id)
);
drop table if exists oauth_client_details;
CREATE TABLE oauth_client_details (
    client_id varchar(255) NOT NULL,
    resource_ids varchar(255) DEFAULT NULL,
    client_secret varchar(255) DEFAULT NULL,
    scope varchar(255) DEFAULT NULL,
    authorized_grant_types varchar(255) DEFAULT NULL,
    web_server_redirect_uri varchar(255) DEFAULT NULL,
    authorities varchar(255) DEFAULT NULL,
    access_token_validity integer(11) DEFAULT NULL,
    refresh_token_validity integer(11) DEFAULT NULL,
    additional_information varchar(255) DEFAULT NULL,
    autoapprove varchar(255) DEFAULT NULL,
    primary key (client_id)
  );
drop table if exists oauth_access_token;
CREATE TABLE oauth_access_token (
  token_id VARCHAR(512),
  token LONGBLOB,
  authentication_id VARCHAR(255),
  user_name VARCHAR(255),
  client_id VARCHAR(255),
  authentication LONGBLOB,
  refresh_token VARCHAR(255)
);
drop table if exists oauth_refresh_token;
CREATE TABLE IF NOT EXISTS oauth_refresh_token(
  token_id VARCHAR(512),
  token LONGBLOB,
  authentication LONGBLOB
);
drop table if exists authority;
CREATE TABLE authority (
  id  integer,
  authority varchar(255),
  primary key (id)
);

drop table if exists oauth_code;
CREATE TABLE oauth_code (
  code VARCHAR(255), authentication LONGBLOB
);

drop table if exists oauth_approvals;
create table oauth_approvals (
    userId VARCHAR(255),
    clientId VARCHAR(255),
    scope VARCHAR(255),
    status VARCHAR(10),
    expiresAt DATETIME,
    lastModifiedAt DATETIME
);