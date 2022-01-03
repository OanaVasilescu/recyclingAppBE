package recycle.greenlife.model;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.UUID;

public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(name = "componentName")
    private String name;

    @NotNull
    @Column(name = "productCode")
    private Integer productCode;

    @Column(name = "recyclingInstructions")
    private String instructions;

    @Column(name = "priority")
    private Integer priority;

    public enum ProductType {
        bottle,
        wrapping,
        capac // TODO: add categories
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "productType")
    private ProductType productType;


    public Component(String name, Integer productCode, String instructions, Integer priority, ProductType productType) {
        this.name = name;
        this.productCode = productCode;
        this.instructions = instructions;
        this.priority = priority;
        this.productType = productType;
    }

    public Component() {
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

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
}
