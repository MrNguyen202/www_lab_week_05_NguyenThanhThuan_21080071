/*
 * @ (#) JobInsertDTO.java    1.0    12/14/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.dtos;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/14/2024
 * @Version: 1.0
 *
 */

import lombok.*;
import vn.edu.iuh.hero.backend.enums.SkillLevel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class JobInsertDTO {
    private String jobTitle;
    private String jobDescription;
    private List<Long> skillSelected;  // Sửa kiểu List<Long> cho kỹ năng
    private Map<Long, String> skillLevel;  // Map giữa skill ID và level
    private String expiryDate;
}
