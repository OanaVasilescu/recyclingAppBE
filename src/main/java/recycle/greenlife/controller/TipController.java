package recycle.greenlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recycle.greenlife.model.Tip;
import recycle.greenlife.service.TipService;

import java.util.List;
import java.util.UUID;

@Controller
public class TipController {
    @Autowired
    private TipService tipService;

    @GetMapping("/tips")
    public ResponseEntity<List<Tip>> getAllTips() {
        return tipService.getAllTips();
    }

    @GetMapping("/tips/{id}")
    public ResponseEntity<Tip> getTipById(@PathVariable UUID id) {
        return tipService.getTipById(id);
    }

    @PostMapping("/tips")
    public ResponseEntity<String> addTip(@RequestBody Tip tip) {
        return tipService.addTip(tip);
    }

    @PutMapping("/tips/{id}")
    public ResponseEntity<Tip> updateTip(@PathVariable UUID id, @RequestBody Tip tip) {
        return tipService.updateTip(id, tip);
    }

    @DeleteMapping("/tips")
    public ResponseEntity<String> deleteAllTips() {
        return tipService.deleteAllTips();
    }

    @DeleteMapping("/tips/{id}")
    public ResponseEntity<String> deleteTip(@PathVariable UUID id) {
        return tipService.deleteTip(id);
    }

}
