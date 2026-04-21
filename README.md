# Gestor de Ayuda de Parroquias

## Curso
**Diseño y Construcción de Componentes**

## Universidad
**Universidad Latina de Costa Rica**

## Profesor
**Bernal Fernandez Bonilla**

---

## Integrantes

- Esneider Alonso Camacho Rodriguez  
- Santiago Villalta Montero   — [GitHub](https://github.com/santivm0710-maker)  
- Oslyn Delgado Rojas — [GitHub](https://github.com/oslyn2808)  
- Juan Daniel Jiménez Hidalgo — [GitHub](https://github.com/JDanhy07)
- David Cerdas Pérez — [LinkedIn](https://www.linkedin.com/in/david-j-cerdas-a56a6925/)  

---

## Metodología de Trabajo

Se utilizó la metodología ágil **SCRUM** para la organización del proyecto, apoyándonos en Trello para la distribución de tareas y seguimiento del avance:

[Trello SCRUM Board](https://trello.com/b/xlwOk0Jl/scrum-board-columns)

---

## Descripción del Proyecto

El sistema **Gestor de Ayuda de Parroquias** permite registrar, gestionar y dar seguimiento a beneficiarios, incluyendo formularios, grupo familiar y control de gastos mediante adendums.

A nivel técnico, fue desarrollado en **Java** utilizando **JPA** con **Hibernate** como ORM, lo que permite mapear automáticamente las entidades a tablas en MySQL y gestionar la persistencia sin SQL manual.

Se implementa el patrón **DAO** para separar la lógica de negocio del acceso a datos, logrando una arquitectura en capas, mantenible y escalable.

---

## Tecnologías

- Java (OpenJDK 21.0.9)  
- Java Swing (Interfaz de usuario)  
- JPA / Hibernate (ORM para persistencia)  
- MySQL 8.0.45 (Ubuntu 24.04.1 LTS)  

---

## Herramientas de Desarrollo

- Eclipse IDE for Java Developers 2025-12 (4.38.0)  
- Windows 11  
- GitHub  
- WSL 2.6.3.0 (Windows Subsystem for Linux)  
- Ubuntu 24.04.1 LTS  

---

## Instalación

**Video demostrativo:**  
[Ver video en YouTube](https://youtu.be/3JNZR4aXmUs?si=u_EkVuFD0UsUQapS)

### 1. Clonar el repositorio

```bash
git clone https://github.com/oslyn2808/ProyectoParroquia.git
cd ProyectoParroquia
```

### 2. Iniciar MySQL

```bash
sudo service mysql start
```

### 3. Crear la base de datos

```bash
mysql -u root < artifacts/SQL/BD.sql
```

> Este script crea la base de datos sin tablas.  
> Las tablas serán generadas automáticamente por Hibernate al ejecutar la aplicación.

### 4. Importar el proyecto en Eclipse

1. Abrir Eclipse  
2. Ir a `File → Import`  
3. Seleccionar `Existing Projects into Workspace`  
4. Seleccionar la carpeta `SistemaPastoralSocialV`  
5. Finalizar  

### 5. Importar librerías

1. Click derecho sobre el proyecto  
2. Ir a `Build Path → Configure Build Path`  
3. Abrir la pestaña **Libraries**  
4. Click en **Add JARs**  
5. Seleccionar todos los archivos `.jar` ubicados en:

```text
artifacts/Librerias/
```

> Para futuras versiones, se recomienda configurar las librerías con **rutas relativas en `.classpath`** en lugar de rutas absolutas, para evitar tener que recargarlas manualmente al instalar el proyecto en otra computadora.

### 6. Ejecutar la aplicación

1. Click derecho sobre el proyecto  
2. Seleccionar `Run As → Java Application`  
3. Ejecutar la clase principal:

```text
presentacion.MainApp
```

### 7. Cargar datos semilla

```bash
mysql -u root < artifacts/SQL/Seed1ParroquiasUsuarioAdmin.sql
```

---

## Acceso al Sistema

**Usuario:** `admin@parroquia.com`  
**Contraseña:** `1234`

