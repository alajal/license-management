package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ProductRepository;
import ee.cyber.licensing.entity.License;
import ee.cyber.licensing.entity.Product;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

/**
 * Created by siiri on 26/09/15.
 */
@Path("products")
public class ProductResource {

    @Inject
    private ProductRepository productRepository;

    @GET
    @Produces("application/json")
    public List<Product> getProducts() throws Exception{
        return productRepository.findAll();
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Product getLicenseById(@PathParam("id") Integer id) throws Exception{
        //TODO Response.ok(License objekt)
        return productRepository.getProductById(id);
    }

    @POST
    public Product saveProduct(Product product) throws Exception {
        return productRepository.save(product);
    }

}
