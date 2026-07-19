<div align="center">

# DULCE LUNA

## CRUD CON SPRING BOOT, THYMELEAF, JPA Y MYSQL

**Actividad 3 — Tema 4**

**Estudiante:** Dalia Montserrat Caballero Silva
**Carrera:** Ingeniería en Sistemas Computacionales

</div>

---

## Descripción del proyecto

**Dulce Luna** es una aplicación web para administrar los productos de una pastelería.

El proyecto implementa un CRUD completo conectado a una base de datos MySQL y utiliza una relación entre las entidades `Categoria` y `Producto`.

La aplicación permite realizar las operaciones de crear, consultar, actualizar y eliminar productos, conservando los datos en una base de datos real.

El CRUD puede probarse de dos maneras:

* Mediante vistas propias desarrolladas con Thymeleaf.
* Mediante endpoints REST utilizando Postman o Bruno.

---

## Entidades y relación JPA

El proyecto utiliza las siguientes entidades:

* `Categoria`: representa una clasificación para los productos de la pastelería.
* `Producto`: representa un producto registrado en el sistema.

La relación implementada es de **uno a muchos y muchos a uno**:

* Una `Categoria` puede tener muchos productos mediante `@OneToMany`.
* Cada `Producto` pertenece a una sola categoría mediante `@ManyToOne`.
* La llave foránea se almacena en la columna `categoria_id` de la tabla `productos`.

Al consultar un producto mediante la API se muestra tanto el identificador como el nombre de la categoría:

```json
{
  "categoriaId": 1,
  "categoriaNombre": "Pasteles"
}
```

De esta forma, la relación no se representa únicamente mediante un número.

---

## Tecnologías utilizadas

* Java 25
* Spring Boot 4.1.0
* Spring Web
* Spring Data JPA
* Thymeleaf
* Jakarta Validation
* MySQL Connector/J
* Maven
* HTML
* CSS
* Postman o Bruno

---

## Estructura principal del proyecto

```text
src/main/java/pasteleria
├── configuracion
│   └── DatosIniciales.java
├── controlador
│   ├── CategoriaControlador.java
│   ├── CategoriaRestControlador.java
│   ├── InicioControlador.java
│   ├── ManejadorErroresRest.java
│   ├── ProductoControlador.java
│   └── ProductoRestControlador.java
├── dto
│   ├── ProductoPeticion.java
│   └── ProductoRespuesta.java
├── excepcion
│   └── RecursoNoEncontradoExcepcion.java
├── modelo
│   ├── Categoria.java
│   └── Producto.java
├── repositorio
│   ├── CategoriaRepositorio.java
│   └── ProductoRepositorio.java
├── servicio
│   ├── CategoriaServicio.java
│   └── ProductoServicio.java
└── PasteleriaApplication.java
```

Los recursos web se encuentran en:

```text
src/main/resources
├── static
│   └── css
├── templates
│   ├── categorias
│   ├── productos
│   └── index.html
└── application.properties
```

---

## Funcionalidades implementadas

* Crear productos.
* Listar todos los productos.
* Consultar el detalle de un producto.
* Actualizar productos existentes.
* Eliminar productos.
* Filtrar productos por categoría.
* Crear categorías.
* Listar categorías.
* Eliminar categorías que no tengan productos asociados.
* Relacionar cada producto con una categoría.
* Mostrar el nombre de la categoría en las vistas y respuestas JSON.
* Validar los datos enviados desde formularios y peticiones REST.
* Mostrar mensajes de error claros.
* Cargar categorías iniciales cuando la tabla se encuentra vacía.
* Persistir los datos en una base de datos MySQL.

---

## Configuración de MySQL

Primero debe crearse la base de datos:

```sql
CREATE DATABASE pasteleria_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;
```

También puede utilizarse el script incluido en el proyecto:

```text
database/crear_bd.sql
```

La aplicación utiliza variables de entorno para evitar publicar la contraseña de MySQL dentro del repositorio:

```properties
spring.datasource.url=${DB_URL:jdbc:mysql://localhost:3306/pasteleria_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Mexico_City}
spring.datasource.username=${DB_USER:pasteleria_user}
spring.datasource.password=${MOON}
```

### Variables de entorno en Windows PowerShell

