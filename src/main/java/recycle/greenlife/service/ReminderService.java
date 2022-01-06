package recycle.greenlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recycle.greenlife.model.Reminder;
import recycle.greenlife.model.User;
import recycle.greenlife.repository.ReminderRepository;
import recycle.greenlife.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReminderService {
    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<Reminder>> getAllReminders() {
        try {
            List<Reminder> reminders = new ArrayList<>();
            reminderRepository.findAll().forEach(reminders::add);
            if (reminders.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(reminders, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all reminders:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Reminder> getReminderById(UUID id) {
        Optional<Reminder> reminder = reminderRepository.findById(id);
        if (reminder.isPresent()) {
            return new ResponseEntity<>(reminder.get(), HttpStatus.OK);
        } else {
            System.out.println("No reminder found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity<String> addReminder(Reminder reminder) {
        try {
            //TODO only allow each user to add it's own reminder
            UUID id = UUID.randomUUID();
            ResponseEntity response = this.addReminderToUser(reminder.getUserId(), id);
            if (response.getStatusCode().isError()) {
                // TODO: if adding reminder fails, what happens?)
                return new ResponseEntity<>("Error adding reminder to user", HttpStatus.BAD_REQUEST);
            } else {
                Reminder savedReminder = reminderRepository.save(new Reminder(reminder.getUserId(), reminder.getReminderDate(), reminder.getReminderTitle(), reminder.getReminderType()));
            }
            return new ResponseEntity<>("Reminder saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The reminder could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<User> addReminderToUser(UUID userId, UUID id) {
        if (userId == null) {
            System.out.println("User ID is null");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Optional<User> oldUserData = userRepository.findById(userId);
        if (oldUserData.isPresent()) {
            List<UUID> list;
            User updatedUser = oldUserData.get();

            if (updatedUser.getReminderIds() == null) {
                list = new ArrayList<>();
            } else {
                list = updatedUser.getReminderIds();
            }

            list.add(id);
            updatedUser.setReminderIds(list);
            return new ResponseEntity<>(userRepository.save(updatedUser), HttpStatus.OK);
        } else {
            System.out.println("No such user found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Reminder> updateReminder(UUID id, Reminder reminderData) {
        Optional<Reminder> oldReminderData = reminderRepository.findById(id);
        if (oldReminderData.isPresent()) {
            Reminder updatedReminder = oldReminderData.get();
            updatedReminder.setUserId(reminderData.getUserId());
            updatedReminder.setReminderDate(reminderData.getReminderDate());
            updatedReminder.setReminderTitle(reminderData.getReminderTitle());
            updatedReminder.setReminderType(reminderData.getReminderType());
            return new ResponseEntity<>(reminderRepository.save(updatedReminder), HttpStatus.OK);
        } else {
            System.out.println("No such reminder found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllReminders() { // TODO: maybe remove this
        try {
            reminderRepository.deleteAll();

            List<User> users = new ArrayList<User>();

            userRepository.findAll().forEach(users::add); // remove reminders from every user
            for (User user : users) {
                user.setReminderIds(null);
            }
            return new ResponseEntity<>("Reminders successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Reminders could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Reminders could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteReminder(UUID id) {
        try {
            UUID userId = this.getUserIdFromReminderId(id);
            if (userId == null) {
                return new ResponseEntity<>("Could not find user to remove reminder from", HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                ResponseEntity response = this.removeReminderFromUser(userId);
                if (response.getStatusCode().isError()) {
                    System.out.println("error:" + response.getBody());
                    return new ResponseEntity<>("Error deleting reminder from user", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            reminderRepository.deleteById(id);
            return new ResponseEntity<>("Reminder " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Reminder " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Reminder " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private UUID getUserIdFromReminderId(UUID reminderId) {
        UUID userId = null;
        Optional<Reminder> reminder = reminderRepository.findById(reminderId);
        if (reminder.isPresent()) {
            Reminder reminderData = reminder.get();
            userId = reminderData.getUserId();
        }
        return userId;
    }


    private ResponseEntity<String> removeReminderFromUser(UUID userId) {
        try {
            List<Reminder> userReminders = reminderRepository.findAllByUserId(userId);
            List<UUID> reminderIds = userReminders.stream().map(Reminder::getId).collect(Collectors.toList());
            Optional<User> oldUserData = userRepository.findById(userId);
            if (oldUserData.isPresent()) {
                User updatedUser = oldUserData.get();
                updatedUser.setReminderIds(reminderIds);
                userRepository.save(updatedUser);
            } else {
                System.out.println("No such user found - remove reminder from user");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Removed reminder from user", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Reminder could not be removed: " + e.getMessage());
            return new ResponseEntity<>("Reminder could not be removed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
