package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Topico;

public class TopicoDTO {
	
	private Long id; 
    private String titulo; 
    private String mensagem; 
    private LocalDateTime dataCriacao;
    
    public TopicoDTO(Topico topico) { 
        this.id = topico.getId(); 
        this.titulo = topico.getTitulo();
        this.mensagem = topico.getMensagem();
        this.dataCriacao = topico.getDataCriacao();
    }
    
    //Gets para permitir a serialização do objeto
    public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public static List<TopicoDTO> converter(List<Topico> topicos) {
		//Usando a sintaxe da API de STREAM do JAVA 8 não é preciso escrever o for com o bloco de operações
		return topicos.stream().map(TopicoDTO::new).collect(Collectors.toList());
	}

}
