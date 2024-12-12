/*
 * @ (#) CompanyService.java    1.0    11/13/2024
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
import vn.edu.iuh.hero.backend.models.Company;
import vn.edu.iuh.hero.backend.repositories.CompanyRepository;
import vn.edu.iuh.hero.backend.services.IServices;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService implements IServices<Company, Long> {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company add(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company update(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company delete(Long aLong) {
        return companyRepository.findById(aLong).map(company -> {
            companyRepository.delete(company);
            return company;
        }).orElse(null);
    }

    @Override
    public Optional<Company> findById(Long aLong) {
        return Optional.of(companyRepository.findById(aLong)).orElse(null);
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
