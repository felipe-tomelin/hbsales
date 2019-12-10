package br.com.hbsis.produto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/produto")
public class ProdutoRest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoRest.class);

    private final ProdutoService produtoService;

    @Autowired
    public ProdutoRest(ProdutoService produtoService) { this.produtoService = produtoService; }

    @PostMapping
    public ProdutoDTO save(@RequestBody ProdutoDTO produtoDTO) {
        LOGGER.info("Recebendo solicitação de persistencia de Produto");
        LOGGER.debug("Payload: {}", produtoDTO);

        return this.produtoService.save(produtoDTO);
    }

    @GetMapping("/{id}")
    public ProdutoDTO find(@PathVariable("id") Long id){
        LOGGER.info("Recebendo find by id, ID: [{}]", id);

        return  this.produtoService.findById(id);
    }

    @GetMapping("/exportcsv")
    public void exportCSV(HttpServletResponse file) throws Exception {
        produtoService.findAll(file);
    }

    @PostMapping("/importcsv")
    public void importCSV(@RequestParam("file")MultipartFile file) throws Exception {
        produtoService.reconhecer(file);
    }

    @PutMapping("/{id}")
    public ProdutoDTO update(@PathVariable("id") Long id, @RequestBody ProdutoDTO produtoDTO){
        LOGGER.info("Recebendo update para Produto de ID: {}", id);
        LOGGER.debug("Payload: {}", produtoDTO);

        return  this.produtoService.update(produtoDTO, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        LOGGER.info("Deletando Produto de ID: {}", id);

        this.produtoService.delete(id);
    }
}
