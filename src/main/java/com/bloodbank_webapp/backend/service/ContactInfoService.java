package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.dto.ContactInfoDTO;
import com.bloodbank_webapp.backend.model.Users;
import com.bloodbank_webapp.backend.model.ContactInfo;
import com.bloodbank_webapp.backend.repository.ContactInfoRepository;
import com.bloodbank_webapp.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactInfoService {

    @Autowired
    private ContactInfoRepository contactInfoRepository;

    @Autowired
    private UserRepository userRepository;

    public ContactInfo getContactInfoByUserId(Long userId) {
        return contactInfoRepository.findByUserUserId(userId);
    }

    public ContactInfo updateContactInfo(Long userId, ContactInfo contactInfoRequest) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ContactInfo contactInfo = contactInfoRepository.findByUserUserId(userId);
        if (contactInfo == null) {
            contactInfo = new ContactInfo();
            contactInfo.setUser(user);
        }

        contactInfo.setPhoneNumber(contactInfoRequest.getPhoneNumber());
        contactInfo.setMobileOperator(contactInfoRequest.getMobileOperator());
        contactInfo.setMobileNumber(contactInfoRequest.getMobileNumber());
        contactInfo.setStreetAddress(contactInfoRequest.getStreetAddress());
        contactInfo.setAddressLine1(contactInfoRequest.getAddressLine1());
        contactInfo.setAddressLine2(contactInfoRequest.getAddressLine2());
        contactInfo.setAddressLine3(contactInfoRequest.getAddressLine3());
        contactInfo.setCity(contactInfoRequest.getCity());
        contactInfo.setDistrict(contactInfoRequest.getDistrict());
        contactInfo.setCountry(contactInfoRequest.getCountry());

        return contactInfoRepository.save(contactInfo);
    }
}
