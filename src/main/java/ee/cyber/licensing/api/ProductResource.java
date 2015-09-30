package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ProductRepository;
import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("products")
public class ProductResource {

    @Inject
    private ProductRepository productRepository;

    @GET
    @Produces("application/json")
    public List<Product> getProducts() throws Exception {
        return productRepository.findAll();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Product getProductById(@PathParam("id") Integer id) throws Exception {
        //TODO Response.ok(Product objekt)
        return productRepository.getProductById(id);
    }

    @POST
    public Product saveProduct(Product product) throws Exception {
        return productRepository.save(product);
    }

}
