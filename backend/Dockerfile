# Stage 1: Build with Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app
COPY . .

RUN mvn clean install -DskipTests

# Stage 2: Use base JDK image
FROM eclipse-temurin:17-jdk

# Copy the custom built server from Maven output
COPY --from=build /app/target/server /opt/server

WORKDIR /opt/server

EXPOSE 8080 9990

CMD ["bin/standalone.sh", "-b", "0.0.0.0"]