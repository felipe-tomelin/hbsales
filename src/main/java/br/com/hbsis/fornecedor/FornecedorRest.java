package br.com.hbsis.fornecedor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Classe resposável por receber as requisições externas ao sistema
 */

@RestController
@RequestMapping("/fornecedores")
public class FornecedorRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorRest.class);

    private final FornecedorService fornecedorService;

    @Autowired
    public FornecedorRest(FornecedorService fornecedorService) { this.fornecedorService = fornecedorService; }

    @PostMapping
    public FornecedorDTO save(@RequestBody FornecedorDTO fornecedorDTO) {
        LOGGER.info("Recebendo solicitação de persistência de Fornecedor");
        LOGGER.debug("Payload: {}", fornecedorDTO);

        return this.fornecedorService.save(fornecedorDTO);
    }

    @GetMapping("/{Id}")
    public FornecedorDTO find(@PathVariable("Id") Long Id) {
        LOGGER.info("Recebendo find by id, Id: [{}]", Id);

        return this.fornecedorService.findById(Id);
    }

    @PutMapping("/{id}")
    public FornecedorDTO update(@PathVariable("id") Long Id, @RequestBody FornecedorDTO fornecedorDTO) {
        LOGGER.info("Recebendo update para Fornecedor  de ID: {}", Id);
        LOGGER.debug("Payload: {}", fornecedorDTO);

        return this.fornecedorService.update(fornecedorDTO, Id);
    }

    @DeleteMapping("/{Id}")
    public void delete(@PathVariable("Id") Long Id) {
        LOGGER.info("Deletando Fornecedor de ID: {}", Id);

        this.fornecedorService.delete(Id);
    }
}
