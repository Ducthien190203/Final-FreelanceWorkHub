package vn.codegym.freelanceworkhub.service;

import vn.codegym.freelanceworkhub.dto.UserRegistrationDto;
import vn.codegym.freelanceworkhub.model.User;

import java.util.List;

public interface UserService {
    User register(UserRegistrationDto dto);

    User findByEmail(String email);

    List<User> findAll();

    User findById(Long id);

    void deleteById(Long id);

    User save(User user);
}
