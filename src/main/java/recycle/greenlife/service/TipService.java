package recycle.greenlife.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import recycle.greenlife.model.Tip;
import recycle.greenlife.repository.TipRepository;

import java.util.*;

@Service
public class TipService {
    @Autowired
    private TipRepository tipRepository;

    public ResponseEntity<List<Tip>> getAllTips() {
        try {
            List<Tip> tips = new ArrayList<>();
            tipRepository.findAll().forEach(tips::add);
            if (tips.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tips, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println("Error while getting all tips:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Tip> getTipById(UUID id) {
        Optional<Tip> tip = tipRepository.findById(id);
        if (tip.isPresent()) {
            return new ResponseEntity<>(tip.get(), HttpStatus.OK);
        } else {
            System.out.println("No tip found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Tip> getRandomTip() {
        try {
            List<Tip> tips = new ArrayList<>();
            tipRepository.findAll().forEach(tips::add);
            if (tips.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            Collections.shuffle(tips);
            return new ResponseEntity<>(tips.get(0), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error while getting all tips:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addTip(Tip tip) {
        try {
            //TODO verify if tip already exists
            Tip savedTip = tipRepository.save(new Tip(tip.getTipText(), tip.getMoreInfo()));
            return new ResponseEntity<>("Tip saved successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("The tip could not be added. Error:" + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Tip> updateTip(UUID id, Tip tipData) {
        //TODO: verify if new data is already taken
        Optional<Tip> oldTipData = tipRepository.findById(id);
        if (oldTipData.isPresent()) {
            Tip updatedTip = oldTipData.get();
            updatedTip.setTipText(tipData.getTipText());
            return new ResponseEntity<>(tipRepository.save(updatedTip), HttpStatus.OK);
        } else {
            System.out.println("No such tip found");
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> deleteAllTips() {
        try {
            tipRepository.deleteAll();
            return new ResponseEntity<>("Tips successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Tips could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Tips could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteTip(UUID id) {
        try {
            tipRepository.deleteById(id);
            return new ResponseEntity<>("Tip " + id + " successfully deleted", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Tip " + id + " could not be deleted. Error: " + e.getMessage());
            return new ResponseEntity<>("Tip " + id + " could not be deleted. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
