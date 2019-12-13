package br.com.hbsis.produto;

import br.com.hbsis.linhaCategoria.LinhaCategoria;
import br.com.hbsis.linhaCategoria.LinhaCategoriaDTO;
import br.com.hbsis.linhaCategoria.LinhaCategoriaService;
import com.opencsv.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ProdutoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoService.class);

    private final ProdutoRepository produtoRepository;
    private final LinhaCategoriaService linhaCategoriaService;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, LinhaCategoriaService linhaCategoriaService) {
        this.produtoRepository = produtoRepository;
        this.linhaCategoriaService = linhaCategoriaService;
    }

    public void validate(ProdutoDTO produtoDTO){
        LOGGER.info("Validando Produto");

        String unidade = produtoDTO.getUnidade_medida();
        String last2peso = unidade.substring(unidade.length() - 2);
        String last1peso = unidade.substring(unidade.length() - 1);

        if (last2peso.equals("kg") || last2peso.equals("mg") || last1peso.equals("g")){
            LOGGER.info("Deu certo validação unidade peso");
        }else {
            throw new IllegalArgumentException("Unidade peso somente em kg, mg e g");
        }

        if(StringUtils.isEmpty(produtoDTO.getId().toString())) {
            throw new IllegalArgumentException("ID da Produto não deve ser nulo");
        }

        if(StringUtils.isEmpty(produtoDTO.getCodigoProduto())) {
            throw new IllegalArgumentException("Codigo de Produto não deve ser nulo");
        }

        if(StringUtils.isEmpty(produtoDTO.getNome_produto())) {
            throw new IllegalArgumentException("Nome de Produto não deve ser nulo");
        }

        if(StringUtils.isEmpty(produtoDTO.getPreco_produto().toString())) {
            throw new IllegalArgumentException("Preço de Produto não deve ser nulo");
        }

        if(StringUtils.isEmpty(produtoDTO.getId_linha().toString())) {
            throw new IllegalArgumentException("Id de Linha de Produto não deve ser nulo");
        }

        if(StringUtils.isEmpty(produtoDTO.getUnidade_por_caixa().toString())) {
            throw new IllegalArgumentException("Unidade por caixa de Produto não deve ser nulo");
        }

        if(StringUtils.isEmpty(produtoDTO.getPeso_unidade().toString())) {
            throw new IllegalArgumentException("Peso de unidade de Produto não deve ser nulo");
        }

        if(StringUtils.isEmpty(produtoDTO.getValidade().toString())) {
            throw new IllegalArgumentException("Validade de Produto não deve ser nulo");
        }
    }

    public String codigoValidarLinha (String codigo_produto){
        String codigoProcessado = StringUtils.leftPad(codigo_produto, 10, "0");

        return codigoProcessado;
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO){

        this.validate(produtoDTO);

        LOGGER.info("Salvando Produto");
        LOGGER.debug("Produto: {}", produtoDTO);

        Produto produto = new Produto();

        /*CODIGO*/
        String codigoProduto = produtoDTO.getCodigoProduto();
        String codigoUpper = codigoProduto.toUpperCase();
        String codigoPronto = codigoValidarLinha(codigoUpper);
        produto.setCodigoProduto(codigoPronto);

        /*NOME*/
        produto.setNome_produto(produtoDTO.getNome_produto());

        /*PREÇO*/
        produto.setPreco_produto(produtoDTO.getPreco_produto());

        /*UNIDADE POR CAIXA*/
        produto.setUnidade_por_caixa(produtoDTO.getUnidade_por_caixa());

        /*PESO*/
        produto.setPeso_unidade(produtoDTO.getPeso_unidade());

        /*UNIDADE DE MEDIDA*/
        produto.setUnidade_medida(produtoDTO.getUnidade_medida());

        /*VALIDADE*/
        produto.setValidade(produtoDTO.getValidade());

        LinhaCategoriaDTO linhaCategoriaDTO = linhaCategoriaService.findById(produtoDTO.getId_linha());
        LinhaCategoria linhaCategoria = converter(linhaCategoriaDTO);

        produto.setLinhaCategoria(linhaCategoria);

        produto = this.produtoRepository.save(produto);

        return produtoDTO.of(produto);
    }

    public LinhaCategoria converter(LinhaCategoriaDTO linhaCategoriaDTO){

        LinhaCategoria linhaCategoria = new LinhaCategoria();

        linhaCategoria.setId(linhaCategoriaDTO.getIdLinha());

        return linhaCategoria;
    }

    public ProdutoDTO findById(Long id){
        Optional<Produto> produtoOptional = this.produtoRepository.findById(id);

        if (produtoOptional.isPresent()){
            Produto produto = produtoOptional.get();
            ProdutoDTO produtoDTO = ProdutoDTO.of(produto);

            return produtoDTO;
        }
        throw new IllegalArgumentException(String.format("ID %s não existente", id));
    }

    public String formatarPreco(Double preco_produto){

        String format = "R$"+preco_produto;

        return format;
    }

    public String pesoConc(Double peso_unidade, String unidade_medida){

        Double peso = peso_unidade;
        String unidade = unidade_medida;

        String pesoConcatenado = peso + unidade;

        return pesoConcatenado;
    }

    public String validadeForm(LocalDate validade){

        LocalDate data = validade;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String formatado = data.format(formatter);

        return formatado;
    }

    public String formatarCnpj(String cnpj){

        String cnpjForm =  cnpj.substring(0, 2)+ "."+
                cnpj.substring(2, 5)+"."+
                cnpj.substring(5, 8)+ "/"+
                cnpj.substring(8, 12)+"-"+
                cnpj.substring(12, 14);

        return cnpjForm;
    }

    public boolean findByCodigoProduto(String codigoProduto) {

        boolean valida;

        Optional<Produto> produtoOptional = this.produtoRepository.findByCodigoProduto(codigoProduto);

        if(produtoOptional.isPresent()){
            valida = true;

            return valida;
        }else {
           valida = false;

            return valida;
        }
    }

    public void findAll (HttpServletResponse resposta) throws Exception {
        String arquivo = "produto.csv";
        resposta.setContentType("text/csv");
        resposta.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + arquivo + "\"");

        PrintWriter writer = resposta.getWriter();

        ICSVWriter icsvWriter = new CSVWriterBuilder(writer).withSeparator(';').withEscapeChar(CSVWriter.DEFAULT_ESCAPE_CHARACTER).withLineEnd(CSVWriter.DEFAULT_LINE_END).build();

        String[] tituloCSV = {"codigo", "nome", "preço", "unidade_por_caixa", "peso_por_unidade", "validade",
                "codigo_linha", "nome_linha", "codigo_categoria","nome_categoria", "cnpj_fornecedor", "razao_social"};

        icsvWriter.writeNext(tituloCSV);

        for (Produto rows : produtoRepository.findAll()){
            icsvWriter.writeNext(new String[]{
                    rows.getCodigoProduto(), rows.getNome_produto(), String.valueOf(formatarPreco(rows.getPreco_produto())),
                    rows.getUnidade_por_caixa().toString(), pesoConc(rows.getPeso_unidade(), rows.getUnidade_medida()), validadeForm(rows.getValidade()),
                    rows.getLinhaCategoria().getCodigoLinha(), rows.getLinhaCategoria().getNome_linha(),
                    rows.getLinhaCategoria().getCategoria().getCodigoCategoria(), rows.getLinhaCategoria().getCategoria().getNome_categoria(),
                    formatarCnpj(rows.getLinhaCategoria().getCategoria().getFornecedor().getCnpj()),
                    rows.getLinhaCategoria().getCategoria().getFornecedor().getRazaoSocial()});
        }
    }

    public List<Produto> reconhecer(MultipartFile importacao) throws Exception {

        InputStreamReader inserir = new InputStreamReader(importacao.getInputStream());

        CSVReader reader = new CSVReaderBuilder(inserir).withSkipLines(1).build();

        List<String[]> row = reader.readAll();
        List<Produto> result = new ArrayList<>();

        for (String[] linha : row) {
            try {
                String[] dados = linha[0].replaceAll("\"", "").split(";");

                Produto produto = new Produto();

                boolean valida  = findByCodigoProduto(dados[0]);

                if (valida == false) {

                    produto.setCodigoProduto(dados[0]);
                    produto.setNome_produto(dados[1]);
                    produto.setPreco_produto(Double.parseDouble(dados[2].replace("R", "").replace("$", "").replaceAll(",", ".")));
                    produto.setUnidade_por_caixa(Long.parseLong(dados[3]));
                    produto.setPeso_unidade(Double.parseDouble(dados[4].replace("m", "").replace("g", "").replace("k", "")
                            .replace("M", "").replace("K", "").replaceAll(",", " ")));
                    produto.setUnidade_medida(dados[4].replaceAll("\\d", "").replace(".", ""));

                    String data = dados[5];
                    int ano = Integer.parseInt(data.substring(6, 10));
                    int mes = Integer.parseInt(data.substring(3, 5));
                    int dia = Integer.parseInt(data.substring(0, 2));
                    LocalDate validadeDesform = LocalDate.of(ano, mes, dia);

                    produto.setValidade(validadeDesform);

                    LinhaCategoria linhaCategoria = new LinhaCategoria();
                    LinhaCategoriaDTO linhaCategoriaDTO = this.linhaCategoriaService.findByCodigoLinha(dados[6]);
                    linhaCategoria = converter(linhaCategoriaDTO);

                    produto.setLinhaCategoria(linhaCategoria);

                    result.add(produto);

                    LOGGER.info("Validando importação");

                    return produtoRepository.saveAll(result);

                } else if (valida == true) {
                    LOGGER.info("Produto existente no banco de dados...");
                }

            } catch (Exception e) {
                System.out.println("ERRO: " + e.getMessage());
            }
        }
            return produtoRepository.saveAll(result);
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO, Long id){
        Optional<Produto> produtoExistenteOptional = this.produtoRepository.findById(id);

        if(produtoExistenteOptional.isPresent()){
            Produto produtoExistente = produtoExistenteOptional.get();

            LOGGER.info("Atualizando, br.com.hbsis.Produto... ID: [{}]", produtoExistente.getId());
            LOGGER.debug("Payload: {}", produtoDTO);
            LOGGER.debug("Produto existente: {}", produtoExistente);

            produtoExistente.setId(produtoDTO.getId());
            produtoExistente.setCodigoProduto(produtoDTO.getCodigoProduto());
            produtoExistente.setNome_produto(produtoDTO.getNome_produto());
            produtoExistente.setPreco_produto(produtoDTO.getPreco_produto());
            produtoExistente.setUnidade_por_caixa(produtoDTO.getUnidade_por_caixa());
            produtoExistente.setPeso_unidade(produtoDTO.getPeso_unidade());
            produtoExistente.setValidade(produtoDTO.getValidade());

            LinhaCategoriaDTO linhaCategoriaDTO = linhaCategoriaService.findById(produtoDTO.getId_linha());
            LinhaCategoria linhaCategoria = converter(linhaCategoriaDTO);

            produtoExistente.setLinhaCategoria(linhaCategoria);

            produtoExistente = this.produtoRepository.save(produtoExistente);

            return produtoDTO.of(produtoExistente);
        }
        throw new IllegalArgumentException(String.format("ID %s não existente", id));
    }

    public void delete(Long id){
        LOGGER.info("Executando delete para Produto de ID: [{}]");
        this.produtoRepository.deleteById(id);
    }

    public void reconhecerTudo(MultipartFile importar) throws Exception{

    }
}
