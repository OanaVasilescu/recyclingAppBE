package recycle.greenlife.model;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.UUID;

public class Tip {
    @Id
    private UUID id = UUID.randomUUID();

    private String tipText; //TODO: not null text

    public Tip() {
    }

    public Tip(String tipText) {
        this.tipText = tipText;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }
}
