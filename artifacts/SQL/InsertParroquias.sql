USE pastoraldb;

-- 1. Insert parroquias primero
INSERT INTO parroquia (nombre, provincia, canton, direccion, telefono, email, sector_filial, codigo_acceso)
VALUES
('Parroquia San José', 'San José', 'Central', 'Barrio Catedral', '22223333', 'sanjose@iglesia.cr', 'Centro', 'SJ123'),
('Parroquia Cartago', 'Cartago', 'Central', 'Basílica', '25511234', 'cartago@iglesia.cr', 'Basílica', 'CA456');

-- 2. Insert usuario (FK REQUIRED)
INSERT INTO usuario (nombre, apellido, email, password, rol, id_parroquia)
VALUES ('Admin', 'Sistema', 'admin@parroquia.com', '1234', 'ADMIN', 1);USE pastoraldb;
