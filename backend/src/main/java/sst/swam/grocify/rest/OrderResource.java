package sst.swam.grocify.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import sst.swam.grocify.dto.OrderDTO;
import sst.swam.grocify.mapper.OrderMapper;
import sst.swam.grocify.model.Customer;
import sst.swam.grocify.service.CustomerService;
import sst.swam.grocify.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderService orderService;

    @Inject
    CustomerService customerService;

    @GET
    @RolesAllowed("ADMIN")
    public List<OrderDTO> getAllOrders(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("limit") @DefaultValue("10") int limit,
            @QueryParam("status") String status,
            @QueryParam("date") String date
    ) {
        return orderService.findAllFiltered(page, limit, status, date).stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/my")
    @RolesAllowed("CUSTOMER")
    public List<OrderDTO> getMyOrders(
        @Context SecurityContext ctx,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("limit") @DefaultValue("10") int limit,
        @QueryParam("status") String status,
        @QueryParam("date") String date
    ) {
        String email = ctx.getUserPrincipal().getName();
        Customer customer = customerService.findByEmail(email);
        return orderService.findByCustomerFiltered(customer, page, limit, status, date)
                .stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @POST
    @RolesAllowed("CUSTOMER")
    public Response placeOrder(@Context SecurityContext ctx, OrderDTO dto) {
        String email = ctx.getUserPrincipal().getName();
        Customer customer = customerService.findByEmail(email);
        orderService.placeOrder(customer, dto);
        return Response.status(Response.Status.CREATED).build();
    }

    @PATCH
    @Path("/{id}/deliver")
    @RolesAllowed("ADMIN")
    public Response markAsDelivered(@PathParam("id") Long id) {
        boolean updated = orderService.markDelivered(id);
        if (!updated) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().build();
    }
}
