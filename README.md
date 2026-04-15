# Gestor de Ayuda de Parroquias

## Curso
Diseño y Construcción de Componentes  

## Profesor
Bernal Fernandez Bonilla  

---

## Integrantes

- Esneider Alonso Camacho Rodriguez  
- Santiago Villalta Montero  
- Oslyn Delgado Rojas  
- Juan Daniel Jiménez Hidalgo  
- David Cerdas Pérez  

---

## Descripción del Proyecto

El sistema **Gestor de Ayuda de Parroquias** permite registrar, gestionar y dar seguimiento a beneficiarios dentro de una parroquia.

---

## Tecnologías

- Java (Swing)  
- JPA / Hibernate  
- MySQL  

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/oslyn2808/ProyectoParroquia.git
cd ProyectoParroquia
```

---

### 2. Iniciar MySQL

```bash
sudo service mysql start
```

---

### 3. Crear la base de datos

```bash
mysql -u root < artifacts/SQL/BD.sql
```

Este script crea la base de datos sin tablas.

---

### 4. Importar el proyecto en Eclipse

1. Abrir Eclipse  
2. Ir a:  
   ```
   File → Import
   ```
3. Seleccionar:  
   ```
   Existing Projects into Workspace
   ```
4. Seleccionar la carpeta:  
   ```
   ProyectoParroquia/SistemaPastoralSocialV
   ```
5. Finalizar  

---

### 5. Ejecutar la aplicación

En Eclipse:

- Click derecho sobre el proyecto  
- Seleccionar:  
  ```
  Run As → Java Application
  ```
- Clase principal:  
  ```
  presentacion.MainApp
  ```

Al iniciar la aplicación:

- Hibernate crea automáticamente las tablas  

---

### 6. Cargar datos semilla

```bash
mysql -u root < artifacts/SQL/InsertParroquias.sql
```

**Notas:**
- Hibernate crea las tablas automáticamente  
- El script `InsertParroquias.sql` se utiliza únicamente para login y pruebas  

---

## Acceso al sistema

**Usuario:**
```
admin@parroquia.com
```

**Contraseña:**
```
1234
```

---

## Flujo de prueba

1. Iniciar sesión  
2. Crear formulario  
3. Listar formularios  
4. Crear adendum  
5. Listar datos  

---

## Consideraciones técnicas

- El esquema de base de datos es generado por Hibernate  
- Los scripts SQL no crean tablas, solo insertan datos  
- Se utiliza el patrón DAO para acceso a datos  
- Se aplican validaciones antes de persistir información  

---

## Reinicio del sistema

```bash
mysql -u root < artifacts/SQL/BD.sql
```

Luego:

1. Ejecutar la aplicación  
2. Ejecutar el script de datos semilla  

---

## Estado del Proyecto

Sistema funcional con persistencia completa y flujo de gestión de beneficiarios.

