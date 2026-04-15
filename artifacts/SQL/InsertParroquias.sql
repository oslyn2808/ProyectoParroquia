USE pastoraldb;

INSERT INTO parroquia (nombre, provincia, canton, direccion, telefono, email, sector_filial, codigo_acceso)
VALUES
('Parroquia San José', 'San José', 'Central', 'Barrio Catedral, Avenida 2', '22223333', 'sanjose@iglesia.cr', 'Sector Centro', 'SJ123'),
('Parroquia Nuestra Señora de los Ángeles', 'Cartago', 'Central', 'Basílica de Cartago', '25511234', 'cartago@iglesia.cr', 'Sector Basílica', 'CA456'),
('Parroquia San Isidro Labrador', 'Heredia', 'San Isidro', 'Frente al parque central', '22667788', 'heredia@iglesia.cr', 'Sector Norte', 'HE789'),
('Parroquia San Rafael Arcángel', 'Alajuela', 'San Rafael', 'Costado oeste de la iglesia', '24335566', 'alajuela@iglesia.cr', 'Sector Oeste', 'AL321'),
('Parroquia Espíritu Santo', 'Limón', 'Limón', 'Centro de Limón, calle principal', '27581234', 'limon@iglesia.cr', 'Sector Caribe', 'LI654');

INSERT INTO usuario (nombre, apellido, email, password, rol, id_parroquia)
VALUES ('Administrator', 'Test', 'admin@parroquia.com', '1234', 'ADMIN', 1);
