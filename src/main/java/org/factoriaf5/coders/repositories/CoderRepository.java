package org.factoriaf5.coders.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CoderRepository extends CrudRepository <Coder, Long> {

    List<Coder> findCoderByNombreContaining(String word);
}
