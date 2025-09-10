package com.smartcity.controller;

import com.smartcity.model.EmergencyContact;
import com.smartcity.service.EmergencyContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/emergency-contacts")
@RequiredArgsConstructor
public class EmergencyContactController {

    private final EmergencyContactService emergencyContactService;

    @PostMapping
    public EmergencyContact addContact(@RequestBody EmergencyContact contact) {
        return emergencyContactService.save(contact);
    }

    @GetMapping
    public List<EmergencyContact> getAllContacts() {
        return emergencyContactService.findAll();
    }
}
