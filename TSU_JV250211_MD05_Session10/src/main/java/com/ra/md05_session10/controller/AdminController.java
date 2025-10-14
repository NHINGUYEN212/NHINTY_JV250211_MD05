package com.ra.md05_session10.controller;

import com.ra.md05_session10.model.dto.response.DataResponse;
import com.ra.md05_session10.model.dto.response.PaginationResponse;
import com.ra.md05_session10.model.entity.User;
import com.ra.md05_session10.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    private UserService userService;

    @GetMapping("/findAllUser")
    public ResponseEntity<DataResponse<PaginationResponse<User>>> findAllUser(@RequestParam(name = "page",defaultValue = "0") int page,
                                                                              @RequestParam(name = "size",defaultValue = "5") int size,
                                                                              @RequestParam(name = "searchUsername", required = false) String searchUsername) {
        return userService.adminFindAllUserAndSearch(PageRequest.of(page,size),searchUsername);
    }

    @PutMapping("/changeStatus/user/{id}")
    public ResponseEntity<DataResponse<String>> changeStatus(@PathVariable("id") Long id) {
        return userService.changeStatus(id);
    }
}
