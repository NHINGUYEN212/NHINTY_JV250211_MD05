package ra.com.md05.session08.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.com.md05.session08.model.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String roleName);}
