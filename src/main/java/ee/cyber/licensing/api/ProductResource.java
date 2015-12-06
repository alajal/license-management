package ee.cyber.licensing.api;

import ee.cyber.licensing.dao.ProductRepository;
import ee.cyber.licensing.dao.ReleaseRepository;
import ee.cyber.licensing.entity.AuthorisedUser;
import ee.cyber.licensing.entity.Product;
import ee.cyber.licensing.entity.Release;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

@Path("products")
public class ProductResource {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ReleaseRepository releaseRepository;

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

    @Path("/search/{keyword}")
    @GET
    @Produces("application/json")
    public List<Product> getProductsSearch(@PathParam("keyword") String keyword) throws Exception {
      return productRepository.findByKeyword(keyword);
    }

    @POST
    public Product saveProduct(Product product) throws Exception {
        Product productWithId = productRepository.save(product);
        Release release;
        for(int i = 0; i < productWithId.getReleases().size(); i++){
            releaseRepository.saveRelease(productWithId.getId(), product.getReleases().get(i));
        }
        return productWithId;
    }

    @PUT
    public Product editProduct(Product product) throws Exception {
      System.out.println("siin");
        System.out.println(product);
        System.out.println("seal");
        return productRepository.editProduct(product);
    }

    @DELETE
    @Path("/{productId}")
    public boolean deleteProductById(@PathParam("productId") int productId) throws Exception {
        return productRepository.deleteProduct(productRepository.getProductById(productId));

    }

}
