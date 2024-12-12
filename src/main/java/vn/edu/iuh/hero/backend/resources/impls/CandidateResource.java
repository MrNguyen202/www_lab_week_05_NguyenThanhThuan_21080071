/*
 * @ (#) CandidateResource.java    1.0    11/13/2024
 *
 *
 */

package vn.edu.iuh.hero.backend.resources.impls;
/*
 * @Description:
 * @Author: Nguyen Thanh Thuan
 * @Date: 11/13/2024
 * @Version: 1.0
 *
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.hero.backend.models.Candidate;
import vn.edu.iuh.hero.backend.models.Response;
import vn.edu.iuh.hero.backend.resources.IResources;
import vn.edu.iuh.hero.backend.services.impls.CandidateService;

@RestController
@RequestMapping("/api/candidates")
public class CandidateResource implements IResources<Candidate, Long> {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    @Override
    public ResponseEntity<Response> add(@RequestBody Candidate candidate) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Add candidate successfully",
                candidateService.add(candidate)
        ));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Response> update(@PathVariable("id") Long aLong, @RequestBody Candidate candidate) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Update candidate successfully",
                candidateService.update(candidate)
        ));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Response> delete(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Delete candidate successfully",
                candidateService.delete(aLong)
        ));
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Response> findById(@PathVariable("id") Long aLong) {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get candidate by id successfully",
                candidateService.findById(aLong)
        ));
    }

    @GetMapping("/all")
    @Override
    public ResponseEntity<Response> findAll() {
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all candidates successfully",
                candidateService.findAll()
        ));
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Response> getAll(@PathVariable("page") String pageNumber) {
        Pageable page = PageRequest.of(Integer.parseInt(pageNumber), 2);
        return ResponseEntity.ok(new Response(
                HttpStatus.OK.value(),
                "Get all candidates successfully",
                candidateService.getAll(page)
        ));
    }
}
