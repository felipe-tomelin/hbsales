package br.com.hbsis.linhaCategoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LinhaCategoriaRepository extends JpaRepository<LinhaCategoria, Long> {

    Optional<LinhaCategoria> findByCodigoLinha(String codigo);
}
