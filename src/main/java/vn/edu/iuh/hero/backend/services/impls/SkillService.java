/*
 * @ (#) SkillService.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.services.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.hero.backend.models.Skill;
import vn.edu.iuh.hero.backend.repositories.SkillRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService implements IServices<Skill, Long> {

    @Autowired
    private SkillRepository skillRepository;

    @Override
    public Skill add(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill update(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill delete(Long aLong) {
        return skillRepository.findById(aLong).map(skill -> {
            skillRepository.delete(skill);
            return skill;
        }).orElse(null);
    }

    @Override
    public Optional<Skill> findById(Long aLong) {
        return Optional.of(skillRepository.findById(aLong)).orElse(null);
    }

    @Override
    public List<Skill> findAll() {
        return skillRepository.findAll();
    }
}
