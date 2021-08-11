package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDTO;
import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
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
	public Page<TopicoDTO> lista(@RequestParam(required = false) String nomeCurso,
								 @RequestParam int pagina, @RequestParam int quandidade,
								 @RequestParam(required = false) String ordenacao) {
		
		Pageable paginacao;
		if(ordenacao != null)
			paginacao = PageRequest.of(pagina, quandidade, Direction.DESC, ordenacao);
		else
			paginacao = PageRequest.of(pagina, quandidade);
		
		Page<Topico> topicos;
		
		if(nomeCurso == null)
			topicos = topicoRepository.findAll(paginacao);
		else
			topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
		return TopicoDTO.converter(topicos);
	}
	
	@PostMapping
	@Transactional
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
	
	@PutMapping("/{id}")
	@Transactional //Avisar ao SPRINT que haverá uma transação
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		Topico topico = form.atualizar(id, topicoRepository);
		return ResponseEntity.ok(new TopicoDTO(topico));
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
	    topicoRepository.deleteById(id);
	    return ResponseEntity.ok().build();
	}

}
