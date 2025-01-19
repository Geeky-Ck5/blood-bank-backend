package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.CenterDTO;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CenterController {

    @Autowired
    private CenterService centerService;


    @GetMapping("/{id}")
    public ResponseEntity<Center> getCenterById(@PathVariable Long id) {
        Center center = centerService.getCenterById(id);
        return ResponseEntity.ok(center);
    }

    @PostMapping
    public String createCenter(@RequestBody CenterDTO centerDTO) {
        centerService.createCenter(centerDTO);
        return "Center created successfully.";
    }

    @GetMapping
    public ResponseEntity<List<Center>> getAllCenters() {
        List<Center> centers = centerService.getAllCenters();
        return ResponseEntity.ok(centers);
    }
}
