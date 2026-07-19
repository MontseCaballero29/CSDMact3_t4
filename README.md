<div align="center">

# DULCE LUNA

## CRUD CON SPRING BOOT, THYMELEAF, JPA Y MYSQL

**Actividad 3 — Tema 4**

**Estudiante:** Dalia Montserrat Caballero Silva  
**Carrera:** Ingeniería en Sistemas Computacionales  
**Materia:** Programación Web  
**Docente:** Adelina Martínez Nieto  

</div>

---

## Descripción del proyecto

**Dulce Luna** es una aplicación web para administrar los productos de una pastelería.

El proyecto implementa un CRUD completo conectado a una base de datos MySQL y utiliza una relación entre las entidades `Categoria` y `Producto`.

La aplicación permite crear, consultar, actualizar y eliminar productos. Los datos se guardan de forma permanente en MySQL y cada producto se relaciona con una categoría.

El CRUD puede probarse de dos maneras:

- Mediante vistas propias desarrolladas con Thymeleaf.
- Mediante endpoints REST utilizando Postman o Bruno.

---

## Entidades y relación JPA

El proyecto utiliza las siguientes entidades:

- `Categoria`: representa una clasificación para los productos de la pastelería.
- `Producto`: representa un producto registrado en el sistema.

La relación implementada es de **uno a muchos y muchos a uno**:

- Una `Categoria` puede tener muchos productos mediante `@OneToMany`.
- Cada `Producto` pertenece a una sola categoría mediante `@ManyToOne`.
- La llave foránea se almacena en la columna `categoria_id` de la tabla `productos`.

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

- Java 25
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA
- Thymeleaf
- Jakarta Validation
- MySQL Connector/J
- Maven Wrapper
- HTML
- CSS
- Postman o Bruno

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

- Crear productos.
- Listar todos los productos.
- Consultar el detalle de un producto.
- Actualizar productos existentes.
- Eliminar productos.
- Filtrar productos por categoría.
- Crear categorías.
- Listar categorías.
- Eliminar categorías que no tengan productos asociados.
- Relacionar cada producto con una categoría.
- Mostrar el nombre de la categoría en las vistas y respuestas JSON.
- Validar los datos enviados desde formularios y peticiones REST.
- Mostrar mensajes de error claros.
- Cargar categorías iniciales cuando la tabla se encuentra vacía.
- Persistir los datos en una base de datos MySQL.

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

En Windows, el proyecto puede ejecutarse desde la carpeta principal con el Maven Wrapper:

```powershell
.\mvnw.cmd clean spring-boot:run
```

En Linux o macOS:

```bash
./mvnw clean spring-boot:run
```

La aplicación se ejecuta en:

```text
http://localhost:8086
```

---

## Vistas web

| Función | URL |
|---|---|
| Página de inicio | `http://localhost:8086/` |
| Lista de productos | `http://localhost:8086/productos` |
| Registrar producto | `http://localhost:8086/productos/nuevo` |
| Lista de categorías | `http://localhost:8086/categorias` |

Desde las vistas web se pueden realizar las operaciones principales del CRUD sin utilizar un proyecto de frontend separado.

---

## Endpoints REST

