# --- Stage 1: Build the WAR using Maven ---
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Set working directory in builder image
WORKDIR /app

# Copy the entire backend project into the image
COPY . .

# Build the WAR file (skipping tests for faster build)
RUN mvn clean install -DskipTests

# --- Stage 2: Deploy WAR to WildFly ---
FROM quay.io/wildfly/wildfly:30.0.0.Final-jdk17

# Copy the built WAR file into WildFly's deployments directory
COPY --from=build /app/target/*.war /opt/jboss/wildfly/standalone/deployments/ROOT.war