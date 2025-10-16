# SISTEMA DE TIENDA BACKEND + JWT

 Esta API esta desarrollada con Spring boot.Esta API esta desarrollada con Spring boot.
 

------------

### Tecnologias usadas Tecnologias usadas

------------
- Java 17
- Spring Data JPA
- Spring Security
- Docker
- PostgreSQL

#### Instrucciones de instalación

------------

Sigue estos pasos para configurar y ejecutar el proyecto en tu entorno local.

##### 1. Clonar el repositorio
```
git clone https://github.com/msxd26/Project-TiendaApp.git
cd Project-TiendaApp
```

##### 2. Configurar y levantar
El archivo ```docker-compose.yml``` está dentro del directorio principa, el cual se encargara de levantar la base de datos.

``` 
docker compose up -d
 ```

Verifica que el contenedor esté corriendo
```
docker ps
 ```

Por defecto al ejecutar el proyecto estará disponible en :
``` 
http://localhost:8080
```

##### 3. Puedes revisar la documentancion de los endpoint en:
```
http://localhost:8080/swagger-ui/index.html#/
``` 
