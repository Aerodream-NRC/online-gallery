CREATE USER artwork_service WITH PASSWORD 'password';
CREATE USER user_service WITH PASSWORD 'password';
CREATE USER keycloak WITH PASSWORD 'password';

CREATE DATABASE users_db OWNER user_service;
CREATE DATABASE keycloak_db OWNER keycloak;
CREATE DATABASE artworks_db OWNER artwork_service;