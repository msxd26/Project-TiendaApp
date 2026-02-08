# TiendaApp

> API REST para gestión de tienda: inventario, ventas, ingresos, usuarios y roles. Backend con **Spring Boot 3**, **JWT** y **PostgreSQL**.

[![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-4169E1?logo=postgresql&logoColor=white)](https://www.postgresql.org/)

---

## Contenido

- [Características](#características)
- [Requisitos](#requisitos)
- [Tecnologías](#tecnologías)
- [Inicio rápido](#inicio-rápido)
- [Configuración](#configuración)
- [Documentación API](#documentación-api)
- [Autenticación](#autenticación)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Licencia](#licencia)

---

## Características

- **CRUD** de artículos, categorías, personas (clientes/proveedores), usuarios
- **Ingresos** y **ventas** con detalle (ítems, cantidades, totales)
- **Autenticación JWT** (login por email/contraseña)
- **Control de acceso por roles:** ADMIN, GERENTE, VENDEDOR, ALMACENERO, INVITADO
- **Validación** de datos (Bean Validation + validadores personalizados)
- **Manejo centralizado de errores** con respuestas JSON tipadas
- **Documentación OpenAPI/Swagger** integrada
- **Docker Compose** para base de datos y datos de ejemplo

---

## Requisitos

| Herramienta | Versión |
|-------------|---------|
| Java | 17+ |
| Maven | 3.8+ |
| Docker & Docker Compose | (para BD) |
| PostgreSQL | 15+ (si no usas Docker) |

---

## Tecnologías

| Categoría | Stack |
|-----------|--------|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.5, Spring Web, Spring Data JPA |
| Seguridad | Spring Security, JWT (jjwt) |
| Base de datos | PostgreSQL |
| Utilidades | Lombok, MapStruct, Bean Validation |
| Documentación | SpringDoc OpenAPI (Swagger UI) |

---

## Inicio rápido

### 1. Clonar e ingresar al proyecto

```bash
git clone https://github.com/msxd26/Project-TiendaApp.git
cd Project-TiendaApp
```

### 2. Levantar la base de datos

```bash
docker compose up -d
```

Los scripts en `sql/` (esquema y datos iniciales) se ejecutan al iniciar el contenedor.

### 3. Ejecutar la aplicación

**Linux / macOS:**

```bash
./mvnw spring-boot:run
```

**Windows (PowerShell o CMD):**

```bash
mvnw.cmd spring-boot:run
```

### 4. Probar la API

| Recurso | URL |
|--------|-----|
| API base | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |

---

## Configuración

Archivo: `src/main/resources/application.properties`

| Propiedad | Descripción | Por defecto |
|-----------|-------------|-------------|
| `spring.datasource.url` | URL JDBC | `jdbc:postgresql://localhost:5432/tienda` |
| `spring.datasource.username` | Usuario BD | `user01` |
| `spring.datasource.password` | Contraseña BD | `password` |
| `spring.jpa.show-sql` | Mostrar SQL en consola | `true` |

**Perfiles:** puedes crear `application-dev.properties` y `application-prod.properties` y ejecutar con:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## Documentación API

Con la app en ejecución:

1. Abre **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
2. Para endpoints protegidos: **Authorize** → pega el token JWT con prefijo `Bearer `.

El esquema OpenAPI 3 está en: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

---

## Autenticación

### Login (obtener token)

**`POST`** `/login`

**Body (JSON):**

```json
{
  "email": "admin@tienda.com",
  "password": "tu_contraseña"
}
```

**Respuesta (200):** se devuelve `token`, `email` y un mensaje. Usa el `token` en las peticiones protegidas.

### Uso del token

Incluye en la cabecera de cada petición protegida:

```
Authorization: Bearer <tu_token>
```

### Registro de usuario (público)

**`POST`** `/usuario/registrar`

Body según el DTO de usuario (nombre, email, password, etc.). El usuario se crea sin rol ADMIN.

### Endpoints públicos

- `POST /login` — Iniciar sesión
- `POST /usuario/registrar` — Registrar usuario
- `GET /usuario/` — Listar usuarios (requiere rol ADMIN)
- Rutas de Swagger/OpenAPI

El resto de endpoints requieren JWT válido y el rol correspondiente.

---

## Estructura del proyecto

```
Project-TiendaApp/
├── sql/
│   ├── create_schema.sql    # Tablas de la BD
│   └── data.sql             # Datos iniciales (roles, usuarios, categorías, etc.)
├── src/main/java/pe/jsaire/tiendaapp/
│   ├── config/              # Configuración OpenAPI/Swagger
│   ├── controllers/         # REST: Articulo, Categoria, Persona, Usuario, Ingreso, Venta
│   ├── infraestructures/
│   │   ├── abstract_services/
│   │   └── services/        # Lógica de negocio
│   ├── mappers/             # MapStruct (DTO ↔ Entity)
│   ├── models/
│   │   ├── dto/             # request & response
│   │   ├── entities/        # JPA
│   │   └── repositories/    # Spring Data JPA
│   ├── security/            # JWT, filtros, SecurityConfig
│   ├── utils/
│   │   ├── enums/           # RolNombre, TipoComprobante, EstadoTransaccion, etc.
│   │   ├── exceptions/      # Excepciones de negocio
│   │   └── validations/     # Validadores personalizados
│   └── TiendaAppApplication.java
├── src/main/resources/
│   └── application.properties
├── docker-compose.yml       # PostgreSQL
├── pom.xml
└── README.md
```

---

## Licencia

Proyecto de uso educativo/demostrativo. Ver repositorio para más detalles.
