package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.CenterDTO;
import com.bloodbank_webapp.backend.model.Center;
import com.bloodbank_webapp.backend.repository.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CenterService {

    @Autowired
    private CenterRepository centerRepository;

    public List<CenterDTO> getAllCenters() {
        List<Center> centers = centerRepository.findAll();
        return centers.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CenterDTO getCenterById(Long id) {
        Center center = centerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Center not found"));
        return mapToDTO(center);
    }

    public void createCenter(CenterDTO centerDTO) {
        Center center = mapToEntity(centerDTO);
        centerRepository.save(center);
    }

    // Mapper: Entity to DTO
    private CenterDTO mapToDTO(Center center) {
        CenterDTO dto = new CenterDTO();
        dto.setCenterId(center.getCenterId());
        dto.setName(center.getName());
        dto.setStreetAddress(center.getStreetAddress());
        dto.setCity(center.getCity());
        dto.setDistrict(center.getDistrict());
        dto.setCountry(center.getCountry());
        dto.setContactNumber(center.getContactNumber());
        return dto;
    }

    // Mapper: DTO to Entity
    private Center mapToEntity(CenterDTO dto) {
        Center center = new Center();
        center.setName(dto.getName());
        center.setStreetAddress(dto.getStreetAddress());
        center.setCity(dto.getCity());
        center.setDistrict(dto.getDistrict());
        center.setCountry(dto.getCountry());
        center.setContactNumber(dto.getContactNumber());
        return center;
    }
}
