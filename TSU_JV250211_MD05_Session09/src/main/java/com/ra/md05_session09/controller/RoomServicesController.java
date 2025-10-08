package com.ra.md05_session09.controller;

import com.ra.md05_session09.model.dto.request.RoomServicesDTO;
import com.ra.md05_session09.model.entity.RoomServices;
import com.ra.md05_session09.service.RoomServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/services")
public class RoomServicesController {

    @Autowired
    private RoomServicesService roomServicesService;

    @GetMapping
    public ResponseEntity<Page<RoomServices>> getAllServices(Pageable pageable) {
        return ResponseEntity.ok(roomServicesService.getAllServices(pageable));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addService(@RequestBody RoomServicesDTO roomServiceDTO) {
        RoomServices roomServices = roomServicesService.addService(roomServiceDTO);
        if (roomServices != null) {
            return new ResponseEntity<>(roomServices, HttpStatus.CREATED);
        } else  {
            return new ResponseEntity<>("Add Service Failed!" ,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> updateService(@RequestBody RoomServicesDTO roomServiceDTO, @PathVariable Long id) {
        RoomServices roomServices = roomServicesService.updateService(roomServiceDTO, id);
        if (roomServices != null) {
            return new ResponseEntity<>(roomServices, HttpStatus.OK);
        } else   {
            return new ResponseEntity<>("Update Service Failed!" ,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        return  new ResponseEntity<>(roomServicesService.deleteService(id), HttpStatus.OK);
    }
}
