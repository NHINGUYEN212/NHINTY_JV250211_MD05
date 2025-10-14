package com.ra.md05_session10.validator;


import com.ra.md05_session10.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HandleUsernameUnique implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userService.findUserByUsername(username) == null;
    }
}