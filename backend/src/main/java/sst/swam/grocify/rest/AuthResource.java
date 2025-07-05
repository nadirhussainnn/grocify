// REST: AuthResource.java
package sst.swam.grocify.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sst.swam.grocify.dto.AdminDTO;
import sst.swam.grocify.dto.CustomerDTO;
import sst.swam.grocify.model.Admin;
import sst.swam.grocify.model.Customer;
import sst.swam.grocify.service.AdminService;
import sst.swam.grocify.service.CustomerService;
import sst.swam.grocify.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AdminService adminService;

    @Inject
    CustomerService customerService;

    @POST
    @Path("/login")
    public Response login(Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        try {
            Admin admin = adminService.findByEmail(email);
            if (admin != null && BCrypt.checkpw(password, admin.getPassword())) {
                String token = JwtUtil.generateToken(admin.getEmail(), "ADMIN");
                return Response.ok(Map.of("token", token)).build();
            }
        } catch (Exception ignored) {}

        try {
            Customer customer = customerService.findByEmail(email);
            if (customer != null && BCrypt.checkpw(password, customer.getPassword())) {
                String token = JwtUtil.generateToken(customer.getEmail(), "CUSTOMER");
                return Response.ok(Map.of("token", token)).build();
            }
        } catch (Exception ignored) {}

        return Response.status(Response.Status.UNAUTHORIZED)
                       .entity(Map.of("error", "Invalid credentials"))
                       .build();
    }

    @POST
    @Path("/register/customer")
    public Response registerCustomer(Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");

        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        customerService.save(customer);

        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/register/admin")
    public Response registerAdmin(Map<String, String> data) {
        String email = data.get("email");
        String password = data.get("password");

        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        adminService.save(admin);

        return Response.status(Response.Status.CREATED).build();
    }
}
