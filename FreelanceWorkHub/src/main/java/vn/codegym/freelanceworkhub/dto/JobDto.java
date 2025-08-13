package vn.codegym.freelanceworkhub.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class JobDto {

    private Long id;

    @NotBlank(message = "Tên công việc không được để trống")
    private String title;

    @NotBlank(message = "Mô tả không được để trống")
    @Size(min = 100, message = "Mô tả tối thiểu 100 ký tự")
    private String description;

    private Double budget;

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId; // Lấy từ form select

    private String status; // PENDING / APPROVED / CLOSED

    private String skills;
    private String responsibilities;
    private String requirements;
    private String locationType;
    private String onSiteAddress;
    private String experienceLevel;
    private String paymentType;
    private Double minBudget;
    private Double maxBudget;
}
