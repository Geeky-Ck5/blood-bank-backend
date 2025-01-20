package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.model.ContactInfo;
import com.bloodbank_webapp.backend.service.ContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact-info")
public class ContactInfoController {

    @Autowired
    private ContactInfoService contactInfoService;

    @GetMapping("/{userId}")
    public ResponseEntity<ContactInfo> getContactInfo(@PathVariable Long userId) {
        return ResponseEntity.ok(contactInfoService.getContactInfoByUserId(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ContactInfo> updateContactInfo(
            @PathVariable Long userId,
            @RequestBody ContactInfo contactInfoRequest) {
        return ResponseEntity.ok(contactInfoService.updateContactInfo(userId, contactInfoRequest));
    }
}
