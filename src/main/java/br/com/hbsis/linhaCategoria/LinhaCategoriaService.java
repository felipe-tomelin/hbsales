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

    public LinhaCategoriaService(LinhaCategoriaRepository linhaCategoriaRepository, CategoriaService categoriaService){
        this.linhaCategoriaRepository = linhaCategoriaRepository;
        this.categoriaService = categoriaService;
    }

    public String codigoValidarLinha (String codigo_linha){
        String codigoProcessado = StringUtils.leftPad(codigo_linha, 10, "0");

        return codigoProcessado;
    }

    public void validate(LinhaCategoriaDTO linhaCategoriaDTO){
        LOGGER.info("Validando Linha de Categoria");

        Long idLinha = linhaCategoriaDTO.getIdLinha();

        Optional<LinhaCategoria> linhaCategoriaExistenteOptional = this.linhaCategoriaRepository.findById(idLinha);

        if(linhaCategoriaExistenteOptional.isPresent()) {
            LinhaCategoria linhaCategoriaExistente = linhaCategoriaExistenteOptional.get();
            LinhaCategoriaDTO linhaCategoriaDTOExistente = LinhaCategoriaDTO.of(linhaCategoriaExistente);

            String idCategoria = linhaCategoriaDTOExistente.getId_categoria().toString();

                if (linhaCategoriaDTO.getId_categoria() == null) {
                    throw new IllegalArgumentException("Cadastro inválido, ID de Categoria já existe");
                }

                if (StringUtils.isEmpty(linhaCategoriaDTO.getNome_linha())) {
                    throw new IllegalArgumentException("Nome da linha não deve ser nulo");
                }

                if (StringUtils.isEmpty(linhaCategoriaDTO.getCodigo_linha())) {
                    throw new IllegalArgumentException("Codigo da linha não deve ser nulo");
                } else {
                    LOGGER.info(String.format("ID Categoria %s não está vinculada", idLinha));
                }
        }else{
            LOGGER.info(String.format("ID %s cadastrado com sucesso", idLinha));
        }

        if(StringUtils.isEmpty(linhaCategoriaDTO.getId_categoria().toString())) {
            throw new IllegalArgumentException("ID da Categoria não deve ser nulo");
        }

        if(StringUtils.isEmpty(linhaCategoriaDTO.getIdLinha().toString())) {
            throw new IllegalArgumentException("ID da linha não deve ser nulo");
        }
    }

    public LinhaCategoriaDTO save(LinhaCategoriaDTO linhaCategoriaDTO){

        this.validate(linhaCategoriaDTO);

        LOGGER.info("Salvando Linha de Categoria");
        LOGGER.debug("Linha de Categoria: {}", linhaCategoriaDTO);

        LinhaCategoria linhaCategoria = new LinhaCategoria();

        linhaCategoria.setNome_linha(linhaCategoriaDTO.getNome_linha());

            String codigolinha = linhaCategoriaDTO.getCodigo_linha();
            String codigoLinhaUpperCase = codigolinha.toUpperCase();
            String cnpjProcessado = codigoValidarLinha(codigoLinhaUpperCase);

        linhaCategoria.setCodigo_linha(cnpjProcessado);

        CategoriaProdutoDTO categoriaProdutoDTO = categoriaService.findById(linhaCategoriaDTO.getIdLinha());

        Categoria categoria = converter(categoriaProdutoDTO);

        linhaCategoria.setCategoria(categoria);

        linhaCategoria = this.linhaCategoriaRepository.save(linhaCategoria);

        return linhaCategoriaDTO.of(linhaCategoria);
    }

    public Categoria converter(CategoriaProdutoDTO categoriaProdutoDTO){

        Categoria categoria = new Categoria();

        categoria.setId(categoriaProdutoDTO.getId());

        return categoria;
    }

    public LinhaCategoriaDTO findById(Long id){
        Optional<LinhaCategoria> linhaCategoriaOptional = this.linhaCategoriaRepository.findById(id);

        if (linhaCategoriaOptional.isPresent()){
            return LinhaCategoriaDTO.of(linhaCategoriaOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID %s não existente", id));
    }

    public void findAll (HttpServletResponse resposta) throws Exception{
        String arquivo = "linhaCategoria.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter writer = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(writer).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String [] tituloCSV = { "codigo", "nome", "codigo_categoria", "nome_categoria"};
        icsvWriter.writeNext(tituloCSV);

        for (LinhaCategoria rows : linhaCategoriaRepository.findAll()){
            icsvWriter.writeNext(new String[]{rows.getCodigo_linha(), rows.getNome_linha(), rows.getCategoria().getCodigo(), rows.getCategoria().getNome_categoria()});
        }
    }

    public List<LinhaCategoria> reconhecer(MultipartFile importacao) throws Exception{

        InputStreamReader inserir = new InputStreamReader(importacao.getInputStream());

        CSVReader reader = new CSVReaderBuilder(inserir).withSkipLines(1).build();

        List<String[]> row = reader.readAll();
        List<LinhaCategoria> result = new ArrayList<>();

        for (String[] linha : row){
            try{
                String[] dados = linha[0].replaceAll("\"", "").split(";");

                LinhaCategoria linhaCategoria = new LinhaCategoria();

                linhaCategoria.setCodigo_linha(dados[0]);
                linhaCategoria.setNome_linha(dados[1]);

                Categoria categoria = new Categoria();
                CategoriaProdutoDTO categoriaProdutoDTO = this.categoriaService.findByCodigo(dados[2]);
                categoria = converter(categoriaProdutoDTO);

                linhaCategoria.setCategoria(categoria);

                result.add(linhaCategoria);

                LOGGER.info("Validando importação");
            }catch (Exception e){
                System.out.println("ERRO: "+e.getMessage());
            }
        }
        return linhaCategoriaRepository.saveAll(result);
    }

    public LinhaCategoriaDTO update(LinhaCategoriaDTO linhaCategoriaDTO, Long id){
        Optional<LinhaCategoria> linhaCategoriaExistenteOptional = this.linhaCategoriaRepository.findById(id);

        if(linhaCategoriaExistenteOptional.isPresent()){
            LinhaCategoria linhaCategoriaExistente = linhaCategoriaExistenteOptional.get();

            LOGGER.info("Atualizando, br.com.hbsis.LinhaCategoria... id: [{}]", linhaCategoriaExistente.getId());
            LOGGER.debug("Payload: {}", linhaCategoriaDTO);
            LOGGER.debug("Categoria existente: {}", linhaCategoriaExistente);

            linhaCategoriaExistente.setId(linhaCategoriaDTO.getIdLinha());
            linhaCategoriaExistente.setNome_linha(linhaCategoriaDTO.getNome_linha());
            linhaCategoriaExistente.setCodigo_linha(linhaCategoriaDTO.getCodigo_linha());

            CategoriaProdutoDTO categoriaProdutoDTO = categoriaService.findById(linhaCategoriaDTO.getIdLinha());
            Categoria categoria = converter(categoriaProdutoDTO);

            linhaCategoriaExistente.setCategoria(categoria);

            linhaCategoriaExistente = this.linhaCategoriaRepository.save(linhaCategoriaExistente);

            return linhaCategoriaDTO.of(linhaCategoriaExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existente", id));
    }

    public void delete(Long id){
        LOGGER.info("Executando delete para Linha de Categoria de ID: [{}]", id);
        this.linhaCategoriaRepository.deleteById(id);
    }

}