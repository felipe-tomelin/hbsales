package br.com.hbsis.periodo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo, Long> {

    @Query(value = "SELECT COUNT(1) FROM seg_periodo WHERE data_final >= :dataInicio AND id_fornecedor = :fornecedor", nativeQuery = true)
    Long existDataAberta(
            @Param("dataInicio")
                    LocalDate dataInicio,
            @Param("fornecedor")
                    Long fornecedor);
}
