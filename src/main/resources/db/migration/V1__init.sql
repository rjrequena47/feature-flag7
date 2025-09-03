-- V1__init.sql
-- Migración inicial para probar la conexión
-- No crea tablas aún, solo asegura que Flyway se ejecute

-- Podemos crear una tabla dummy que luego borraremos
CREATE TABLE flyway_test (
    id SERIAL PRIMARY KEY,
    mensaje VARCHAR(100) NOT NULL
);

INSERT INTO flyway_test (mensaje) VALUES ('Flyway funcionando correctamente');
