package br.com.alura.forum.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;

@RunWith(SpringRunner.class)
@DataJpaTest //Para classes que testarm REPOSITORIES
// @AutoConfigureTestDatabase(replace = AutoConfigureTesteDatabase.Replace.NONE) => Caso queria usar um banco nos testes que não seja o H2. Dessa forma ele não substitui as configurações do application.properties para usar o H2
// @ActiveProfiles("test") => Ao executar essa classe, muda o ambiente de desenvolvimento para "test"
public class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository repository;

	@Test
	public void deveriaCarregarUmCursoAoBuscarPeloNome() {
		String nomeCurso = "HTML 5";
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNotNull(curso);
		Assert.assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void naoDeveriaCarregarUmCursoAoBuscarPorNomeSxistente() {
		String nomeCurso = "Automaquiagem";
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNull(curso);
	}
	
}
