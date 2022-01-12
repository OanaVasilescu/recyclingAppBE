package recycle.greenlife.model;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.UUID;

public class Tip {
    @Id
    private UUID id = UUID.randomUUID();

    private String tipText; //TODO: not null text

    private String moreInfo;

    public Tip() {
    }

    public Tip(String tipText, String moreInfo) {
        this.tipText = tipText;
        this.moreInfo = moreInfo;
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

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }
}
