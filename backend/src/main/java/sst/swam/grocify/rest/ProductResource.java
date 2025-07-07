package sst.swam.grocify.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.Context;
import sst.swam.grocify.dto.ProductDTO;
import sst.swam.grocify.mapper.ProductMapper;
import sst.swam.grocify.model.Product;
import sst.swam.grocify.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

	@Inject
	ProductService productService;

	@GET
	@RolesAllowed({ "ADMIN", "CUSTOMER" })
	public List<ProductDTO> getAll(
			@Context SecurityContext ctx, 
			@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("limit") @DefaultValue("10") int limit,
			@QueryParam("search") String search) 
	{
		boolean isCustomer = ctx.isUserInRole("CUSTOMER");
		return productService.findAllFiltered(page, limit, search, isCustomer)
				.stream().map(ProductMapper::toDTO)
				.collect(Collectors.toList());
	}

	@GET
	@Path("/{id}")
	public Response getById(@PathParam("id") Long id) {
		Product product = productService.findById(id);
		if (product == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		return Response.ok(ProductMapper.toDTO(product)).build();
	}

	@POST
	@RolesAllowed("ADMIN")
	public Response create(ProductDTO dto) {
		productService.save(ProductMapper.toEntity(dto));
		return Response.status(Response.Status.CREATED).build();
	}

	@PATCH
	@Path("/{id}")
	@RolesAllowed("ADMIN")
	public Response update(@PathParam("id") Long id, ProductDTO dto) {
		Product existing = productService.findById(id);
		if (existing == null)
			return Response.status(Response.Status.NOT_FOUND).build();
		Product updated = ProductMapper.toEntity(dto);
		updated.setId(id);
		productService.update(updated);
		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed("ADMIN")
	public Response delete(@PathParam("id") Long id) {
		productService.delete(id);
		return Response.noContent().build();
	}
}
