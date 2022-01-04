package recycle.greenlife.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import recycle.greenlife.model.Component;
import recycle.greenlife.service.ComponentService;

import java.util.List;
import java.util.UUID;

@Controller
public class ComponentController {
    @Autowired
    private ComponentService componentService;

    @GetMapping("/components")
    public ResponseEntity<List<Component>> getAllComponents() {
        return componentService.getAllComponents();
    }

    @GetMapping("/components/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable UUID id) {
        return componentService.getComponentById(id);
    }

    @PostMapping("/components")
    public ResponseEntity<String> addComponent(@RequestBody Component component) {
        return componentService.addComponent(component);
    }

    @PutMapping("/components/{id}")
    public ResponseEntity<Component> updateComponent(@PathVariable UUID id, @RequestBody Component component) {
        return componentService.updateComponent(id, component);
    }

    @DeleteMapping("/components")
    public ResponseEntity<String> deleteAllComponents() {
        return componentService.deleteAllComponents();
    }

    @DeleteMapping("/components/{id}")
    public ResponseEntity<String> deleteComponent(@PathVariable UUID id) {
        return componentService.deleteComponent(id);
    }

}
