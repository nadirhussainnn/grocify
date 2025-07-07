package sst.swam.grocify.rest;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import sst.swam.grocify.dto.CustomerDTO;
import sst.swam.grocify.mapper.CustomerMapper;
import sst.swam.grocify.service.CustomerService;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
	
	@Inject
	CustomerService customerService;
	
	@GET
	@RolesAllowed({"ADMIN"})
	public List<CustomerDTO> getAll(
			@Context SecurityContext ctx, 
			@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("limit") @DefaultValue("20") int limit,
			@QueryParam("search") String search
	) {
		return customerService.findAll(page, limit, search == null ? "" : search)
				.stream()
				.map(CustomerMapper::toDTOWithoutOrders)
				.collect(Collectors.toList());
	}
	
	@DELETE
	@Path("/{id}")
	@RolesAllowed("ADMIN")
	public Response delete(@PathParam("id") Long id) {
		customerService.delete(id);
		return Response.noContent().build();
	}
}

