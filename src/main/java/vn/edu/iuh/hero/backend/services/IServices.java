package vn.edu.iuh.hero.backend.services;

import java.util.List;
import java.util.Optional;

public interface IServices<T, P> {
    T add (T t);
    T update (T t);
    T delete (P p);
    Optional<T> findById (P p);
    List<T> findAll();
}
