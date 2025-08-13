package vn.codegym.freelanceworkhub.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.codegym.freelanceworkhub.dto.UserRegistrationDto;
import vn.codegym.freelanceworkhub.model.User;
import vn.codegym.freelanceworkhub.repository.UserRepository;
import vn.codegym.freelanceworkhub.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegistrationDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email đã được sử dụng");
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu xác nhận không khớp");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encodedPassword);
        System.out.println("Registering user: " + dto.getEmail() + ", Encoded password: " + encodedPassword); // Added logging
        user.setRole(User.Role.valueOf(dto.getRole().replace("ROLE_", "").toUpperCase()));
        
        // Add logging before and after save
        System.out.println("Attempting to save user: " + user.getEmail());
        User savedUser = userRepository.save(user);
        System.out.println("User saved successfully with ID: " + savedUser.getId()); // Assuming ID is generated

        return savedUser;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Attempting to load user by email: " + email); // Added logging
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    System.out.println("User not found for email: " + email); // Added logging
                    return new UsernameNotFoundException("User not found with email: " + email);
                });

        System.out.println("User found: " + user.getEmail() + ", Role: " + user.getRole().name()); // Added logging
        System.out.println("Stored password (encoded): " + user.getPassword()); // Added logging (CAUTION: In real app, don't log passwords)

        if (user.getStatus() == User.AccountStatus.BANNED) {
            System.out.println("User " + user.getEmail() + " is banned.");
            throw new DisabledException("Tài khoản của bạn đã bị cấm.");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }

    @Override
    public User findByIdWithProfile(Long id) {
        User user = userRepository.findByIdWithProfile(id).orElse(null);
        if (user != null) {
            System.out.println("User found with ID: " + id + ", Email: " + user.getEmail());
            if (user.getFreelancerProfile() != null) {
                System.out.println("  Freelancer Profile Bio: " + user.getFreelancerProfile().getBio());
                System.out.println("  Freelancer Profile Skills: " + user.getFreelancerProfile().getSkills());
                System.out.println("  Freelancer Profile Location: " + user.getFreelancerProfile().getLocation());
                System.out.println("  Freelancer Profile Experience Years: " + user.getFreelancerProfile().getExperienceYears());
                System.out.println("  Freelancer Profile Availability: " + user.getFreelancerProfile().getAvailability());
            } else {
                System.out.println("  Freelancer Profile is NULL for user ID: " + id);
            }
        } else {
            System.out.println("User not found with ID: " + id);
        }
        return user;
    }

    @Override
    public void banUser(Long id) {
        User user = findById(id);
        if (user != null) {
            user.setStatus(User.AccountStatus.BANNED);
            userRepository.save(user);
        }
    }

    @Override
    public void unbanUser(Long id) {
        User user = findById(id);
        if (user != null) {
            user.setStatus(User.AccountStatus.ACTIVE);
            userRepository.save(user);
        }
    }
}