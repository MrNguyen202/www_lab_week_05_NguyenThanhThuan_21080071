/*
 * @ (#) ExperienceService.java    1.0    11/13/2024
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
import vn.edu.iuh.hero.backend.models.Experience;
import vn.edu.iuh.hero.backend.repositories.ExperienceRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceService implements IServices<Experience, Long> {

    @Autowired
    private ExperienceRepository experienceRepository;

    @Override
    public Experience add(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public Experience update(Experience experience) {
        return experienceRepository.save(experience);
    }

    @Override
    public Experience delete(Long aLong) {
        return experienceRepository.findById(aLong).map(experience -> {
            experienceRepository.delete(experience);
            return experience;
        }).orElse(null);
    }

    @Override
    public Optional<Experience> findById(Long aLong) {
        return Optional.of(experienceRepository.findById(aLong)).orElse(null);
    }

    @Override
    public List<Experience> findAll() {
        return experienceRepository.findAll();
    }
}
