package br.com.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.api.model.Numeros;

@Repository
public interface NumerosRepository extends MongoRepository<Numeros, Integer>{

	Optional<Numeros> findByNumero(int numero);

}
