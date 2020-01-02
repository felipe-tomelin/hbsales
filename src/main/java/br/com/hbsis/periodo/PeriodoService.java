package br.com.hbsis.periodo;

import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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

    public void validate(PeriodoDTO periodoDTO) {
        LOGGER.info("Validando Periodo");

        if (periodoDTO.getDataInicio() == null) {
            throw new IllegalArgumentException("Data de inicio do Periodo de vendas não deve ser nula/vazia");
        }

        if (periodoDTO.getDataFim() == null) {
            throw new IllegalArgumentException("Data do fim do Periodo de vendas não deve ser nula/vazia");
        }

        if (periodoDTO.getDataRetirada() == null) {
            throw new IllegalArgumentException("Data de retirada do Periodo de vendas não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(periodoDTO.getDescricao())) {
            throw new IllegalArgumentException("Descrição do Periodo de vendas não deve ser nula/vazia");
        }

        if (StringUtils.isEmpty(periodoDTO.getIdFornecedor().toString())) {
            throw new IllegalArgumentException("ID de fornecedor não deve ser nulo/vazio");
        }

        if (periodoDTO.getDataRetirada().isBefore(periodoDTO.getDataFim())){
            throw new IllegalArgumentException("Data de retirada não pode ser anterior a data de fim do periodo");
        }

        if (periodoDTO.getDataFim().isBefore(periodoDTO.getDataInicio())){
            throw new IllegalArgumentException("Data do fim não pode ser anterior a data de inicio do periodo");
        }

        validatePeriodoFinalAndStartDate(periodoDTO);

        validateDatasIsBefore(periodoDTO);
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
        FornecedorDTO fornecedorDTO = fornecedorService.findById(periodoDTO.getIdFornecedor());
        Fornecedor fornecedor = converter(fornecedorDTO);
        periodo.setFornecedor(fornecedor);

        periodo = this.periodoRepository.save(periodo);

        return periodoDTO.of(periodo);
    }

    private void validateDatasIsBefore(PeriodoDTO periodoDTO) {
        if (periodoDTO.getDataInicio().isBefore(LocalDate.now()) || periodoDTO.getDataFim().isBefore(LocalDate.now()) || periodoDTO.getDataRetirada().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Datas não podem ser anteriores a hoje");
        }
    }

    private void validatePeriodoFinalAndStartDate(PeriodoDTO periodoDTO) {

        List<Periodo> periodoList = this.periodoRepository.findAllByFornecedor_Id(periodoDTO.getIdFornecedor());

        for(Periodo periodo : periodoList){

            if (periodoDTO.getDataInicio().isEqual(periodo.getDataInicio())) {
                throw new IllegalArgumentException("Periodo já cadastrada com esa data de início.");
            }
            if (periodoDTO.getDataInicio().isBefore(periodo.getDataInicio()) && periodoDTO.getDataFim().isAfter(periodo.getDataFim())) {
                throw new IllegalArgumentException("Caso1");
            }
            if (periodoDTO.getDataInicio().isAfter(periodo.getDataInicio()) && periodoDTO.getDataFim().isBefore(periodo.getDataFim())) {
                throw new IllegalArgumentException("Caso2");
            }
            if (periodoDTO.getDataInicio().isBefore(periodo.getDataInicio()) && periodoDTO.getDataFim().isAfter(periodo.getDataInicio())) {
                throw new IllegalArgumentException("Caso3");
            }
            if (periodoDTO.getDataInicio().isBefore(periodo.getDataFim()) && periodoDTO.getDataFim().isAfter(periodo.getDataFim())) {
                throw new IllegalArgumentException("Caso4");
            }
        }
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

        if (periodoDTO.getDataFim().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Um periodo de vendas não pode ser mais alterado após o termino de sua vigência");
        }else {

            if (periodoExistenteOptional.isPresent()) {
                Periodo periodoExistente = periodoExistenteOptional.get();

                LOGGER.info("Atualizando, br.com.hbsis.periodo... id: [{}]", periodoExistente.getId());
                LOGGER.debug("Payload: {}", periodoDTO);
                LOGGER.debug("Categoria existente: {}", periodoExistente);

                periodoExistente.setId(periodoDTO.getId());
                periodoExistente.setDataInicio(periodoDTO.getDataInicio());
                periodoExistente.setDataFim(periodoDTO.getDataFim());
                periodoExistente.setDataRetirada(periodoDTO.getDataRetirada());
                periodoExistente.setDescricao(periodoDTO.getDescricao());

                FornecedorDTO fornecedorDTO = fornecedorService.findById(periodoDTO.getIdFornecedor());
                Fornecedor fornecedor = converter(fornecedorDTO);
                periodoExistente.setFornecedor(fornecedor);

                periodoExistente = this.periodoRepository.save(periodoExistente);

                return periodoDTO.of(periodoExistente);
            }
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }

    public void delete(Long id){
        LOGGER.info("Executando delete para Periodo de ID: [{}]", id);
        this.periodoRepository.deleteById(id);
    }
}
