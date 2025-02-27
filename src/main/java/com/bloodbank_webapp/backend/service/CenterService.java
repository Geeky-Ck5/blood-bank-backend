package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.CenterDTO;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.repository.CenterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CenterService {
    private final CenterRepository centerRepository;

    public CenterService(CenterRepository centerRepository) {
        this.centerRepository = centerRepository;
    }

    // ✅ Fetch only CenterDTO to avoid exposing full Center entity
    public List<CenterDTO> getAllCenters() {
        List<Center> centers = centerRepository.findAll();
        return centers.stream().map(CenterDTO::new).collect(Collectors.toList());
    }
}
