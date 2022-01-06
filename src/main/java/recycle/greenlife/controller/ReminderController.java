package recycle.greenlife.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recycle.greenlife.model.Reminder;
import recycle.greenlife.service.ReminderService;

import java.util.List;
import java.util.UUID;

@Controller
public class ReminderController {
    @Autowired
    private ReminderService reminderService;

    @GetMapping("/reminders")
    public ResponseEntity<List<Reminder>> getAllReminders() {
        return reminderService.getAllReminders();
    }

    @GetMapping("/reminders/{id}")
    public ResponseEntity<Reminder> getReminderById(@PathVariable UUID id) {
        return reminderService.getReminderById(id);
    }

    @PostMapping("/reminders")
    public ResponseEntity<String> addReminder(@RequestBody Reminder reminder) {
        return reminderService.addReminder(reminder);
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<Reminder> updateReminder(@PathVariable UUID id, @RequestBody Reminder reminder) {
        return reminderService.updateReminder(id, reminder);
    }

    @DeleteMapping("/reminders")
    public ResponseEntity<String> deleteAllReminders() {
        return reminderService.deleteAllReminders();
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<String> deleteReminder(@PathVariable UUID id) {
        return reminderService.deleteReminder(id);
    }
}
