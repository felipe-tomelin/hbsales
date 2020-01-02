package br.com.hbsis.periodo;

import br.com.hbsis.fornecedor.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

    List<Periodo> findAllByFornecedor_Id(Long idFornecedor);
}
