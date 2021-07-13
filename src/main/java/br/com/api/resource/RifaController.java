package br.com.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.model.Rifa;
import br.com.api.repository.RifaRepository;
import br.com.api.service.SequenceGeneratorService;

@RestController
public class RifaController {

	@Autowired
	private RifaRepository repository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/numeros")
	public List<Rifa> getRifas(){
		return repository.findAll();
	}
	
	@GetMapping("/numeros/{id}")
	public ResponseEntity<Optional<Rifa>> getRifa(@PathVariable int id){
		Optional<Rifa> rifa = repository.findById(id);
		return ResponseEntity.ok().body(rifa);
	}
	
	@PostMapping("/numeros")
	public Rifa saveRifa(@RequestBody Rifa rifa) {
		//return repository.save(rifa);
		if(rifa.getId()==0)
			rifa.setId(sequenceGeneratorService.generateSequence(Rifa.SEQUENCE_ID));
		
		return repository.save(rifa);
	}
	
	@PutMapping("/numeros/{id}")
	public ResponseEntity<Rifa> updateRifa(@PathVariable int id, @RequestBody Rifa rifaDetails){
		Optional<Rifa> rifaOptional = repository.findById(id);
				//.orElseThrow(() -> new ResourceNotFoundException("Número não encontrado com esse id: " + id));
		Rifa rifa = rifaOptional.get();
		
		rifa.setNome(rifaDetails.getNome());
		rifa.setNumero(rifaDetails.getNumero());
		rifa.setFralda(rifaDetails.getFralda());
		rifa.setStatus(rifaDetails.getStatus());
		
		final Rifa updatedRifa = repository.save(rifa);
		return ResponseEntity.ok(updatedRifa);
	}
	
	@DeleteMapping("/numeros/{id}")
	public String deleteRifa(@PathVariable int id) {
		repository.deleteById(id);
		return "Número deletado (" + id + ")";
	}
}
