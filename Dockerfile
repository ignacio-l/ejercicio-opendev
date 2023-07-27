
# Usar una imagen base con JDK 11 y Maven
FROM maven:3.6.3-openjdk-17-oracle AS build

# Establecer un directorio de trabajo
WORKDIR /app

# Copiar archivos de tu proyecto al directorio de trabajo
COPY . /app

# Ejecutar Maven para construir el proyecto
RUN mvn clean

# Crear una nueva imagen basada en OpenJDK 11
FROM eclipse-temurin:17-jdk-jammy

# Exponer el puerto que utilizará la aplicación
EXPOSE 8080

# Copiar el archivo JAR construido desde la etapa anterior
COPY --from=build /app/target/Peugeot-0.0.1-SNAPSHOT.jar /app/Peugeot-0.0.1-SNAPSHOT.jar

# Establecer el punto de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/Peugeot-0.0.1-SNAPSHOT.jar"]
