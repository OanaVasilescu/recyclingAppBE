package recycle.greenlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recycle.greenlife.model.Reminder;
import recycle.greenlife.repository.ReminderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReminderService {
    @Autowired
    private ReminderRepository reminderRepository;

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
            //TODO verify if reminder already exists
            Reminder savedReminder = reminderRepository.save(new Reminder(reminder.getUserId(), reminder.getReminderDate(), reminder.getReminderTitle(), reminder.getReminderType()));
            return new ResponseEntity<>("Reminder saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The reminder could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Reminder> updateReminder(UUID id, Reminder reminderData) {
        //TODO: verify if new data is already taken
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

    public ResponseEntity<String> deleteAllReminders() {
        try {
            reminderRepository.deleteAll();
            return new ResponseEntity<>("Reminders successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Reminders could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Reminders could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteReminder(UUID id) {
        try {
            reminderRepository.deleteById(id);
            return new ResponseEntity<>("Reminder " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Reminder " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Reminder " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
