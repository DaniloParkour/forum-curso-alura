package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicosRepository;

//@Controller
@RestController //Com essa anotação não é mais necessário colocar o @ResponseBody nos endpoints
public class TopicosController {
	
	@Autowired
	private TopicosRepository topicoRepository;

	@RequestMapping("/topics")
	// @ResponseBody //Informar que o retorno não é uma página da aplicação e sim uma resposta do endpoint
	public List<TopicoDTO> lista() {
		List<Topico> topicos = topicoRepository.findAll();
		return TopicoDTO.converter(topicos);
	}

}
