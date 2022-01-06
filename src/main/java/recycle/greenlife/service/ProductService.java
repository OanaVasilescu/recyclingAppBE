package recycle.greenlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recycle.greenlife.model.Product;
import recycle.greenlife.model.User;
import recycle.greenlife.repository.ProductRepository;
import recycle.greenlife.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


//TODO: add not null somehow
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> products = new ArrayList<>();
            productRepository.findAll().forEach(products::add);
            if (products.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(products, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all products:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Product> getProductById(UUID id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            System.out.println("No product found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> addProduct(Product product) {
        try {
            //TODO verify if product already exists
            Product savedProduct = productRepository.save(new Product(product.getName(), product.getComponentIds()));
            return new ResponseEntity<>("Product saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The product could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Product> updateProduct(UUID id, Product productData) {
        //TODO: verify if new data is already taken
        Optional<Product> oldProductData = productRepository.findById(id);
        if (oldProductData.isPresent()) {
            Product updatedProduct = oldProductData.get();
            updatedProduct.setName(productData.getName());
            updatedProduct.setComponentIds(productData.getComponentIds()); //TODO: verify if it has components
            return new ResponseEntity<>(productRepository.save(updatedProduct), HttpStatus.OK);
        } else {
            System.out.println("No such product found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllProducts() {
        try {
            productRepository.deleteAll();

            return new ResponseEntity<>("Products successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Products could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Products could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Product " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Product " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Product " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
