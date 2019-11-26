package br.com.hbsis.categoriaProduto;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaProdutoDTO save(CategoriaProdutoDTO categoriaProdutoDTO){

        this.validate(categoriaProdutoDTO);

        LOGGER.info("Salvando Categoria");
        LOGGER.debug("Categoria: {}", categoriaProdutoDTO);

        Categoria categoria = new Categoria();
        categoria.setFornecedor_categoria(categoriaProdutoDTO.getFornecedor_categoria());
        categoria.setNome_categoria(categoriaProdutoDTO.getNome_categoria());

        categoria = this.categoriaRepository.save(categoria);

        return categoriaProdutoDTO.of(categoria);
    }

    private void validate(CategoriaProdutoDTO categoriaProdutoDTO){
        LOGGER.info("Validando Categoria");

        if(StringUtils.isEmpty(categoriaProdutoDTO.getFornecedor_categoria().toString())) {
            throw new IllegalArgumentException("Categoria não deve ser nula/vazia");
        }
    }

    public CategoriaProdutoDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if(categoriaOptional.isPresent()){
            return CategoriaProdutoDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException((String.format("ID não existente", id)));
    }

    public  CategoriaProdutoDTO update(CategoriaProdutoDTO categoriaProdutoDTO, Long id){
        Optional<Categoria> categoriaExistenteOptional = this.categoriaRepository.findById(id);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando, br.com.hbsis.categoria... id: [{}]", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaProdutoDTO);
            LOGGER.debug("Categoria existente: {}", categoriaExistente);

            categoriaExistente.setNome_categoria(categoriaProdutoDTO.getNome_categoria());
            categoriaExistente.setFornecedor_categoria(categoriaProdutoDTO.getFornecedor_categoria());

            categoriaExistente = this.categoriaRepository.save(categoriaExistente);

            return categoriaProdutoDTO.of(categoriaExistente);
        }

        throw new IllegalArgumentException(String.format("ID não existente", id));

    }

        public void delete(Long id){
            LOGGER.info("Executando delete para Categoria de ID: [{}]", id);

            this.categoriaRepository.deleteById(id);
        }
}
