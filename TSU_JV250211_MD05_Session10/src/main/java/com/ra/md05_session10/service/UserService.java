package com.ra.md05_session10.service;

import com.ra.md05_session10.model.constant.Role;
import com.ra.md05_session10.model.dto.request.UserLoginDTO;
import com.ra.md05_session10.model.dto.request.UserRegisterDTO;
import com.ra.md05_session10.model.dto.response.DataResponse;
import com.ra.md05_session10.model.dto.response.PaginationResponse;
import com.ra.md05_session10.model.dto.response.UserLoginResponse;
import com.ra.md05_session10.model.entity.User;
import com.ra.md05_session10.repository.UserRepository;
import com.ra.md05_session10.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Lazy
public class UserService {
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;

    public DataResponse<?> login(UserLoginDTO userLoginDTO) {
        User user = findUserByUsername(userLoginDTO.getUsername());
        if (user != null && passwordEncoder.matches(userLoginDTO.getPassword(), user.getPassword())) {
            if (user.isStatus()){
                UserLoginResponse userLoginResponse = UserLoginResponse
                        .builder()
                        .username(user.getUsername())
                        .accessToken(jwtProvider.generateToken(user.getUsername()))
                        .build();

                return DataResponse
                        .builder()
                        .message("Login successful")
                        .data(userLoginResponse)
                        .status(200)
                        .build();
            }else {

                return DataResponse
                        .builder()
                        .message("Login failed")
                        .data("Tài khoản của bạn đã bị khóa")
                        .status(200)
                        .build();
            }

        }else {
            return DataResponse
                    .builder()
                    .message("Username or password is incorrect")
                    .status(404)
                    .build();
        }
    }

    public DataResponse<?> register(UserRegisterDTO userRegisterDTO) {
        User user = User
                .builder()
                .username(userRegisterDTO.getUsername())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .email(userRegisterDTO.getEmail())
                .phone(userRegisterDTO.getPhone())
                .role(Role.valueOf(userRegisterDTO.getRole().toUpperCase()))
                .status(true)
                .build();
        try {
            return DataResponse
                    .builder()
                    .status(201)
                    .message("Registration successful")
                    .data(userRepository.save(user))
                    .build();
        }catch (Exception e){
            return DataResponse
                    .builder()
                    .status(400)
                    .message(e.getMessage())
                    .build();
        }
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<DataResponse<PaginationResponse<User>>> adminFindAllUserAndSearch(Pageable pageable , String searchUsername) {
        Page<User> page = userRepository.findByUsernameContaining(searchUsername, pageable);
        PaginationResponse<User> paginationResponse = new PaginationResponse<>();
        paginationResponse.setTotalElements(page.getTotalElements());
        paginationResponse.setTotalPages(page.getTotalPages());
        paginationResponse.setContent(page.getContent());
        paginationResponse.setCurrentPage(pageable.getPageNumber()+ 1);
        paginationResponse.setSize(pageable.getPageSize());
        DataResponse<PaginationResponse<User>> dataResponse = new DataResponse<>();
        dataResponse.setData(paginationResponse);
        dataResponse.setMessage("Search successful");
        dataResponse.setStatus(200);
        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public ResponseEntity<DataResponse<String>> changeStatus(long id){
        DataResponse<String> dataResponse = new DataResponse<>();
        User user = findById(id);
        if(user != null){
            try {
                user.setStatus(!user.isStatus());
                userRepository.save(user);
                dataResponse.setMessage("Status changed successfully");
                dataResponse.setStatus(200);
                return new ResponseEntity<>(dataResponse, HttpStatus.OK);
            } catch (Exception e) {
                dataResponse.setMessage(e.getMessage());
                dataResponse.setStatus(400);
                return new ResponseEntity<>(dataResponse, HttpStatus.BAD_REQUEST);
            }

        }else {
            dataResponse.setMessage("User not found");
            dataResponse.setStatus(404);
            return new ResponseEntity<>(dataResponse, HttpStatus.NOT_FOUND);
        }
    }
}
