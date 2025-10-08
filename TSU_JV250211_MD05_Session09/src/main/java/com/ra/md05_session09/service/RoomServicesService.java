package com.ra.md05_session09.service;

import com.ra.md05_session09.model.dto.request.RoomServicesDTO;
import com.ra.md05_session09.model.entity.RoomServices;
import com.ra.md05_session09.repository.RoomServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoomServicesService {
    @Autowired
    private RoomServicesRepository roomServiceRepository;

    public Page<RoomServices> getAllServices(Pageable pageable) {
        return roomServiceRepository.findAll(pageable);
    }

    public RoomServices addService(RoomServicesDTO roomServiceDTO) {
        RoomServices roomServices = new RoomServices();
        roomServices.setName(roomServiceDTO.getName());
        roomServices.setPrice(roomServiceDTO.getPrice());
        try {
            return roomServiceRepository.save(roomServices);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public RoomServices findById(Long id) {
        return roomServiceRepository.findById(id).orElse(null);
    }

    public RoomServices updateService(RoomServicesDTO roomServiceDTO, Long id) {
        RoomServices roomServices = findById(id);
        if (roomServices != null) {
            roomServices.setName(roomServiceDTO.getName());
            roomServices.setPrice(roomServiceDTO.getPrice());
            try {
                return roomServiceRepository.save(roomServices);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }  else return null;
    }

    public String deleteService(Long id) {
        RoomServices roomServices = findById(id);
        if (roomServices != null) {
            try {
                roomServiceRepository.delete(roomServices);
                return "RoomServices deleted successfully";
            }  catch (Exception e) {
                e.printStackTrace();
                return "RoomServices delete failed";
            }
        } else return "RoomServices not found";
    }
}
