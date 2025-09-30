package ra.com.session07.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.com.session07.model.dto.UserDTO;
import ra.com.session07.model.entity.User;
import ra.com.session07.service.UserService;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        User user = userService.register(userDTO);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } else  {
            return new ResponseEntity<>("Register failed!", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO, HttpSession session) {
        User user = userService.login(userDTO.getUsername(), userDTO.getPassword(), session);
        if (user != null) {
            return new ResponseEntity<>("Login successfully", HttpStatus.OK);
        } else   {
            return new ResponseEntity<>("Login failed!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        userService.logout(session);
        return new ResponseEntity<>("Logout successfully", HttpStatus.OK);
    }
}
