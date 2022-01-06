package recycle.greenlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recycle.greenlife.model.Component;
import recycle.greenlife.model.Product;
import recycle.greenlife.repository.ComponentRepository;
import recycle.greenlife.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComponentService {
    @Autowired
    private ComponentRepository componentRepository;

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<Component>> getAllComponents() {
        try {
            List<Component> components = new ArrayList<>();
            componentRepository.findAll().forEach(components::add);
            if (components.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(components, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all components:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Component> getComponentById(UUID id) {
        Optional<Component> component = componentRepository.findById(id);
        if (component.isPresent()) {
            return new ResponseEntity<>(component.get(), HttpStatus.OK);
        } else {
            System.out.println("No component found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> addComponent(Component component) {
        try {
            //TODO verify if component already exists
            Component savedComponent = componentRepository.save(new Component(component.getName(), component.getProductCode(), component.getInstructions(), component.getPriority(), component.getProductType()));
            return new ResponseEntity<>("Component saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The component could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Component> updateComponent(UUID id, Component componentData) {
        //TODO: verify if new data is already taken
        Optional<Component> oldComponentData = componentRepository.findById(id);
        if (oldComponentData.isPresent()) {
            Component updatedComponent = oldComponentData.get();
            updatedComponent.setName(componentData.getName());
            updatedComponent.setProductCode(componentData.getProductCode());
            updatedComponent.setInstructions(componentData.getInstructions());
            updatedComponent.setPriority(componentData.getPriority());
            updatedComponent.setProductType(componentData.getProductType());
            return new ResponseEntity<>(componentRepository.save(updatedComponent), HttpStatus.OK);
        } else {
            System.out.println("No such component found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllComponents() { //TODO: don't allow deletion if any product uses this
        try {
            componentRepository.deleteAll();

            productRepository.deleteAll();// cascade products answers

            List<Product> products = new ArrayList<Product>();
            productRepository.findAll().forEach(products::add); // remove component from every product
            for (Product product : products) {
                product.setComponentIds(null);
            }

            return new ResponseEntity<>("Components successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Components could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Components could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteComponent(UUID id) {
        try {
            //TODO: remove from every product which uses this
            ResponseEntity response = this.removeComponentFromProducts(id);
            if (response.getStatusCode().isError()) {
                return new ResponseEntity<>("Error removing component from products", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            componentRepository.deleteById(id);
            return new ResponseEntity<>("Component " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Component " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Component " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> removeComponentFromProducts(UUID id) {
        try {
            List<Product> allProducts = this.getAllProducts();
            if (allProducts == null) {
                return new ResponseEntity<>("Error getting product to delete components from them", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            for (Product product : allProducts) { // remove component ID from every product
                List<UUID> components = product.getComponentIds();
                components.removeIf(el -> el == id);
            }

            return new ResponseEntity<>("Component removed from products", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("err:" + e.getMessage());
            return new ResponseEntity<>("Error removing component:" + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private List<Product> getAllProducts() {
        try {
            List<Product> products = new ArrayList<>();
            productRepository.findAll().forEach(products::add);
            return products;
        } catch (Exception e) {
            System.out.println("Error while getting all products:" + e.getMessage());
            return null;
        }
    }
}
