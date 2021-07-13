package br.com.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@PostMapping("/numero")
	public Rifa saveRifa(@RequestBody Rifa rifa) {
		//return repository.save(rifa);
		if(rifa.getId()==0)
			rifa.setId(sequenceGeneratorService.generateSequence(Rifa.SEQUENCE_ID));
		
		return repository.save(rifa);
	}
	
	@GetMapping("/numero")
	public List<Rifa> getRifas(){
		return repository.findAll();
	}
	
	@GetMapping("/numero/{rifaId}")
	public Optional<Rifa> getRifa(@PathVariable int rifaId){
		return repository.findById(rifaId);
	}
	
	@DeleteMapping("/numero/{id}")
	public String deleteRifa(@PathVariable int id) {
		repository.deleteById(id);
		return "Número deletado (" + id + ")";
	}
}
