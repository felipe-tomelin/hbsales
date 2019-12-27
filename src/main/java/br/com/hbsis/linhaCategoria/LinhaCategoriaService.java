package br.com.hbsis.linhaCategoria;

import br.com.hbsis.categoriaProduto.Categoria;
import br.com.hbsis.categoriaProduto.CategoriaProdutoDTO;
import br.com.hbsis.categoriaProduto.CategoriaService;
import com.opencsv.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LinhaCategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaCategoriaService.class);

    private final LinhaCategoriaRepository linhaCategoriaRepository;
    private final CategoriaService categoriaService;

    public LinhaCategoriaService(LinhaCategoriaRepository linhaCategoriaRepository, CategoriaService categoriaService) {
        this.linhaCategoriaRepository = linhaCategoriaRepository;
        this.categoriaService = categoriaService;
    }

    public String leftPadCompleteWithZeroForCodigoLinha(String codigoLinha) {
        String codigoProcessado = StringUtils.leftPad(codigoLinha, 10, "0");

        return codigoProcessado;
    }

    public void validate(LinhaCategoriaDTO linhaCategoriaDTO) {
        LOGGER.info("Validando Linha de Categoria");

        Long idLinha = linhaCategoriaDTO.getIdLinha();

        Optional<LinhaCategoria> linhaCategoriaExistenteOptional = this.linhaCategoriaRepository.findById(idLinha);

        if (linhaCategoriaExistenteOptional.isPresent()) {
            if (linhaCategoriaDTO.getId_categoria() == null) {
                throw new IllegalArgumentException("Cadastro inválido, ID de Categoria já existe");
            }

            if (StringUtils.isEmpty(linhaCategoriaDTO.getNome_linha())) {
                throw new IllegalArgumentException("Nome da linha não deve ser nulo");
            }

            if (StringUtils.isEmpty(linhaCategoriaDTO.getCodigoLinha())) {
                throw new IllegalArgumentException("Codigo da linha não deve ser nulo");
            } else {
                LOGGER.info(String.format("ID Categoria %s não está vinculada", idLinha));
            }
        } else {
            LOGGER.info(String.format("ID %s cadastrado com sucesso", idLinha));
        }

        if (StringUtils.isEmpty(linhaCategoriaDTO.getId_categoria().toString())) {
            throw new IllegalArgumentException("ID da Categoria não deve ser nulo");
        }

        if (StringUtils.isEmpty(linhaCategoriaDTO.getIdLinha().toString())) {
            throw new IllegalArgumentException("ID da linha não deve ser nulo");
        }
    }

    public String codigoLinhaCategoriaConcatenado(){

        LinhaCategoriaDTO linhaCategoriaDTO = new LinhaCategoriaDTO();

        String codigolinha = linhaCategoriaDTO.getCodigoLinha();
        String codigoLinhaUpperCase = codigolinha.toUpperCase();
        String cnpjProcessado = leftPadCompleteWithZeroForCodigoLinha(codigoLinhaUpperCase);

        return cnpjProcessado;
    }

    public LinhaCategoriaDTO save(LinhaCategoriaDTO linhaCategoriaDTO) {

        this.validate(linhaCategoriaDTO);

        LOGGER.info("Salvando Linha de Categoria");
        LOGGER.debug("Linha de Categoria: {}", linhaCategoriaDTO);

        LinhaCategoria linhaCategoria = new LinhaCategoria();

        linhaCategoria.setNome_linha(linhaCategoriaDTO.getNome_linha());

        linhaCategoria.setCodigoLinha(codigoLinhaCategoriaConcatenado());

        CategoriaProdutoDTO categoriaProdutoDTO = categoriaService.findById(linhaCategoriaDTO.getIdLinha());

        Categoria categoria = converter(categoriaProdutoDTO);

        linhaCategoria.setCategoria(categoria);

        linhaCategoria = this.linhaCategoriaRepository.save(linhaCategoria);

        return linhaCategoriaDTO.of(linhaCategoria);
    }

    public Categoria converter(CategoriaProdutoDTO categoriaProdutoDTO) {

        Categoria categoria = new Categoria();

        categoria.setId(categoriaProdutoDTO.getId());

        return categoria;
    }

    public LinhaCategoriaDTO findById(Long id) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.linhaCategoriaRepository.findById(id);

        if (linhaCategoriaOptional.isPresent()) {
            return LinhaCategoriaDTO.of(linhaCategoriaOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existente", id));
    }

    public void exportBuilderCSVForLinhaCategoria(HttpServletResponse resposta) throws Exception {
        String arquivo = "linhaCategoria.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter writer = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(writer).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"codigo", "nome", "codigo_categoria", "nome_categoria"};
        icsvWriter.writeNext(tituloCSV);

        for (LinhaCategoria rows : linhaCategoriaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{rows.getCodigoLinha(), rows.getNome_linha(), rows.getCategoria().getCodigoCategoria(), rows.getCategoria().getNome_categoria()});
        }
    }

    public List<LinhaCategoria> importReaderCSVForLinhaCategoria(MultipartFile importacao) throws Exception {

        InputStreamReader inserir = new InputStreamReader(importacao.getInputStream());

        CSVReader reader = new CSVReaderBuilder(inserir).withSkipLines(1).build();

        List<String[]> row = reader.readAll();
        List<LinhaCategoria> result = new ArrayList<>();

        for (String[] linha : row) {
            try {
                String[] dados = linha[0].replaceAll("\"", "").split(";");

                LinhaCategoria linhaCategoria = new LinhaCategoria();

                boolean valida = findExistsByCodigoLinhaCategoria(dados[0]);

                if (valida == false) {
                    linhaCategoria.setCodigoLinha(dados[0]);
                    linhaCategoria.setNome_linha(dados[1]);

                    Categoria categoria = new Categoria();
                    CategoriaProdutoDTO categoriaProdutoDTO = this.categoriaService.findByCodigoCategoria(dados[2]);
                    categoria = converter(categoriaProdutoDTO);

                    linhaCategoria.setCategoria(categoria);

                    result.add(linhaCategoria);

                    LOGGER.info("Validando importação");

                    return linhaCategoriaRepository.saveAll(result);
                } else if (valida == true) {
                    LOGGER.info("Linha de Categoria já cadastrada no banco de dados...");
                }
            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
        return linhaCategoriaRepository.saveAll(result);
    }

    public LinhaCategoriaDTO update(LinhaCategoriaDTO linhaCategoriaDTO, Long id) {
        Optional<LinhaCategoria> linhaCategoriaExistenteOptional = this.linhaCategoriaRepository.findById(id);

        this.validate(linhaCategoriaDTO);

        if (linhaCategoriaExistenteOptional.isPresent()) {
            LinhaCategoria linhaCategoriaExistente = linhaCategoriaExistenteOptional.get();

            LOGGER.info("Atualizando, br.com.hbsis.LinhaCategoria... id: [{}]", LinhaCategoria.class.getName());
            LOGGER.debug("Payload: {}", linhaCategoriaDTO);
            LOGGER.debug("Categoria existente: {}", LinhaCategoria.class.getName());

            linhaCategoriaExistente.setId(linhaCategoriaDTO.getIdLinha());
            linhaCategoriaExistente.setNome_linha(linhaCategoriaDTO.getNome_linha());
            linhaCategoriaExistente.setCodigoLinha(codigoLinhaCategoriaConcatenado());

            CategoriaProdutoDTO categoriaProdutoDTO = categoriaService.findById(linhaCategoriaDTO.getIdLinha());
            Categoria categoria = converter(categoriaProdutoDTO);

            linhaCategoriaExistente.setCategoria(categoria);

            linhaCategoriaExistente = this.linhaCategoriaRepository.save(linhaCategoriaExistente);

            return linhaCategoriaDTO.of(linhaCategoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existente", id));
    }

    public void delete(Long id) {
        LOGGER.info("Executando delete para Linha de Categoria de ID: [{}]", id);
        this.linhaCategoriaRepository.deleteById(id);
    }

    public LinhaCategoriaDTO findByCodigoLinha(String codigo) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.linhaCategoriaRepository.findByCodigoLinha(codigo);

        if (linhaCategoriaOptional.isPresent()) {
            LinhaCategoria linhaCategoria = linhaCategoriaOptional.get();
            LinhaCategoriaDTO linhaCategoriaDTO = LinhaCategoriaDTO.of(linhaCategoria);

            return linhaCategoriaDTO;
        }

        String format = String.format("Codigo da linha %s não existe", codigo);

        throw new IllegalArgumentException(format);
    }

    public boolean findExistsByCodigoLinhaCategoria(String codigo) {
        Optional<LinhaCategoria> linhaCategoriaOptional = this.linhaCategoriaRepository.findByCodigoLinha(codigo);

        boolean valida;

        if (linhaCategoriaOptional.isPresent()) {
            valida = true;

            return valida;
        } else {
            valida = false;

            return valida;
        }
    }
}
