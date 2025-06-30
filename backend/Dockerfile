# --- Stage 1: Build the WAR using Maven ---
FROM maven:3.9.5-eclipse-temurin-17 AS builder

# Set working directory
WORKDIR /app

# Copy project files into container
COPY . .

# Build the WAR file
RUN mvn clean package -DskipTests


# --- Stage 2: Deploy WAR to Wildfly ---
FROM quay.io/wildfly/wildfly:latest-jdk17

# Copy the WAR file built in stage 1 into Wildfly's deployments folder
COPY --from=builder /app/target/ROOT.war /opt/jboss/wildfly/standalone/deployments/
