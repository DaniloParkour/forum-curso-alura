package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

/*Interface repository que herda de JPARepository vários métodos prontos e recorrentes
 * nos projetos como listar todos, obter por id, deletar, adicionar, alterar, ...
 * São passados ainda dois tipos <Entidade, TipoAtributoID>*/
public interface TopicosRepository extends JpaRepository<Topico, Long> {

	/* O Spring já cria automaticamente pelo nome do método o "findBy" na tabela
	 * "Curso" na coluna "Nome" */
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
	
	/*OBS: Para diferenciar quando há ambiguidade. Por exemplo, se houvesse também a
	 * coluna cursoNome. Se houver ambuiguidade pode usar o findByCurso_Nome que nesse
	 * caso já passa a não ser mais para o campo cursoNome e sim para nome na tabela curso*/
	
	//findByCursoCategoriaZonaNome => Pesquisa pelo campo nome em Curso->Categoria-Zona
	
	//Caso queria usar outra nomenclatura para o método e infomrar manualmente a consulta
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> listarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
}
