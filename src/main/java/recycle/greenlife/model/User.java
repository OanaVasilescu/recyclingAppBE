package recycle.greenlife.model;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;


public class User {
    @Id
    private UUID id = UUID.randomUUID();

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private List<UUID> reminderIds;


    //TODO: add admin roles

    public User() {
    }

    public User(String username, String password, String email, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<UUID> getReminderIds() {
        return reminderIds;
    }

    public void setReminderIds(List<UUID> reminderIds) {
        this.reminderIds = reminderIds;
    }
}
