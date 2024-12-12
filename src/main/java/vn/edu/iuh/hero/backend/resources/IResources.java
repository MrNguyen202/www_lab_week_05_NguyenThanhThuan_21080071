package vn.edu.iuh.hero.backend.resources;

import org.springframework.http.ResponseEntity;
import vn.edu.iuh.hero.backend.models.Response;

public interface IResources<T, P>{
    ResponseEntity<Response> add(T t);
    ResponseEntity<Response> update(P p, T t);
    ResponseEntity<Response> delete(P p);
    ResponseEntity<Response> findById(P p);
    ResponseEntity<Response> findAll();
}
