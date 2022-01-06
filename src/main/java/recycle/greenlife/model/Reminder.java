package recycle.greenlife.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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


    private String reminderTitle;

    @Enumerated(EnumType.STRING)
    private Enums.ReminderType reminderType;

    public Reminder() {
    }

    public Reminder(UUID userId, String reminderDate, String reminderTitle, Enums.ReminderType reminderType) {
        this.userId = userId;
        this.reminderDate = reminderDate;
        this.reminderTitle = reminderTitle;
        this.reminderType = reminderType;
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

    public String getReminderTitle() {
        return reminderTitle;
    }

    public void setReminderTitle(String reminderTitle) {
        this.reminderTitle = reminderTitle;
    }

    public Enums.ReminderType getReminderType() {
        return reminderType;
    }

    public void setReminderType(Enums.ReminderType reminderType) {
        this.reminderType = reminderType;
    }
}
