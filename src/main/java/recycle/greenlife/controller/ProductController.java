package recycle.greenlife.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recycle.greenlife.model.Product;
import recycle.greenlife.service.ProductService;

import java.util.List;
import java.util.UUID;


//TODO: see why it does not return "bad request" when the json has anything other than what is needed
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping("/products")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/products")
    public ResponseEntity<String> deleteAllProducts() {
        return productService.deleteAllProducts();
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id);
    }

}
