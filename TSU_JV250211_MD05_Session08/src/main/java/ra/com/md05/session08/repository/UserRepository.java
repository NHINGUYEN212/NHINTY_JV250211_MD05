package ra.com.md05.session08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.md05.session08.model.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}
