package com.ra.md05_session10.controller;

import com.ra.md05_session10.model.dto.request.UserLoginDTO;
import com.ra.md05_session10.model.dto.request.UserRegisterDTO;
import com.ra.md05_session10.model.dto.response.DataResponse;
import com.ra.md05_session10.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/api/v1/admin/auth/register")
    public ResponseEntity<DataResponse<?>> register(@Valid @RequestBody UserRegisterDTO userRegisterDTO) {
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.CREATED);
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<DataResponse<?>> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<>(userService.login(userLoginDTO), HttpStatus.OK);
    }
}
