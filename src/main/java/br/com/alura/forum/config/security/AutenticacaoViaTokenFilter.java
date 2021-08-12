package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

//Registrar essa classo como interceptador pro SPRING não é por ANOTACAO,
//tem que configurar no configure() do SecurityConfiguration
public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService; //Nesse tipo de classe não da pra fazer um @Autowired
	
	public AutenticacaoViaTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//Precisamos autenticar o token nas requisições pois é stateless (não sei quem está solicitando quando chega)
		
		String token = recuperarToken(request);
		
		boolean valido = tokenService.isTokenValido(token);
		
		//Com o filtro feito, mandar seguir o fluxo da requisição
		filterChain.doFilter(request, response);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
