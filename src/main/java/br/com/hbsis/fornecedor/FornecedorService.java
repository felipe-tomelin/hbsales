package br.com.hbsis.fornecedor;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Classe responsável pelo processamento da regra de negócio
 */

@Service
public class FornecedorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorService.class);

    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {

        this.validate(fornecedorDTO);

        LOGGER.info("Salvado Fornecedor");
        LOGGER.debug("Fornecedor: {}", fornecedorDTO);

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial(fornecedorDTO.getRazaoSocial());
        fornecedor.setCnpj(fornecedorDTO.getCnpj());
        fornecedor.setNomeFantasia(fornecedorDTO.getNomeFantasia());
        fornecedor.setEndereco(fornecedorDTO.getEndereco());
        fornecedor.setTelefone(fornecedorDTO.getTelefone());
        fornecedor.setEmail(fornecedorDTO.getEmail());

        fornecedor = this.fornecedorRepository.save(fornecedor);

        return fornecedorDTO.of(fornecedor);
    }

    private void validate(FornecedorDTO fornecedorDTO){
            LOGGER.info("Validando Fornecedor");

            String tel = fornecedorDTO.getTelefone();

            if(tel.charAt(5) != '9'){
                throw new IllegalArgumentException("Informe apenas telefones celulares");
            }

        if(StringUtils.isEmpty(fornecedorDTO.getRazaoSocial())){
            throw new IllegalArgumentException("Razao Social não deve ser nulo/vazio");
        }

        if(StringUtils.isEmpty(fornecedorDTO.getCnpj())){
            throw new IllegalArgumentException("Cnpj não deve ser nulo/vazio");
        }

        if(StringUtils.isEmpty(fornecedorDTO.getNomeFantasia())){
            throw new IllegalArgumentException("Nome fantasia não deve ser nulo/vazio");
        }

        if(StringUtils.isEmpty(fornecedorDTO.getEndereco())){
            throw new IllegalArgumentException("Endereço não deve ser nulo/vazio");
        }

        if(fornecedorDTO.getTelefone() == null){
            throw new IllegalArgumentException("Telefone não deve ser nulo");
        }

        if(StringUtils.isEmpty(fornecedorDTO.getEmail())){
            throw new IllegalArgumentException("Email não deve ser nulo/vazio");
        }
    }

    public FornecedorDTO findById(Long id){

        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        if(fornecedorOptional.isPresent()){
            Fornecedor fornecedor = fornecedorOptional.get();
            FornecedorDTO fornecedorDTO = FornecedorDTO.of(fornecedor);

            return fornecedorDTO;
        }
        throw new IllegalArgumentException(String.format("ID %s não existe", id));
    }


    public FornecedorDTO update(FornecedorDTO fornecedorDTO, Long Id) {
        Optional<Fornecedor> fornecedorExistenteOptional = this.fornecedorRepository.findById(Id);

        if (fornecedorExistenteOptional.isPresent()) {
            Fornecedor fornecedorExistente = fornecedorExistenteOptional.get();

            LOGGER.info("Atualizando br.com.hbsis.fornecedor... id: [{}]", fornecedorExistente.getId());
            LOGGER.debug("Payload: {}", fornecedorDTO);
            LOGGER.debug("Fornecedor existente: {}", fornecedorExistente);

            fornecedorExistente.setRazaoSocial(fornecedorDTO.getRazaoSocial());
            fornecedorExistente.setCnpj(fornecedorDTO.getCnpj());
            fornecedorExistente.setNomeFantasia(fornecedorDTO.getNomeFantasia());
            fornecedorExistente.setTelefone(fornecedorDTO.getTelefone());
            fornecedorExistente.setEndereco(fornecedorDTO.getEndereco());
            fornecedorExistente.setEmail(fornecedorDTO.getEmail());

            fornecedorExistente = this.fornecedorRepository.save(fornecedorExistente);

            return fornecedorDTO.of(fornecedorExistente);
        }

        throw new IllegalArgumentException(String.format("ID %s não existe", Id));

    }

        public void delete(Long Id) {
            LOGGER.info("Executando delete para Fornecedor de Id: [{}]", Id);

            this.fornecedorRepository.deleteById(Id);
        }

    public FornecedorDTO findByCnpj(String cnpj){

        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findByCnpj(cnpj);

        if(fornecedorOptional.isPresent()){
            Fornecedor fornecedor = fornecedorOptional.get();
            FornecedorDTO fornecedorDTO = FornecedorDTO.of(fornecedor);

            return fornecedorDTO;
        }

        String format = String.format("Cnpj %s não existe", cnpj);

        throw new IllegalArgumentException(format);
    }

    public boolean findByIdFornecedor(Long id) {
        Optional<Fornecedor> fornecedorOptional = this.fornecedorRepository.findById(id);

        boolean valida;

        if (fornecedorOptional.isPresent()) {
            valida = true;

            return valida;
        }else {
            valida = false;

            return valida;
        }

    }

}
