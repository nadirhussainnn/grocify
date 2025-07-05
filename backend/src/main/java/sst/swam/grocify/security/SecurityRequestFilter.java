package sst.swam.grocify.security;

import java.security.Principal;
import java.io.IOException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;
import sst.swam.grocify.util.JwtUtil;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityRequestFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String path = requestContext.getUriInfo().getPath();

        if (path.startsWith("/auth/")) {
            return;
        }

        String authHeader = requestContext.getHeaderString("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                if (JwtUtil.validateToken(token)) {
                    String email = JwtUtil.getEmailFromToken(token);
                    final String userRole = JwtUtil.getRoleFromToken(token); // Make it final

                    System.out.println("✅ Valid token for user: " + email + " with role: " + userRole);

                    // Create security context that JAX-RS can use
                    SecurityContext securityContext = new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return () -> email;
                        }

                        @Override
                        public boolean isUserInRole(String requestedRole) {
                            boolean hasRole = requestedRole.equals(userRole);
                            System.out.println("🔍 Role check: User has '" + userRole + "', requested '" + requestedRole + "' = " + hasRole);
                            return hasRole;
                        }

                        @Override
                        public boolean isSecure() {
                            return requestContext.getUriInfo().getRequestUri().getScheme().equals("https");
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return "Bearer";
                        }
                    };

                    requestContext.setSecurityContext(securityContext);
                    return;
                }
            } catch (Exception e) {
                System.out.println("❌ INVALID TOKEN ERROR: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // No valid token found
        System.out.println("❌ No valid token found for path: " + path);
        requestContext.abortWith(Response
            .status(Response.Status.UNAUTHORIZED)
            .entity("Unauthorized")
            .build());
    }
}