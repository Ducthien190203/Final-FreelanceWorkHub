package vn.codegym.freelanceworkhub.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ApplicationDto {

    private Long id;

    @NotNull(message = "Job ID không được để trống")
    private Long jobId;

    @NotBlank(message = "Thư ứng tuyển không được để trống")
    private String coverLetter;

    private String status; // PENDING / ACCEPTED / REJECTED
}
