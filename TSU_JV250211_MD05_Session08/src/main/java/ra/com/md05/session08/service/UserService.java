package ra.com.md05.session08.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ra.com.md05.session08.model.dto.request.UserDTO;
import ra.com.md05.session08.model.dto.response.UserLoginResponseDTO;
import ra.com.md05.session08.model.entity.Role;
import ra.com.md05.session08.model.entity.User;
import ra.com.md05.session08.repository.UserRepository;
import ra.com.md05.session08.security.jwt.JWTProvider;

@Service
@Lazy
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jWTProvider;


    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User register(UserDTO userDTO) {
        User userExists = userRepository.findUserByUsername(userDTO.getUsername());
        if (userExists != null) {
            return null;
        } else {
            Role roleUser = roleService.findRoleByRoleName("user");
            User newUser = User.builder()
                    .username(userDTO.getUsername())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .role(roleUser)
                    .build();
            try {
                return userRepository.save(newUser);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public UserLoginResponseDTO login(UserDTO userDTO) {
        User userLogin = getUserByUsername(userDTO.getUsername());
        if (userLogin != null && passwordEncoder.matches(userDTO.getPassword(), userLogin.getPassword())) {
            return UserLoginResponseDTO.builder()
                    .username(userLogin.getUsername())
                    .accessToken(jWTProvider.generateToken(userLogin.getUsername()))
                    .build();
        } else  {
            return null;
        }
    }
    public String updatePassword(User user, String oldPassword, String newPassword, String confirmPassword) {
        if (passwordEncoder.matches(oldPassword, user.getPassword()) && newPassword.equals(confirmPassword)) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Changed Password Successfully!";
        } else  {
            return "Passwords do not match!";
        }
    }
}