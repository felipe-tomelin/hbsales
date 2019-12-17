package br.com.hbsis.periodo;

import br.com.hbsis.produto.ProdutoRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/periodo")
public class PeriodoRest {

    public static final Logger LOGGER = LoggerFactory.getLogger(PeriodoRest.class);

    private final PeriodoService periodoService;

    @Autowired
    public PeriodoRest(PeriodoService periodoService) { this.periodoService = periodoService; }

    @PostMapping
    public PeriodoDTO save(@RequestBody PeriodoDTO periodoDTO){
        LOGGER.info("Recebendo solicitação de pesistencia de Periodo");
        LOGGER.debug("Payload: [{}]", periodoDTO);

        return this.periodoService.save(periodoDTO);
    }

    @GetMapping("/{id}")
    public PeriodoDTO find(@PathVariable("id") Long id){
        LOGGER.info("Recebendo find by id, ID: {}", id);

        return this.periodoService.findById(id);
    }

    @PutMapping("/{id}")
    public PeriodoDTO update(@PathVariable("id") Long id, @RequestBody PeriodoDTO periodoDTO){
        LOGGER.info("Recebendo update para Periodo de ID: {}", id);
        LOGGER.debug("Payload: {}", periodoDTO);

        return this.periodoService.update(periodoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Deletando Periodo de ID: {}", id);

        this.periodoService.delete(id);
    }
}
