package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.dto.ContactInfoDTO;
import com.bloodbank_webapp.backend.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact-info")
public class ContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @GetMapping
    public List<ContactInfoDTO> getAllContactInfos() {
        return contactInfoService.getAllContactInfos();
    }

    @GetMapping("/{id}")
    public ContactInfoDTO getContactInfoById(@PathVariable Long id) {
        return contactInfoService.getContactInfoById(id);
    }

    @PostMapping
    public String createContactInfo(@RequestBody ContactInfoDTO contactInfoDTO) {
        contactInfoService.createContactInfo(contactInfoDTO);
        return "Contact Info created successfully.";
    }
}