### Categorías

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/categorias` | Consultar todas las categorías |
| POST | `/api/categorias` | Registrar una categoría |

### Productos

| Método | Endpoint | Operación |
|---|---|---|
| POST | `/api/productos` | Crear un producto |
| GET | `/api/productos` | Consultar todos los productos |
| GET | `/api/productos/{id}` | Consultar un producto |
| PUT | `/api/productos/{id}` | Actualizar un producto |
| DELETE | `/api/productos/{id}` | Eliminar un producto |
| GET | `/api/productos?categoriaId=1` | Filtrar productos por categoría |

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

Para pruebas locales debe utilizarse:

```text
http://localhost:8086
```

Para pruebas en el VPS debe utilizarse:

```text
http://54.83.75.25:8086
```

---

# Evidencias del funcionamiento

Las siguientes capturas demuestran el funcionamiento del CRUD, la persistencia en MySQL y la relación entre las entidades.

## 1. Entidades relacionadas en MySQL

La tabla `productos` contiene la llave foránea `categoria_id`, la cual relaciona cada producto con una categoría.

![Entidades Categoria y Producto relacionadas](capturas/01-entidades-relacionadas.png)

---

## 2. Crear un producto

La aplicación permite registrar un producto y seleccionar la categoría a la que pertenece.

![Creación de un producto](capturas/02-crear-producto.png)

---

## 3. Leer o listar productos

La lista muestra los productos registrados y el nombre de la categoría relacionada.

![Lista de productos](capturas/03-listar-productos.png)

---

## 4. Actualizar un producto

La aplicación permite modificar la información de un producto existente y guardar los cambios en MySQL.

![Actualización de un producto](capturas/04-actualizar-producto.png)

---

## 5. Eliminar un producto

La operación de eliminación remueve el producto de la base de datos y actualiza la lista.

![Eliminación de un producto](capturas/05-eliminar-producto.png)

---

## 6. Relación entre Producto y Categoria

La respuesta JSON muestra `categoriaId` y `categoriaNombre`, lo que permite comprobar la relación entre ambas entidades.

![Relación entre producto y categoría](capturas/06-relacion-categoria-producto.png)

---

## 7. Proyecto funcionando en el VPS

La aplicación se encuentra desplegada en un VPS y se ejecuta públicamente en el puerto `8086`.

![Proyecto funcionando en el VPS](capturas/07-proyecto-vps.png)

---

## Compilación del archivo JAR

En Windows:

```powershell
.\mvnw.cmd clean package -DskipTests
```

En Linux o macOS:

```bash
./mvnw clean package -DskipTests
```

El archivo JAR se genera dentro de:

```text
target/CSDMact3_t4-0.0.1-SNAPSHOT.jar
```

---

## Despliegue en el VPS

El proyecto utiliza el puerto `8086`, diferente a los puertos utilizados por las actividades anteriores.

En el VPS se configuraron las variables de entorno para la conexión con MySQL y el archivo JAR fue desplegado como:

```text
/home/ubuntu/CSDMact3_t4/dulce-luna.jar
```

La aplicación se ejecuta como un servicio de `systemd`, por lo que permanece activa aunque se cierre la conexión SSH y se inicia automáticamente al reiniciar el VPS.

La aplicación está disponible en:

```text
http://54.83.75.25:8086/
```

Los endpoints REST están disponibles en:

```text
http://54.83.75.25:8086/api/productos
```

```text
http://54.83.75.25:8086/api/categorias
```

---

# Respuestas para Classroom

## a) ¿Qué dos entidades elegiste y qué tipo de relación implementaste entre ellas?

Las dos entidades que elegí fueron `Categoria` y `Producto`. Implementé una relación de uno a muchos, porque una categoría puede tener varios productos. También se puede ver como una relación de muchos a uno desde `Producto`, ya que varios productos pueden pertenecer a una misma categoría. Para esto utilicé las anotaciones `@OneToMany` y `@ManyToOne`, y la llave foránea `categoria_id` se guarda en la tabla `productos`.

---

## b) ¿Qué es un Repository en Spring Data JPA y qué ventaja tiene sobre escribir las consultas SQL manualmente?

Un Repository en Spring Data JPA es una interfaz que permite trabajar con la base de datos. En mi proyecto los repositorios extienden de `JpaRepository`, por lo que puedo usar métodos como `save()`, `findAll()`, `findById()` y `deleteById()` sin tener que escribir las consultas SQL manualmente. La ventaja es que se reduce el código, se evita repetir consultas y el proyecto es más fácil de mantener.

---

## c) ¿Por qué es una buena práctica separar la lógica en una capa Service en lugar de colocarla directamente en el Controller?

Es una buena práctica usar una capa Service porque ahí se coloca la lógica del programa. De esta forma, el Controller solamente se encarga de recibir las peticiones y devolver las vistas o las respuestas JSON. En mi proyecto, el Service se encarga de validar que existan los productos y las categorías, relacionar cada producto con su categoría y realizar las operaciones de crear, consultar, actualizar y eliminar. Esto ayuda a que el código esté más ordenado y sea más fácil de modificar.

---

## d) Enlaces del proyecto

### Repositorio de GitHub

```text
https://github.com/MontseCaballero29/CSDMact3_t4
```

### Proyecto ejecutándose en el VPS

```text
http://54.83.75.25:8086/
```

### API de productos

```text
http://54.83.75.25:8086/api/productos
```

### API de categorías

```text
http://54.83.75.25:8086/api/categorias
```

### Colección de Postman

```text
https://github.com/MontseCaballero29/CSDMact3_t4/blob/main/postman/Dulce-Luna-CRUD.postman_collection.json
```

---

## Estado del proyecto

- CRUD completo implementado.
- Persistencia real con MySQL.
- Relación JPA entre `Categoria` y `Producto`.
- Vistas propias con Thymeleaf.
- Endpoints REST para Postman o Bruno.
- Separación mediante Repository, Service y Controller.
- Proyecto desplegado en el VPS en el puerto `8086`.
- Servicio configurado para permanecer activo después de cerrar la conexión SSH.