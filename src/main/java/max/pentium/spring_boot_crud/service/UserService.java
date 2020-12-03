package max.pentium.spring_boot_crud.service;

import max.pentium.spring_boot_crud.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user);
    void deleteUser(long id);
    User getUser(long id);
    List<User> getUsersList();
    User findByEmail(String email);
}
