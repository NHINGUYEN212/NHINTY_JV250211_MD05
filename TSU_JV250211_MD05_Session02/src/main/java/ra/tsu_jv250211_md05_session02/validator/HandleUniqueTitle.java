package ra.tsu_jv250211_md05_session02.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ra.tsu_jv250211_md05_session02.service.MovieService;

@Component
public class HandleUniqueTitle implements ConstraintValidator<ra.tsu_jv250211_md05_session02.validator.UniqueTitle, String> {

    @Autowired
    private MovieService movieService;

    @Override
    public void initialize(ra.tsu_jv250211_md05_session02.validator.UniqueTitle constraintAnnotation) {
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        return !movieService.existsByTitle(title);
    }
}