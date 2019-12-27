package br.com.hbsis.categoriaProduto;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorRepository;
import br.com.hbsis.fornecedor.FornecedorService;
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
public class CategoriaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);

    private final CategoriaRepository categoriaRepository;
    private final FornecedorService fornecedorService;
    private final FornecedorRepository fornecedorRepository;


    public CategoriaService(CategoriaRepository categoriaRepository, FornecedorService fornecedorService, FornecedorRepository fornecedorRepository) {
        this.categoriaRepository = categoriaRepository;
        this.fornecedorService = fornecedorService;
        this.fornecedorRepository = fornecedorRepository;

    }

    public String completeZeroWithLeftPad(String codigo){
        String codigoProcessado = StringUtils.leftPad(codigo, 3, "0");
        return codigoProcessado;
    }

    public String lastFourNumbersFromCNPJ(String cnpj){
        String last4 = cnpj.substring(cnpj.length() - 4);

        return last4;
    }

    public CategoriaProdutoDTO save(CategoriaProdutoDTO categoriaProdutoDTO){

        this.validate(categoriaProdutoDTO);

        LOGGER.info("Salvando Categoria");
        LOGGER.debug("Categoria: {}", categoriaProdutoDTO);

        Categoria categoria = new Categoria();

        categoria.setNome_categoria(categoriaProdutoDTO.getNome_categoria());

        FornecedorDTO fornecedorDTO = fornecedorService.findById(categoriaProdutoDTO.getId_fornecedor());
        Fornecedor fornecedor = converter(fornecedorDTO);
        categoria.setFornecedor(fornecedor);

        categoria.setCodigoCategoria(codigoCategoriaConcatenado());


        categoria = this.categoriaRepository.save(categoria);

        return categoriaProdutoDTO.of(categoria);
    }

    public Fornecedor converter(FornecedorDTO fornecedorDTO){

        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setId(fornecedorDTO.getId());
        return fornecedor;
    }

    private void validate(CategoriaProdutoDTO categoriaProdutoDTO){
        LOGGER.info("Validando Categoria");

        if (categoriaProdutoDTO.getId_fornecedor() == null) {
            throw new IllegalArgumentException("ID do fornecedor não deve ser nulo");
        }

        if (StringUtils.isEmpty(categoriaProdutoDTO.getCodigoCategoria())) {
            throw new IllegalArgumentException("Código não deve ser nulo/vazio");
        }

        if (StringUtils.isEmpty(categoriaProdutoDTO.getNome_categoria())) {
            throw new IllegalArgumentException("Nome não deve ser nulo/vazio");
        }
    }

    public CategoriaProdutoDTO findById(Long id) {
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findById(id);

        if(categoriaOptional.isPresent()){
            return CategoriaProdutoDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException((String.format("ID não existente", id)));
    }

    public CategoriaProdutoDTO findByCodigoCategoria (String codigo){
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findByCodigoCategoria(codigo);

        if(categoriaOptional.isPresent()){
            return CategoriaProdutoDTO.of(categoriaOptional.get());
        }

        throw new IllegalArgumentException((String.format("Codigo não existente", codigo)));
    }

    public boolean findExistsByCodigo(String codigo){
        Optional<Categoria> categoriaOptional = this.categoriaRepository.findByCodigoCategoria(codigo);

        boolean valida;

        if(categoriaOptional.isPresent()){
            valida = true;

            return valida;
        }else{
            valida = false;

            return valida;
        }
    }

    public String codigoCategoriaConcatenado() {

        FornecedorDTO fornecedorDTO = new FornecedorDTO();
        CategoriaProdutoDTO categoriaProdutoDTO = new CategoriaProdutoDTO();

        String cnpj = fornecedorDTO.getCnpj();

        String cnpjProcessado = lastFourNumbersFromCNPJ(cnpj);

        String codigo = categoriaProdutoDTO.getCodigoCategoria();

        String zeroLeft = completeZeroWithLeftPad(codigo);

        String codigoFeito = "CAT" + cnpjProcessado + zeroLeft;

        return codigoFeito;
    }

    public  CategoriaProdutoDTO update(CategoriaProdutoDTO categoriaProdutoDTO, Long id){
        Optional<Categoria> categoriaExistenteOptional = this.categoriaRepository.findById(id);

        this.validate(categoriaProdutoDTO);

        if (categoriaExistenteOptional.isPresent()) {
            Categoria categoriaExistente = categoriaExistenteOptional.get();

            LOGGER.info("Atualizando, br.com.hbsis.categoria... id: [{}]", categoriaExistente.getId());
            LOGGER.debug("Payload: {}", categoriaProdutoDTO);
            LOGGER.debug("Categoria existente: {}", categoriaExistente);

            categoriaExistente.setNome_categoria(categoriaProdutoDTO.getNome_categoria());
            categoriaExistente.setCodigoCategoria(categoriaProdutoDTO.getCodigoCategoria());

            FornecedorDTO fornecedorDTO = fornecedorService.findById(categoriaProdutoDTO.getId_fornecedor());
            Fornecedor fornecedor = converter(fornecedorDTO);

            categoriaExistente.setFornecedor(fornecedor);

            categoriaExistente.setCodigoCategoria(codigoCategoriaConcatenado());

            categoriaExistente = this.categoriaRepository.save(categoriaExistente);

            return categoriaProdutoDTO.of(categoriaExistente);
        }

        throw new IllegalArgumentException(String.format("ID não existente", id));

    }

    public void delete(Long id){
            LOGGER.info("Executando delete para Categoria de ID: [{}]", id);

            this.categoriaRepository.deleteById(id);
        }

    public void exportCSVForCategoriaService(HttpServletResponse resposta) throws Exception {
        String arquivo = "categoria.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter writer = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(writer).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();
        String[] tituloCSV = {"codigo", "nome_categoria", "razão_social_fornecedor" , "cnpj_fornecedor"};
        icsvWriter.writeNext(tituloCSV);

        for (Categoria rows : categoriaRepository.findAll()) {
            icsvWriter.writeNext(new String[]{rows.getCodigoCategoria(), rows.getNome_categoria(), rows.getFornecedor().getRazaoSocial(), formatarCnpj(rows.getFornecedor().getCnpj())});
        }
    }

    public String formatarCnpj(String cnpj){

        String cnpjForm =  cnpj.substring(0, 2)+ "."+
                cnpj.substring(2, 5)+"."+
                cnpj.substring(5, 8)+ "/"+
                cnpj.substring(8, 12)+"-"+
                cnpj.substring(12, 14);

        return cnpjForm;
    }

    public List<Categoria> importLeitorCSVForCategoriaService(MultipartFile file) throws Exception {

        InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());

        CSVReader leitor = new CSVReaderBuilder(inputStreamReader).withSkipLines(1).build();

        List<String[]> row = leitor.readAll();
        List<Categoria> categoriaProdutoList = new ArrayList<>();

        for(String[] linha : row) {

            String[] dados = linha[0].replaceAll("\"", "").split(";");

            Categoria categoria = new Categoria();

            boolean valida = findExistsByCodigo(dados[0]);

            if (valida == false) {

                categoria.setCodigoCategoria(dados[0]);
                categoria.setNome_categoria(dados[1]);

                Fornecedor fornecedor = new Fornecedor();

                String cnpjDesformatado = desformatarCnpj(dados[3]);

                FornecedorDTO fornecedorDTO = fornecedorService.findByCnpj(cnpjDesformatado);
                fornecedor = converter(fornecedorDTO);

                categoria.setFornecedor(fornecedor);

                categoriaProdutoList.add(categoria);
            }else if (valida == true){
                LOGGER.info("Categoria ja existente no banco de dados...");
            }
        }
        LOGGER.info("Finalizando importação...");

        return categoriaRepository.saveAll(categoriaProdutoList);
    }

    public String desformatarCnpj(String cnpj) {

        String cnpjDesformatado =   cnpj.charAt(0)+""+cnpj.charAt(1)+
                cnpj.charAt(3)+cnpj.charAt(4)+cnpj.charAt(5)+
                cnpj.charAt(7)+cnpj.charAt(8)+cnpj.charAt(9)+
                cnpj.charAt(11)+cnpj.charAt(12)+cnpj.charAt(13)+cnpj.charAt(14)+
                cnpj.charAt(16)+cnpj.charAt(17);

        return cnpjDesformatado;
    }

}
