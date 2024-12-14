/*
 * @ (#) CandidateForJobDTO.java    1.0    12/14/2024
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

import lombok.AllArgsConstructor;
import lombok.Data;
import vn.edu.iuh.hero.backend.models.Candidate;

@Data
@AllArgsConstructor
public class CandidateForJobDTO {
    private Candidate candidate;
    private Long totalMatchedSkills;
}
