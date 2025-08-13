package vn.codegym.freelanceworkhub.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EmployerProfileDto {

    private Long id;

    private String companyName;

    private String companyDescription;

    private String website;

    private String location;
}
