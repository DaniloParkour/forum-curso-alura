package br.com.alura.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

/*Interface repository que herda de JPARepository vários métodos prontos e recorrentes
 * nos projetos como listar todos, obter por id, deletar, adicionar, alterar, ...
 * São passados ainda dois tipos <Entidade, TipoAtributoID>*/
public interface TopicosRepository extends JpaRepository<Topico, Long> {
	
}
