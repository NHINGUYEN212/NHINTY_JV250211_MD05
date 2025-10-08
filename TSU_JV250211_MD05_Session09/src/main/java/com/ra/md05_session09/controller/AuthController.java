package com.ra.md05_session09.controller;

import com.ra.md05_session09.model.dto.request.CustomerDTO;
import com.ra.md05_session09.model.dto.request.CustomerLoginDTO;
import com.ra.md05_session09.model.dto.response.CustomerLoginResponse;
import com.ra.md05_session09.model.entity.Customer;
import com.ra.md05_session09.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerService.registerCustomer(customerDTO);
        if(customer != null) {
            return new ResponseEntity<>(customer, HttpStatus.CREATED);
        } else  {
            return new ResponseEntity<>("Register Failed", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginDTO customerLoginDTO) {
        CustomerLoginResponse customerLoginResponse = customerService.login(customerLoginDTO);
        if(customerLoginResponse != null) {
            return new ResponseEntity<>(customerLoginResponse, HttpStatus.OK);
        } else   {
            return new ResponseEntity<>("Login Failed", HttpStatus.BAD_REQUEST);
        }

    }
}
