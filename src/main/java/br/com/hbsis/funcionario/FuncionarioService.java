package br.com.hbsis.funcionario;

import br.com.hbsis.hbemployee.HBEmployeeDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Optional;

@Service
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);

    private final FuncionarioRepository funcionarioRepository;

    private final RestTemplate restTemplate;


    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository, RestTemplate restTemplate) {
        this.funcionarioRepository = funcionarioRepository;
        this.restTemplate = restTemplate;
    }

    public void validate(FuncionarioDTO funcionarioDTO){
        LOGGER.info("Validando funcionario");

        if (StringUtils.isEmpty(funcionarioDTO.getNome())){
            throw new IllegalArgumentException("Nome de funcionario não deve ser vazio");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getEmail())){
            throw new IllegalArgumentException("Email de funcionario não deve ser vazio");
        }

        if (StringUtils.isEmpty(funcionarioDTO.getUuid())){
            throw new IllegalArgumentException("UUID de funcionario não deve ser vazio");
        }

        if (funcionarioDTO.getId() == null){
            throw new IllegalArgumentException("ID de funcionario não deve ser vazio");
        }
    }

    public FuncionarioDTO save(FuncionarioDTO funcionarioDTO){

        LOGGER.info("Salvando Funcionario");

        this.validate(funcionarioDTO);

        LOGGER.debug("Funcionario: [{}]", funcionarioDTO);

        Funcionario funcionario = new Funcionario(funcionarioDTO.getNome(), funcionarioDTO.getEmail(), this.validaFuncionarioAPI(funcionarioDTO).getEmployeeUuid());

        funcionario = this.funcionarioRepository.save(funcionario);

        LOGGER.info("Funcionario validado na API e Cadastrado");

        return  funcionarioDTO.of(funcionario);
    }

    public FuncionarioDTO findById(Long id){
        Optional<Funcionario> funcionarioOptional = this.funcionarioRepository.findById(id);

        if (funcionarioOptional.isPresent()){
            return FuncionarioDTO.of(funcionarioOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID não existente", id));
    }

    public HBEmployeeDTO validaFuncionarioAPI(FuncionarioDTO funcionarioDTO){
        LOGGER.info("Validando funcionário na API HBEmployee");

        try{
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "f59ffa10-1b67-11ea-978f-2e728ce88125");

            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<FuncionarioDTO> httpEntityFun = new HttpEntity<>(funcionarioDTO, httpHeaders);

            LOGGER.info("Executando return");

            return this.restTemplate.postForObject("http://10.2.54.25:9999/api/employees", httpEntityFun, HBEmployeeDTO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Falha ao validar funcionario");
    }

}
