package br.com.hbsis.funcionario;

import br.com.hbsis.hbemployee.HBEmployeeDTO;
import br.com.hbsis.periodo.PeriodoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioRest {

    public static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioRest.class);

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioRest(FuncionarioService funcionarioService) { this.funcionarioService = funcionarioService; }

    @PostMapping
    public FuncionarioDTO save(@RequestBody FuncionarioDTO funcionarioDTO){
        LOGGER.info("Recebendo solicitação de persistencia de Funcionario");
        LOGGER.debug("Payload: [{}]", funcionarioDTO);

        return this.funcionarioService.save(funcionarioDTO);
    }

    @GetMapping("/{id}")
    public FuncionarioDTO find(@PathVariable("id") Long id){
        LOGGER.info("Recebendo find by id, ID: {}", id);

        return this.funcionarioService.findById(id);
    }

}
