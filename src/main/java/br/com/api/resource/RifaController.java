package br.com.api.resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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
import br.com.api.model.Rifa;
import br.com.api.repository.RifaRepository;
import br.com.api.service.SequenceGeneratorService;

@RestController
@RequestMapping("/api/rifa")
public class RifaController {

	@Autowired
	private RifaRepository repository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/numeros")
	public List<Rifa> getRifas(){
		return repository.findAll();
	}
	
	@GetMapping("/numeroId/{id}")
	public ResponseEntity<Rifa> findById(@PathVariable int id)
		throws ResourceNotFoundException{
		Rifa rifa = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o id " + id));
		return ResponseEntity.ok().body(rifa);
	}
	
	@GetMapping("/procurarNumero/{numero}")
	public ResponseEntity<Rifa> findByNumber(@PathVariable int numero)
		throws ResourceNotFoundException{
		Rifa rifa = repository.findByNumero(numero)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o número " + numero));
		return ResponseEntity.ok().body(rifa);
	}
	
	@PostMapping("/numeros")
	@ResponseStatus(HttpStatus.CREATED)
	public Rifa saveRifa(@Valid @RequestBody Rifa rifa) {
		//return repository.save(rifa);
		if(rifa.getId()==0)
			rifa.setId(sequenceGeneratorService.generateSequence(Rifa.SEQUENCE_ID));
		
		return repository.save(rifa);
	}
	
	@PutMapping("/numeros/{id}")
	public ResponseEntity<Rifa> updateRifa(@PathVariable int id, @Valid @RequestBody Rifa rifaDetails)
		throws ResourceNotFoundException{
		Rifa rifa = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o id " + id));
		
		rifa.setNome(rifaDetails.getNome());
		rifa.setNumero(rifaDetails.getNumero());
		rifa.setFralda(rifaDetails.getFralda());
		rifa.setStatus(rifaDetails.getStatus());
		
		final Rifa updatedRifa = repository.save(rifa);
		return ResponseEntity.ok(updatedRifa);
	}
	
	@DeleteMapping("/numeros/{id}")
	public Map<String, Boolean> deleteRifa(@PathVariable int id) throws ResourceNotFoundException {
		Rifa rifa = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Não foi encontrado o id " + id));
		repository.deleteById(id);
		Map<String, Boolean> response = new HashMap< >();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
