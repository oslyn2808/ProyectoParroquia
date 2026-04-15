Gestor de Ayuda de Parroquias

Curso
Diseño y Construcción de Componentes

Profesor
Bernal Fernandez Bonilla

Integrantes
- Esneider Alonso Camacho Rodriguez
- Santiago Villalta Montero
- Oslyn Delgado Rojas
- Juan Daniel Jiménez Hidalgo
- David Cerdas Pérez

Descripción del Proyecto
El sistema Gestor de Ayuda de Parroquias permite registrar, gestionar y dar seguimiento a beneficiarios dentro de una parroquia.

Tecnologías
- Java (Swing)
- JPA / Hibernate
- MySQL

Instalación
1. Clonar repositorio
git clone https://github.com/oslyn2808/ProyectoParroquia.git

cd ProyectoParroquia

2. Iniciar MySQL
sudo service mysql start

3. Crear base de datos
mysql -u root < artifacts/SQL/BD.sql

4. Importar el proyecto en Eclipse

Abrir Eclipse

Ir a:
File → Import

Seleccionar:
Existing Projects into Workspace

Seleccionar la carpeta:
ProyectoParroquia/SistemaPastoralSocialV

Run as Java application:
Clase: presentacion.MainApp

5. Cargar datos semilla para logear y testear:
Notas
- Hibernate crea tablas automáticamente.
- InsertParroquias.sql solo es para login y testing.

mysql -u root < artifacts/SQL/InsertParroquias.sql

Credenciales
Usuario: admin@parroquia.com
Password: 1234

Flujo de prueba
1. Login
2. Crear formulario
3. Listar formulario
4. Crear adendum
5. Listar datos
