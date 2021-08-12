package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}") //Inteja valores do application.properties
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		return Jwts.builder()
				.setIssuer("API forum curso Alura") //Que é a aplicação que fez a geração do token
				.setSubject(usuarioLogado.getId().toString()) //Que é o usuário dono do token
				.setIssuedAt(hoje) //Data de criação do token
				.setExpiration(dataExpiracao) //Quando expira o token
				.signWith(SignatureAlgorithm.HS256, secret) //O Token tem que ser criptografado
				.compact() //Compactar tudo em uma string
				;
	}

	public boolean isTokenValido(String token) {
		try {
			//parseClaimsJws retorna os dados do token ou caso não seja válido lança uma exceção
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
