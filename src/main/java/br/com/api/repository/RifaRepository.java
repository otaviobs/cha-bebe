package br.com.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.api.model.Rifa;

@Repository
public interface RifaRepository extends MongoRepository<Rifa, Integer>{
	Rifa findRifaById(Integer rifaId);

}
