package me.lam.huyen.repository;

import me.lam.huyen.model.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends CrudRepository<Data, Long> {
}
