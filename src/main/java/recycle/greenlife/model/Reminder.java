package recycle.greenlife.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Reminder {
    @Id
    private UUID id = UUID.randomUUID();

    private UUID userId;


    @Transient
    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm"); //To format the date
    @Transient
    private Date unformattedDate = new Date(); // TODO: add data (new Date(something)) -> as is now, it returns this moment

    @Transient
    private String reminderDate = format.format(unformattedDate);


    public Reminder() {
    }

    public Reminder(UUID userId, String reminderDate) {
        this.userId = userId;
        this.reminderDate = reminderDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        this.reminderDate = reminderDate;
    }
}
