package ra.com.session07.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.session07.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
