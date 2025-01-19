package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.ContactInfoDTO;
import com.bloodbank_webapp.backend.model.ContactInfo;
import com.bloodbank_webapp.backend.repository.ContactInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactInfoService {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    public List<ContactInfoDTO> getAllContactInfos() {
        return contactInfoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ContactInfoDTO getContactInfoById(Long id) {
        ContactInfo contactInfo = contactInfoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contact Info not found"));
        return mapToDTO(contactInfo);
    }

    public void createContactInfo(ContactInfoDTO contactInfoDTO) {
        ContactInfo contactInfo = mapToEntity(contactInfoDTO);
        contactInfoRepository.save(contactInfo);
    }

    // Mapper: Entity to DTO
    private ContactInfoDTO mapToDTO(ContactInfo contactInfo) {
        ContactInfoDTO dto = new ContactInfoDTO();
        dto.setContactId(contactInfo.getContactId());
        dto.setPhoneNumber(contactInfo.getPhoneNumber());
        dto.setMobileOperator(contactInfo.getMobileOperator().name());
        dto.setMobileNumber(contactInfo.getMobileNumber());
        dto.setStreetAddress(contactInfo.getStreetAddress());
        dto.setAddressLine1(contactInfo.getAddressLine1());
        dto.setAddressLine2(contactInfo.getAddressLine2());
        dto.setAddressLine3(contactInfo.getAddressLine3());
        dto.setCity(contactInfo.getCity());
        dto.setDistrict(contactInfo.getDistrict());
        dto.setCountry(contactInfo.getCountry());
        return dto;
    }

    // Mapper: DTO to Entity
    private ContactInfo mapToEntity(ContactInfoDTO dto) {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPhoneNumber(dto.getPhoneNumber());
        contactInfo.setMobileOperator(ContactInfo.MobileOperator.valueOf(dto.getMobileOperator()));
        contactInfo.setMobileNumber(dto.getMobileNumber());
        contactInfo.setStreetAddress(dto.getStreetAddress());
        contactInfo.setAddressLine1(dto.getAddressLine1());
        contactInfo.setAddressLine2(dto.getAddressLine2());
        contactInfo.setAddressLine3(dto.getAddressLine3());
        contactInfo.setCity(dto.getCity());
        contactInfo.setDistrict(dto.getDistrict());
        contactInfo.setCountry(dto.getCountry());
        return contactInfo;
    }
}
