package br.com.hbsis.linhaCategoria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/linhaCategoria")
public class LinhaCategoriaRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaRest.class);

    private final LinhaCategoriaService linhaCategoriaService;

    @Autowired
    public LinhaCategoriaRest(LinhaCategoriaService linhaCategoriaService) { this.linhaCategoriaService = linhaCategoriaService; }

    @PostMapping
    public LinhaCategoriaDTO save(@RequestBody LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Recebendo solicitação de persistencia de Linha de Categoria");
        LOGGER.debug("Payload: {}", linhaCategoriaDTO);

        return this.linhaCategoriaService.save(linhaCategoriaDTO);
    }

    @GetMapping("/{id}")
    public LinhaCategoriaDTO find(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by id, id: [{}]", id);

        return this.linhaCategoriaService.findById(id);
    }

    @GetMapping("/exportcsv")
    public void exportCSV(HttpServletResponse file) throws Exception {
        linhaCategoriaService.exportBuilderCSVForLinhaCategoria(file);
    }

    @PostMapping("/importcsv")
        public void importCSV(@RequestParam("file")MultipartFile file) throws Exception {
        linhaCategoriaService.importReaderCSVForLinhaCategoria(file);
    }

    @PutMapping("/{id}")
    public LinhaCategoriaDTO update(@PathVariable("id") Long id, @RequestBody LinhaCategoriaDTO linhaCategoriaDTO){
        LOGGER.info("Recebendo update para Linha de Categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", linhaCategoriaDTO);

        return this.linhaCategoriaService.update(linhaCategoriaDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Deletando linha de categoria de ID: {}", id);

        this.linhaCategoriaService.delete(id);
    }

}
