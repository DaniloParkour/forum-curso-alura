package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicosRepository;

//@Controller
@RestController //Com essa anotação não é mais necessário colocar o @ResponseBody nos endpoints
@RequestMapping("/topicos") //Agora "/topicos" entra como base para todos os métodos definidos nessa classe
public class TopicosController {
	
	@Autowired
	private TopicosRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

	// @ResponseBody //Informar que o retorno não é uma página da aplicação e sim uma resposta do endpoint
	// @RequestMapping(value = "/topicos", method = RequestMethod.Get) //Pode ser substituido por "@GetMapping"
	@GetMapping
	public List<TopicoDTO> lista(String nomeCurso) {
		List<Topico> topicos;
		if(nomeCurso == null)
			topicos = topicoRepository.findAll();
		else
			topicos = topicoRepository.findByCursoNome(nomeCurso);
		return TopicoDTO.converter(topicos);
	}
	
	@PostMapping
	public ResponseEntity<TopicoDTO> carastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) {
		
		//Caso queria pegar um campo para alguma validação mas específica
		/* if(topicoForm.getTitulo().startsWith("Exemplo")) {
			// ...
		} */
		
		Topico topico = topicoForm.converter(cursoRepository);
        topicoRepository.save(topico);
        
        URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}
	
	@GetMapping("/{id}")
    public DetalhesDoTopicoDTO detalhar(@PathVariable Long id) { //Se fosse a outra variavel (?id=5), sem o "/id", não precisaria colocar uma anotação 
    Topico topico = topicoRepository.getOne(id);
    return new DetalhesDoTopicoDTO(topico);
    }

}
