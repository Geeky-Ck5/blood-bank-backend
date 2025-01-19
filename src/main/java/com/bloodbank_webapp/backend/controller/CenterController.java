package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.CenterDTO;
import com.bloodbank_webapp.backend.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/centers")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @GetMapping
    public List<CenterDTO> getAllCenters() {
        return centerService.getAllCenters();
    }

    @GetMapping("/{id}")
    public CenterDTO getCenterById(@PathVariable Long id) {
        return centerService.getCenterById(id);
    }

    @PostMapping
    public String createCenter(@RequestBody CenterDTO centerDTO) {
        centerService.createCenter(centerDTO);
        return "Center created successfully.";
    }
}
