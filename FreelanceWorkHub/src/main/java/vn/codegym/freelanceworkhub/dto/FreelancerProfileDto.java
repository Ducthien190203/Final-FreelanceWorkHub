package vn.codegym.freelanceworkhub.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FreelancerProfileDto {

    private Long id;

    @NotBlank(message = "Tiểu sử không được để trống")
    private String bio;

    // Chuỗi CSV hoặc mảng skills tùy UI xử lý
    private String skills;

    private String location;

    private Integer experienceYears;

    private String availability; // "Available" hoặc "Busy"
}
