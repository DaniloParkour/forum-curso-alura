package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;

//@Controller
@RestController //Com essa anotação não é mais necessário colocar o @ResponseBody nos endpoints
public class TopicosController {
	
	@RequestMapping("/topics")
	// @ResponseBody //Informar que o retorno não é uma página da aplicação e sim uma resposta do endpoint
	public List<Topico> lista() {
		Topico topico = new Topico("Dúvida", "Dúvida com SpringBoot", new Curso("Spring", "Programação"));
		
	}

}
