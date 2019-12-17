package br.com.hbsis.periodo;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PeriodoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PeriodoService.class);

    private final PeriodoRepository periodoRepository;
    private final FornecedorService fornecedorService;

    @Autowired
    public PeriodoService(PeriodoRepository periodoRepository, FornecedorService fornecedorService) {
        this.periodoRepository = periodoRepository;
        this.fornecedorService = fornecedorService;
    }

    public void validate(PeriodoDTO periodoDTO){
        LOGGER.info("Validando Periodo");

        if (StringUtils.isEmpty(periodoDTO.getDataInicio().toString())){
            throw new IllegalArgumentException("Data de inicio do Periodo de vendas não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(periodoDTO.getDataFim().toString())){
            throw new IllegalArgumentException("Data do fim do Periodo de vendas não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(periodoDTO.getDataRetirada().toString())){
            throw new IllegalArgumentException("Data de retirada do Periodo de vendas não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(periodoDTO.getDescricao())) {
            throw new IllegalArgumentException("Descrição do Periodo de vendas não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(periodoDTO.getId().toString())){
            throw new IllegalArgumentException("ID de Periodo não deve ser nulo/vazio");
        }

        if (StringUtils.isEmpty(periodoDTO.getId_fornecedor().toString())){
            throw new IllegalArgumentException("ID de fornecedor não deve ser nulo/vazio");
        }

    }

    public PeriodoDTO save(PeriodoDTO periodoDTO){

        this.validate(periodoDTO);

        LOGGER.info("Salvando Periodo");
        LOGGER.debug("Periodo: [{}]", periodoDTO);

        Periodo periodo = new Periodo();

        /*Data Inicio do Periodo*/
        periodo.setDataInicio(periodoDTO.getDataInicio());

        /*Data Fim do Periodo*/
        periodo.setDataFim(periodoDTO.getDataFim());

        /*Data Retirada do Periodo*/
        periodo.setDataRetirada(periodoDTO.getDataRetirada());

        /*Descrição do Periodo*/
        periodo.setDescricao(periodoDTO.getDescricao());

        /*Id do fornecedor*/
        FornecedorDTO fornecedorDTO = fornecedorService.findById(periodoDTO.getId_fornecedor());
        Fornecedor fornecedor = converter(fornecedorDTO);
        periodo.setFornecedor(fornecedor);

        periodo = this.periodoRepository.save(periodo);

        return periodoDTO.of(periodo);
    }

    public Fornecedor converter(FornecedorDTO fornecedorDTO){

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setId(fornecedorDTO.getId());
        return fornecedor;
    }

    public PeriodoDTO findById(Long id){
        Optional<Periodo> periodoOptional = this.periodoRepository.findById(id);

        if (periodoOptional.isPresent()){
            return PeriodoDTO.of(periodoOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID não existente", id));
    }

    public PeriodoDTO update(PeriodoDTO periodoDTO, Long id){
        Optional<Periodo> periodoExistenteOptional = this.periodoRepository.findById(id);

        if (periodoExistenteOptional.isPresent()){
            Periodo periodoExistente = periodoExistenteOptional.get();

            LOGGER.info("Atualizando, br.com.hbsis.periodo... id: [{}]", periodoExistente.getId());
            LOGGER.debug("Payload: {}", periodoDTO);
            LOGGER.debug("Categoria existente: {}", periodoExistente);

            periodoExistente.setId(periodoDTO.getId());
            periodoExistente.setDataInicio(periodoDTO.getDataInicio());
            periodoExistente.setDataFim(periodoDTO.getDataFim());
            periodoExistente.setDataRetirada(periodoDTO.getDataRetirada());
            periodoExistente.setDescricao(periodoDTO.getDescricao());

            FornecedorDTO fornecedorDTO = fornecedorService.findById(periodoDTO.getId_fornecedor());
            Fornecedor fornecedor = converter(fornecedorDTO);
            periodoExistente.setFornecedor(fornecedor);

            periodoExistente = this.periodoRepository.save(periodoExistente);

            return periodoDTO.of(periodoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id){
        LOGGER.info("Executando delete para Periodo de ID: [{}]", id);
        this.periodoRepository.deleteById(id);
    }
}