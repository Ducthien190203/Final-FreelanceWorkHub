package vn.codegym.freelanceworkhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.codegym.freelanceworkhub.model.JobCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryStatsDTO {
    private String categoryName;
    private long jobCount;

    
}