```powershell
$env:MOON="TU_PASSWORD"
$env:DB_USER="pasteleria_user"
```

Si se utiliza una dirección diferente para la base de datos:

```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/pasteleria_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Mexico_City"
```

### Variables de entorno en Linux o VPS

```bash
export MOON='TU_PASSWORD'
export DB_USER='pasteleria_user'
export DB_URL='jdbc:mysql://localhost:3306/pasteleria_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Mexico_City'
```

---

## Ejecución local

Para ejecutar el proyecto desde la carpeta principal:

```bash
mvn clean spring-boot:run
```

La aplicación se ejecuta en el puerto `8086`:

```text
http://localhost:8086
```

---

## Vistas web

| Función             | URL                                     |
| ------------------- | --------------------------------------- |
| Página de inicio    | `http://localhost:8086/`                |
| Lista de productos  | `http://localhost:8086/productos`       |
| Registrar producto  | `http://localhost:8086/productos/nuevo` |
| Lista de categorías | `http://localhost:8086/categorias`      |

Desde las vistas web se pueden realizar las operaciones principales del CRUD sin utilizar un proyecto de frontend separado.

---

## Endpoints REST

### Categorías

| Método | Endpoint          | Descripción                    |
| ------ | ----------------- | ------------------------------ |
| GET    | `/api/categorias` | Consultar todas las categorías |
| POST   | `/api/categorias` | Registrar una categoría        |

### Productos

| Método | Endpoint                       | Operación                       |
| ------ | ------------------------------ | ------------------------------- |
| POST   | `/api/productos`               | Crear un producto               |
| GET    | `/api/productos`               | Consultar todos los productos   |
| GET    | `/api/productos/{id}`          | Consultar un producto           |
| PUT    | `/api/productos/{id}`          | Actualizar un producto          |
| DELETE | `/api/productos/{id}`          | Eliminar un producto            |
| GET    | `/api/productos?categoriaId=1` | Filtrar productos por categoría |

---

## Ejemplo para crear o actualizar un producto

La siguiente petición puede enviarse en formato JSON desde Postman o Bruno:

```json
{
  "nombre": "Pastel Luna",
  "sabor": "Chocolate",
  "descripcion": "Pastel de chocolate con decoración lunar",
  "precio": 450.00,
  "disponible": true,
  "categoriaId": 1
}
```

---

## Ejemplo de respuesta con la relación

La respuesta muestra el identificador y el nombre de la categoría relacionada:

```json
{
  "id": 1,
  "nombre": "Pastel Luna",
  "sabor": "Chocolate",
  "descripcion": "Pastel de chocolate con decoración lunar",
  "precio": 450.00,
  "disponible": true,
  "categoriaId": 1,
  "categoriaNombre": "Pasteles"
}
```

---

## Colección de Postman

La colección para probar los endpoints está incluida en:

```text
postman/Dulce-Luna-CRUD.postman_collection.json
```

Después de importarla en Postman, debe modificarse la variable `baseUrl`.

Para pruebas locales:

```text
http://localhost:8086
```

Para las pruebas en el VPS:

```text
http://TU_IP_PUBLICA:8086
```

---

# Evidencias del funcionamiento

Las siguientes capturas demuestran el funcionamiento del CRUD y la relación entre las entidades.

Las imágenes deben guardarse dentro de la carpeta:

```text
capturas/
```

## 1. Entidades relacionadas en MySQL

En esta captura deben mostrarse las tablas `categorias` y `productos`, incluyendo la llave foránea `categoria_id`.

![Entidades Categoria y Producto relacionadas](capturas/01-entidades-relacionadas.png)

---

## 2. Crear un producto

Captura del formulario o de la petición POST utilizada para registrar un producto con su categoría.

![Creación de un producto](capturas/02-crear-producto.png)

---

## 3. Leer o listar productos

Captura de la lista de productos desde Thymeleaf o de la petición GET realizada desde Postman o Bruno.

![Lista de productos](capturas/03-listar-productos.png)

---

## 4. Actualizar un producto

Captura de la edición de un producto existente o de la petición PUT.

![Actualización de un producto](capturas/04-actualizar-producto.png)

---

## 5. Eliminar un producto

Captura que demuestre la eliminación de un producto y la actualización de la lista.

