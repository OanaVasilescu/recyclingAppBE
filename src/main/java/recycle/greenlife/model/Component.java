package recycle.greenlife.model;


import javax.persistence.*;
import java.util.UUID;
import org.springframework.data.annotation.Id;


public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String name;

    private Integer productCode;

    private String instructions;

    private Integer priority;

    @Enumerated(EnumType.STRING)
    private Enums.ProductType productType;

    @Enumerated(EnumType.STRING)
    private Enums.BinType binType;

    //TODO: see how to know where should every component be thrown - > normal,plastic, compost
    public Component(String name, Integer productCode, String instructions, Integer priority, Enums.ProductType productType) {
        this.name = name;
        this.productCode = productCode;
        this.instructions = instructions;
        this.priority = priority;
        this.productType = productType;
    }

    public Component() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Enums.ProductType getProductType() {
        return productType;
    }

    public void setProductType(Enums.ProductType productType) {
        this.productType = productType;
    }
}
