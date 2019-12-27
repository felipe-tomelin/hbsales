package br.com.hbsis.categoriaProduto;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categorias")
public class CategoriaRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaRest.class);

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaRest(CategoriaService categoriaService) { this.categoriaService = categoriaService; }

    @PostMapping
    public  CategoriaProdutoDTO save(@RequestBody CategoriaProdutoDTO categoriaProdutoDTO) {
        LOGGER.info("Recebendo solcitação de persistencia de Categoria");
        LOGGER.debug("Payload: {}", categoriaProdutoDTO);

        return this.categoriaService.save(categoriaProdutoDTO);
    }

    @PostMapping("/importcsv")
    public void importarCSV(@RequestParam("file") MultipartFile file) throws Exception {

        LOGGER.info("Recebendo importação de um CSV...");

        categoriaService.importLeitorCSVForCategoriaService(file);

    }

    @GetMapping("/{id}")
    public CategoriaProdutoDTO findById(@PathVariable("id") Long id) {
        LOGGER.info("Recebendo find by id, id: [{}]", id);

        return this.categoriaService.findById(id);
    }

    @GetMapping("/exportcsv")
    public void exportarCSV(HttpServletResponse file) throws Exception {

        LOGGER.info("Recebendo exportação para CSV... ");

        categoriaService.exportCSVForCategoriaService(file);
    }

    @PutMapping("/{id}")
    public CategoriaProdutoDTO update(@PathVariable("id") Long id, @RequestBody CategoriaProdutoDTO categoriaProdutoDTO){
        LOGGER.info("Recebendo update para Categoria de ID: {}", id);
        LOGGER.debug("Payload: {}", categoriaProdutoDTO);

        return this.categoriaService.update(categoriaProdutoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        LOGGER.info("Deletando Categoria de ID: {}", id);

        this.categoriaService.delete(id);
    }

}
