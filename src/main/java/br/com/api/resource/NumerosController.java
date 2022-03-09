package br.com.api.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import br.com.api.exception.ResourceDuplicateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.exception.ResourceNotFoundException;
import br.com.api.model.Numeros;
import br.com.api.repository.NumerosRepository;
import br.com.api.service.SequenceGeneratorService;

@RestController
@RequestMapping("/api/rifa")
public class NumerosController {

	@Autowired
	private NumerosRepository repository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/numeros")
	public List<Numeros> getNumeros(){
		return repository.findAll();
	}
	
	@GetMapping("/numeroId/{id}")
	public ResponseEntity<Numeros> findById(@PathVariable int id)
		throws ResourceNotFoundException{
		Numeros numeros = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o id " + id));
		return ResponseEntity.ok().body(numeros);
	}
	
	@GetMapping("/procurarNumero/{numero}")
	public ResponseEntity<Numeros> findByNumber(@PathVariable int numero)
		throws ResourceNotFoundException{
		Numeros numeros = repository.findByNumero(numero)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o número " + numero));
		return ResponseEntity.ok().body(numeros);
	}
	
	@PostMapping("/numeros")
	@ResponseStatus(HttpStatus.CREATED)
	public Numeros saveRifa(@Valid @RequestBody Numeros numeros) throws ResourceDuplicateException {
		//return repository.save(numeros);
		Optional<Numeros> checkNumero = repository.findByNumero(numeros.getNumero());
		if(!checkNumero.isEmpty())
			throw new ResourceDuplicateException("O número " + numeros.getNumero() + " já existe!");

		if(numeros.getId()==0)
			numeros.setId(sequenceGeneratorService.generateSequence(Numeros.SEQUENCE_ID));
		
		return repository.save(numeros);
	}
	
	@PutMapping("/numeros/{id}")
	public ResponseEntity<Numeros> updateRifa(@PathVariable int id, @Valid @RequestBody Numeros numerosDetails)
		throws ResourceNotFoundException{
		Numeros numeros = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o id " + id));
		
		numeros.setNome(numerosDetails.getNome());
		numeros.setNumero(numerosDetails.getNumero());
		numeros.setFralda(numerosDetails.getFralda());
		numeros.setStatus(numerosDetails.getStatus());
		
		final Numeros updatedNumeros = repository.save(numeros);
		return ResponseEntity.ok(updatedNumeros);
	}
	
	@DeleteMapping("/numeros/{id}")
	public Map<String, Boolean> deleteNumeros(@PathVariable int id) throws ResourceNotFoundException {
		Numeros numeros = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o id " + id));
		repository.deleteById(id);
		Map<String, Boolean> response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
