USE pastoraldb;

-- =========================
-- FORMULARIOS (5)
-- =========================
INSERT INTO formulario (
alimentos, alquiler, aparatos_ortopedicos,
duracion, frecuencia,
higiene_limpieza, indumentaria, medicamentos,
modalidad, otros_1, otros_2,
servicios, valor,
direccion,
fecha_inicio, fecha_conclusion,
ficha_numero, nombre_entrevistador,
observaciones,
telefono_contacto,
condicion_vivienda, tenencia, tipo_vivienda,
id_parroquia
)
VALUES
(1,0,0,'3 meses','Mensual',1,0,1,'Efectivo','N/A','N/A',1,50000,'San José',CURDATE(),DATE_ADD(CURDATE(), INTERVAL 3 MONTH),'F001','Juan Perez','Caso vulnerable','88888888','Regular','Alquilada','Casa',1),

(1,1,0,'6 meses','Quincenal',1,1,0,'Transferencia','N/A','N/A',1,75000,'Cartago',CURDATE(),DATE_ADD(CURDATE(), INTERVAL 6 MONTH),'F002','Maria Lopez','Caso urgente','87777777','Mala','Prestada','Apartamento',1),

(0,1,1,'2 meses','Mensual',0,1,1,'Efectivo','N/A','N/A',1,30000,'Heredia',CURDATE(),DATE_ADD(CURDATE(), INTERVAL 2 MONTH),'F003','Carlos Ramirez','Caso aprobado','86666666','Buena','Propia','Casa',1),

(1,0,0,'1 mes','Semanal',1,0,0,'Efectivo','N/A','N/A',1,20000,'Alajuela',CURDATE(),DATE_ADD(CURDATE(), INTERVAL 1 MONTH),'F004','Ana Gomez','Caso en evaluación','85555555','Regular','Alquilada','Cuarto',1),

(1,1,1,'4 meses','Mensual',1,1,1,'Transferencia','N/A','N/A',1,90000,'Limón',CURDATE(),DATE_ADD(CURDATE(), INTERVAL 4 MONTH),'F005','Luis Perez','Caso cerrado','84444444','Mala','Precaria','Rancho',1);
