package vn.codegym.freelanceworkhub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Họ tên không được để trống")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    @Column(nullable = false, unique = true)
    private String email;

    // Lưu trữ mật khẩu đã mã hoá (BCrypt)
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.FREELANCER; // mặc định FREELANCER

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;

    // Nếu dùng OAuth (Google), lưu provider + avatar
    @Column(name = "provider")
    private String provider; // e.g. "LOCAL", "GOOGLE"

    @Column(name = "avatar_url")
    private String avatarUrl;

    public enum Role {
        FREELANCER,
        EMPLOYER,
        ADMIN
    }

    public enum AccountStatus {
        ACTIVE,
        BANNED
    }
}
