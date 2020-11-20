package max.pentium.spring_boot_crud.dao;

import max.pentium.spring_boot_crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
