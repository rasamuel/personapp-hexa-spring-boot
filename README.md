# PersonApp - Arquitectura Hexagonal con Spring Boot

Este proyecto es una aplicación de demostración basada en arquitectura hexagonal, desarrollada con Spring Boot y Docker. El sistema cuenta con integración a dos motores de base de datos: MariaDB (relacional) y MongoDB (NoSQL).

## Tabla de Contenidos

- Requisitos Previos  
- Preparación del Entorno  
- Compilación y Despliegue  
- Uso  
- Configuración Adicional  
- Notas

## Requisitos Previos

Para ejecutar correctamente este proyecto, necesitas tener instalado en tu entorno:

- Docker y Docker Compose  
- Java 11 o una versión superior  
- Maven para construir el proyecto  
- Git (solo si deseas clonar el repositorio desde GitHub)

## Preparación del Entorno

### Clonar el Repositorio

```bash
git clone https://github.com/rasamuel/personapp-hexa-spring-boot.git
cd personapp-hexa-spring-boot
```

### Configurar las Bases de Datos

El sistema utiliza MariaDB y MongoDB. Ambas se configuran automáticamente mediante Docker Compose. Las propiedades de conexión están definidas en el archivo `application.properties`.

### Configuración de Docker Compose

El archivo `docker-compose.yml` incluido en el proyecto configura y lanza los contenedores necesarios para la aplicación y las bases de datos.

### Verificar application.properties

Asegúrate de que el archivo `src/main/resources/application.properties` contenga lo siguiente:

```properties
#Loggion config
logging.level.root=INFO
#logging.level.org.springframework=debug
logging.file.name=logs/persona.log
#Bean reference Config
spring.main.allow-circular-references=true
#MariaDB Config
spring.datasource.url=jdbc:mariadb://localhost:3307/persona_db
spring.datasource.username=persona_db
spring.datasource.password=persona_db
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=none
spring.jpa.open-in-view=false
#MongoDB Config
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.username=persona_db
spring.data.mongodb.password=persona_db
spring.data.mongodb.database=persona_db
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
```

## Compilación y Despliegue

### Paso 1: Compilar el Proyecto

Antes de levantar los contenedores, compila la aplicación utilizando Maven:

```bash
mvn clean install
```

### Paso 2: Iniciar los Servicios con Docker Compose

Luego de compilar, ejecuta el siguiente comando para levantar los contenedores necesarios:

```bash
docker-compose up -d
```

Este comando iniciará los servicios definidos en el archivo `docker-compose.yml`, incluyendo:

- `rest-service`: la aplicación desarrollada con Spring Boot  
- `maria-db`: base de datos relacional MariaDB  
- `mongo-db`: base de datos NoSQL MongoDB  

La primera vez puede tardar algunos minutos mientras Docker descarga las imágenes necesarias.

## Uso

### Acceso a la Aplicación

Una vez iniciada, la aplicación estará disponible en:

```
http://localhost:8080
```

(o en el puerto configurado en el archivo `application.properties`).

### Interacción con la API REST

Puedes probar los endpoints de la aplicación utilizando:

- Un cliente REST como Postman o Insomnia  
- La interfaz Swagger (OpenAPI), accesible en:  
  ```
  http://localhost:8080/swagger-ui/index.html
  ```


### Apagar los Contenedores

Cuando termines de trabajar con el sistema, puedes detener todos los servicios con:

```bash
docker-compose down
```

## Notas

- Si necesitas cambiar el puerto u otros parámetros, puedes editar los archivos `application.properties` o `docker-compose.yml`.  
- En caso de errores de conexión con las bases de datos, revisa las credenciales y configuraciones de autenticación de MongoDB y MariaDB.