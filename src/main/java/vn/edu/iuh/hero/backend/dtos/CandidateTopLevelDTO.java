/*
 * @ (#) CandidateTopLevelDTO.java    1.0    12/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.dtos;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 12/13/2024
 * @Version: 1.0
 *
 */

import lombok.*;
import vn.edu.iuh.hero.backend.models.Candidate;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CandidateTopLevelDTO {
    private Long id;
    private String fullName;
    private String level;
    private List<String> companies;
    private List<String> skills;

    public CandidateTopLevelDTO(Candidate candidate, String level, List<String> companies, List<String> skills) {
        this.id = candidate.getId();
        this.fullName = candidate.getFullName();
        this.level = level;
        this.companies = companies;
        this.skills = skills;
    }
}
