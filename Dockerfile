# Se utiliza una imagen base de Maven que incluye Amazon Corretto JDK 17 para compilar la aplicación
FROM maven:3.9.9-amazoncorretto-17-alpine AS builder

# Se define el directorio principal de trabajo dentro del contenedor
WORKDIR /app

# Se copian los archivos pom.xml de los módulos necesarios para preparar las dependencias en modo offline
COPY pom.xml .
COPY common/pom.xml common/
COPY domain/pom.xml domain/
COPY application/pom.xml application/
COPY maria-output-adapter/pom.xml maria-output-adapter/
COPY mongo-output-adapter/pom.xml mongo-output-adapter/
COPY rest-input-adapter/pom.xml rest-input-adapter/
COPY cli-input-adapter/pom.xml cli-input-adapter/

RUN mvn dependency:go-offline -B

# Se transfieren los archivos fuente de cada módulo al contenedor
COPY common/src common/src
COPY domain/src domain/src
COPY application/src application/src
COPY maria-output-adapter/src maria-output-adapter/src
COPY mongo-output-adapter/src mongo-output-adapter/src
COPY rest-input-adapter/src rest-input-adapter/src
COPY cli-input-adapter/src cli-input-adapter/src

# Se realiza la compilación del proyecto, omitiendo las pruebas
RUN mvn install -DskipTests



# Dockerfile para el servicio REST
FROM amazoncorretto:17.0.13-alpine

# Define el directorio donde se ejecutará la aplicación dentro del contenedor
WORKDIR /PERSONAPP-HEXA-SPRING-BOOT

# Copia el archivo JAR generado del módulo REST desde la imagen de compilación
COPY --from=builder /app/rest-input-adapter/target/*.jar app.jar

# Abre el puerto 8080 para permitir el acceso al servicio REST
EXPOSE 8080

# Instrucción para ejecutar la aplicación Spring Boot con el perfil "live"
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=live"]
