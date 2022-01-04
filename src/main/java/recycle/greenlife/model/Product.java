package recycle.greenlife.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

public class Product {
    @Id
    private UUID id = UUID.randomUUID();

    private String name;

    private List<String> componentIds; // = new ArrayList<>();???? TODO: what?

    //TODO: add barcode to product

    public Product() {
    }

    public Product(String name, List<String> componentIds) {
        this.name = name;
        this.componentIds = componentIds;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getComponentIds() {
        return componentIds;
    }

    public void setComponentIds(List<String> componentIds) {
        this.componentIds = componentIds;
    }
}
