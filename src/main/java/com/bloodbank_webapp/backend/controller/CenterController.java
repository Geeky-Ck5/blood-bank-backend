package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.CenterDTO;
import com.bloodbank_webapp.backend.service.CenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CenterController {
    private final CenterService centerService;

    public CenterController(CenterService centerService) {
        this.centerService = centerService;
    }

    // ✅ Ensure API returns only CenterDTO objects
    @GetMapping("/centers")
    public ResponseEntity<List<CenterDTO>> getCenters() {
        List<CenterDTO> centers = centerService.getAllCenters();
        return ResponseEntity.ok(centers);
    }
}
