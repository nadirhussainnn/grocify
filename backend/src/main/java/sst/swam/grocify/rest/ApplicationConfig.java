package sst.swam.grocify.rest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api") 
public class ApplicationConfig extends Application {
    // No need to override anything; WildFly auto-scans resources
}