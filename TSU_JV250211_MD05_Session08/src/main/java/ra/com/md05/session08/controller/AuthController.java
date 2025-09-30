package ra.com.md05.session08.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.com.md05.session08.model.dto.request.UserDTO;
import ra.com.md05.session08.model.dto.response.UserLoginResponseDTO;
import ra.com.md05.session08.model.entity.User;
import ra.com.md05.session08.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.register(userDTO);
        if (user == null) {
            return new ResponseEntity<>("Username exists, register failed !", HttpStatus.BAD_REQUEST);
        } else  {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        UserLoginResponseDTO userLoginResponseDTO = userService.login(userDTO);
        if (userLoginResponseDTO == null) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        } else  {
            return new ResponseEntity<>(userLoginResponseDTO, HttpStatus.OK);
        }
    }
}