![Eliminación de un producto](capturas/05-eliminar-producto.png)

---

## 6. Relación entre Producto y Categoria

Captura del detalle de un producto o de la respuesta JSON donde se muestre `categoriaNombre`.

![Relación entre producto y categoría](capturas/06-relacion-categoria-producto.png)

---

## 7. Proyecto funcionando en el VPS

Captura de la aplicación abierta desde la dirección IP pública y el puerto `8086`.

![Proyecto funcionando en el VPS](capturas/07-proyecto-vps.png)

---

## Compilación del archivo JAR

Para generar el archivo ejecutable:

```bash
mvn clean package
```

El archivo JAR se generará dentro de:

```text
target/CSDMact3_t4-0.0.1-SNAPSHOT.jar
```

---

## Despliegue en el VPS

El proyecto utiliza el puerto `8086`, diferente a los puertos utilizados por las actividades anteriores.

Primero deben configurarse las variables de entorno:

```bash
export MOON='TU_PASSWORD_REAL'
export DB_USER='pasteleria_user'
export DB_URL='jdbc:mysql://localhost:3306/pasteleria_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=America/Mexico_City'
```

Después se ejecuta el archivo JAR:

```bash
java -jar CSDMact3_t4-0.0.1-SNAPSHOT.jar
```

La aplicación podrá consultarse desde:

```text
http://TU_IP_PUBLICA:8086/
```

Los endpoints REST estarán disponibles desde:

```text
http://TU_IP_PUBLICA:8086/api/productos
```

```text
http://TU_IP_PUBLICA:8086/api/categorias
```

---

# Respuestas para Classroom

## a) ¿Qué dos entidades elegiste y qué tipo de relación implementaste entre ellas?

Elegí las entidades `Categoria` y `Producto`.

Implementé una relación de uno a muchos desde `Categoria` hacia `Producto` mediante la anotación `@OneToMany`. También implementé una relación de muchos a uno desde `Producto` hacia `Categoria` mediante `@ManyToOne`.

La llave foránea `categoria_id` se almacena en la tabla `productos`. Esto permite que varios productos pertenezcan a una misma categoría, mientras que cada producto solo puede tener una categoría.

---

## b) ¿Qué es un Repository en Spring Data JPA y qué ventaja tiene sobre escribir las consultas SQL manualmente?

Un Repository es una interfaz encargada de realizar las operaciones de acceso a la base de datos.

Al extender la interfaz `JpaRepository`, Spring Data JPA proporciona automáticamente métodos para guardar, consultar, actualizar y eliminar registros, como `save()`, `findAll()`, `findById()` y `deleteById()`.

Su principal ventaja es que evita escribir manualmente gran parte de las consultas SQL, reduce código repetitivo y facilita el mantenimiento de la aplicación.

También permite crear consultas personalizadas utilizando el nombre de los métodos, sin tener que escribir directamente el SQL en muchos casos.

---

## c) ¿Por qué es una buena práctica separar la lógica en una capa Service en lugar de colocarla directamente en el Controller?

La capa Service concentra la lógica de negocio de la aplicación.

Esto permite que el Controller se encargue únicamente de recibir las peticiones, procesar los datos necesarios y devolver una vista o una respuesta JSON.

Separar estas responsabilidades hace que el código sea más organizado, reutilizable, fácil de mantener y sencillo de probar.

En este proyecto, la capa Service se encarga de validar que las categorías y los productos existan, asociar un producto con la categoría seleccionada y controlar las operaciones de creación, consulta, actualización y eliminación.

---

## d) Enlaces del proyecto

### Repositorio de GitHub

```text
https://github.com/MontseCaballero29/CSDMact3_t4
```

### Proyecto ejecutándose en el VPS

```text
http://TU_IP_PUBLICA:8086/
```

### Colección de Postman

```text
postman/Dulce-Luna-CRUD.postman_collection.json
```

---

## Estado del proyecto

* CRUD completo implementado.
* Persistencia real con MySQL.
* Relación JPA entre `Categoria` y `Producto`.
* Vistas propias con Thymeleaf.
* Endpoints REST para Postman o Bruno.
* Separación mediante Repository, Service y Controller.
* Configuración mediante variables de entorno.
* Preparado para ejecutarse localmente y desplegarse en el VPS.
