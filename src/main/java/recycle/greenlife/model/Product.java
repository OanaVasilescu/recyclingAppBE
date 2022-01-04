package recycle.greenlife.model;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.springframework.data.annotation.Id;
import java.util.List;
import java.util.UUID;

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

//    @NotNull
    private String name;

//    @NotNull
    private List<UUID> componentIds; // = new ArrayList<>();???? TODO: what?

    //TODO: add barcode to product

    public Product() {
    }

    public Product(String name, List<UUID> componentIds) {
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

    public List<UUID> getComponentIds() {
        return componentIds;
    }

    public void setComponentIds(List<UUID> componentIds) {
        this.componentIds = componentIds;
    }
}
