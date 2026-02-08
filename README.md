# TiendaApp — API REST Backend

API REST para gestión de tienda: artículos, categorías, personas (clientes/proveedores), usuarios, ingresos y ventas. Desarrollada con **Spring Boot 3**, **Spring Security** (JWT) y **PostgreSQL**.

---

## Tabla de contenidos

- [Requisitos](#requisitos)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Configuración](#configuración)
- [Documentación API](#documentación-api)
- [Autenticación](#autenticación)
- [Análisis y mejoras](#análisis-y-mejoras)
- [Licencia](#licencia)

---

## Requisitos

- **Java 17**
- **Maven 3.8+**
- **Docker** y **Docker Compose** (para la base de datos)
- **PostgreSQL 15+** (si no usas Docker)

---

## Tecnologías

| Tecnología | Uso |
|------------|-----|
| Java 17 | Lenguaje base |
| Spring Boot 3.5 | Framework |
| Spring Data JPA | Persistencia |
| Spring Security | Autenticación y autorización |
| JWT (jjwt 0.13) | Tokens de sesión |
| PostgreSQL | Base de datos |
| MapStruct | Mapeo DTO ↔ entidades |
| Lombok | Reducción de boilerplate |
| SpringDoc OpenAPI 2 | Documentación Swagger/OpenAPI 3 |
| Docker | Contenedor de PostgreSQL |

---

## Arquitectura

```
src/main/java/pe/jsaire/tiendaapp/
├── config/           # Swagger/OpenAPI
├── controllers/      # REST (Articulo, Categoria, Persona, Usuario, Ingreso, Venta)
├── infraestructures/
│   ├── abstract_services/   # Interfaces de servicios
│   └── services/            # Implementaciones
├── mappers/          # MapStruct DTO ↔ Entity
├── models/
│   ├── dto/request & response
│   ├── entities/
│   └── repositories/
├── security/         # JWT, filtros, SecurityConfig
├── utils/
│   ├── enums/        # RolNombre, TipoComprobante, etc.
│   ├── exceptions/   # Excepciones de negocio
│   └── validations/  # Validadores personalizados
└── TiendaAppApplication.java
```

- **Entidades principales:** Categoria, Articulo, Persona, Usuario, Rol, Ingreso, DetalleIngreso, Venta, DetalleVenta.
- **Seguridad:** login con email/password; JWT en cabecera `Authorization: Bearer <token>`; roles: ADMIN, GERENTE, VENDEDOR, ALMACENERO, INVITADO.
- **Documentación:** Swagger UI en `/swagger-ui.html` (o `/swagger-ui/index.html`).

---

## Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/msxd26/Project-TiendaApp.git
cd Project-TiendaApp
```

### 2. Levantar la base de datos con Docker

```bash
docker compose up -d
```

Comprueba que el contenedor esté en ejecución:

```bash
docker ps
```

El script `sql/create_schema.sql` y los datos iniciales en `sql/data.sql` se cargan automáticamente al crear el contenedor.

### 3. Ejecutar la aplicación

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

La API quedará disponible en:

- **Base URL:** `http://localhost:8080`
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`  
  (o `http://localhost:8080/swagger-ui/index.html`)

---

## Configuración

Archivo principal: `src/main/resources/application.properties`.

| Propiedad | Descripción | Valor por defecto |
|-----------|-------------|-------------------|
| `spring.datasource.url` | JDBC PostgreSQL | `jdbc:postgresql://localhost:5432/tienda` |
| `spring.datasource.username` | Usuario DB | `user01` |
| `spring.datasource.password` | Contraseña DB | `password` |
| `spring.jpa.show-sql` | Mostrar SQL en consola | `true` |

Para entornos adicionales puedes usar perfiles:

- `application-dev.properties` (desarrollo)
- `application-prod.properties` (producción)

Y ejecutar con: `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`

---

## Documentación API

Con la aplicación en marcha:

1. Abre **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
2. Los endpoints protegidos requieren token JWT: en Swagger, usa **Authorize** y pega el token con prefijo `Bearer `.

Esquema OpenAPI 3 en JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

---

## Autenticación

### Login (obtener JWT)

- **Método:** `POST`
- **URL:** `http://localhost:8080/login`
- **Body (JSON):**

```json
{
  "email": "admin@tienda.com",
  "password": "tu_password"
}
```

- **Respuesta (200):** incluye `token`, `email` y mensaje. Usa el valor de `token` en las peticiones protegidas.

### Uso del token

En cada petición a recursos protegidos, añade la cabecera:

```
Authorization: Bearer <tu_token>
```

### Registro de usuario (público)

- **Método:** `POST`
- **URL:** `http://localhost:8080/usuario/registrar`
- **Body:** según DTO `UsuarioRequest` (nombre, email, password, etc.). El usuario se crea sin rol ADMIN por defecto.

### Usuarios de prueba (tras ejecutar `data.sql`)

En `data.sql` se insertan usuarios de ejemplo. Las contraseñas están hasheadas con BCrypt; para pruebas reales conviene resetearlas desde la app (registro o un endpoint de cambio de contraseña) o generar nuevos hashes y actualizar el script.

---

## Análisis y mejoras

Resumen de lo revisado en el proyecto y recomendaciones para hacer la aplicación más robusta.

### Lo que está bien resuelto

- Uso de **Spring Security + JWT** con filtros y sesión stateless.
- **Control de excepciones** centralizado con `@ControllerAdvice` y DTO de error.
- **Validación** con Bean Validation y validadores personalizados (ej. email único).
- **Roles y permisos** con `@PreAuthorize` en controladores.
- **Transacciones** con `@Transactional` en servicios.
- **MapStruct** para separar DTOs y entidades.
- **Docker Compose** para levantar PostgreSQL y datos iniciales.
- **Documentación** con SpringDoc OpenAPI.

### Mejoras aplicadas en el código

- **GlobalExceptionController:** manejo de `IngresoNotFoundException` y `UsuarioNotFoundException`; `HttpMessageNotReadableException` devuelve **400 Bad Request** en lugar de 404; corrección del nombre del controlador (typo “Excepion” → “Exception”).
- **SecurityConfig:** permiso explícito para `POST /login`; ajuste de CORS (evitar `allowCredentials(true)` con `allowedOrigins("*")`, que no es válido en navegadores).
- **UsuarioController:** el método `delete` llama al servicio en lugar de devolver siempre 404; en `removeRol` se usa `@PathVariable` para `rolNombre` según la ruta `/{id}/roles/{rolNombre}`.

### Mejoras recomendadas (próximos pasos)

1. **Seguridad**
   - Externalizar la **clave JWT** en `application.properties` (o variables de entorno) y usarla en `TokenJwtConfig`; no generar una clave aleatoria en tiempo de ejecución en producción.
   - Definir **orígenes CORS** concretos en producción (lista de dominios) en lugar de `*`.
   - No exponer la contraseña en la respuesta de login (revisar que el body de éxito no incluya el password).

2. **API y consistencia**
   - Añadir **listados paginados** para recursos principales: artículos, categorías, personas, ventas, ingresos (por ejemplo `GET /articulo?page=0&size=10`).
   - Endpoint de **listado sin paginación** donde tenga sentido (ej. categorías para combos).
   - Unificar códigos HTTP y formato de respuestas (ej. 204 sin body en deletes).

3. **Robustez**
   - **Health checks:** añadir Spring Boot Actuator con `management.endpoints.web.exposure.include=health` y, si aplica, `info`.
   - **Perfiles:** separar `application-dev.properties` y `application-prod.properties` (URL de BD, `show-sql`, nivel de log, CORS).
   - **Logging:** configurar niveles por paquete (ej. `pe.jsaire.tiendaapp`) y para SQL en desarrollo.

4. **Tests**
   - Tests de integración para flujos críticos: login, CRUD de artículos/ventas, permisos por rol.
   - Tests unitarios de servicios (lógica de negocio, excepciones).
   - Uso de `@WithMockUser` o JWT en tests de controladores.

5. **Base de datos**
   - Revisar que las contraseñas en `data.sql` sean hashes BCrypt válidos para los usuarios de prueba; si no, generarlas desde la app o con una utilidad.
   - Considerar **índices** en columnas usadas en filtros y búsquedas (email, nombre, fechas).

6. **Otros**
   - Corregir typo en nombre del controlador de excepciones y mantener el nombre del archivo alineado.
   - Documentar en Swagger los códigos de error (400, 401, 404, 500) y el esquema de `ErrorResponse`.
   - Opcional: **rate limiting** y **auditoría** (quién y cuándo modificó datos sensibles).

Con estas mejoras, la aplicación gana en seguridad, mantenibilidad y claridad para equipos y despliegues.

---

## Licencia

Proyecto de uso educativo/demostrativo. Revisar licencia en el repositorio si se reutiliza código.
