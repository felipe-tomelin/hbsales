package br.com.hbsis.pedido;

import br.com.hbsis.categoriaProduto.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.funcionario.Funcionario;
import br.com.hbsis.funcionario.FuncionarioDTO;
import br.com.hbsis.funcionario.FuncionarioService;
import br.com.hbsis.invoice.InvoiceDTO;
import br.com.hbsis.invoiceItem.InvoiceItemDTO;
import br.com.hbsis.item.Item;
import br.com.hbsis.item.ItemDTO;
import br.com.hbsis.item.ItemRepository;
import br.com.hbsis.produto.Produto;
import br.com.hbsis.produto.ProdutoService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PedidoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;
    private final RestTemplate restTemplate;
    private final ProdutoService produtoService;
    private final FuncionarioService funcionarioService;
    private final ItemRepository itemRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, FornecedorService fornecedorService, CategoriaService categoriaService, RestTemplate restTemplate, ProdutoService produtoService, FuncionarioService funcionarioService, ItemRepository itemRepository) {
        this.pedidoRepository = pedidoRepository;
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
        this.restTemplate = restTemplate;
        this.produtoService = produtoService;
        this.funcionarioService = funcionarioService;
        this.itemRepository = itemRepository;
    }

    public void validate(PedidoDTO pedidoDTO) {
        LOGGER.info("Validando Pedido");

        if (StringUtils.isEmpty(pedidoDTO.getCodigo())){
            throw new IllegalArgumentException("Codigo de pedido não deve ser nulo");
        }

        if (StringUtils.isEmpty(pedidoDTO.getStatus())){
            throw new IllegalArgumentException("Status não deve ser nulo");
        }

        if (pedidoDTO.getDataCriacao() == null){
            throw new IllegalArgumentException("Data de criação não deve ser nula");
        }

        if (pedidoDTO.getIdFornecedor() == null){
            throw new IllegalArgumentException("Id do fornecedor não deve ser nulo");
        }

        if (pedidoDTO.getStatus().equalsIgnoreCase("Cancelado") || pedidoDTO.getStatus().equalsIgnoreCase("Retirado")){
            throw new IllegalArgumentException("Um pedido não pode ser cadastrado com status cancelado/retirado");
        }
    }

    public PedidoDTO save(PedidoDTO pedidoDTO){

        this.validate(pedidoDTO);

        LOGGER.info("Salvando Pedido");
        LOGGER.debug("Pedido: [{}]", pedidoDTO);

        Pedido pedido = new Pedido();

        /*Codigo*/
        int max = 9999;
        int min = 1;

        String cnpjProcessado = categoriaService.lastFourNumbersFromCNPJ(pedidoDTO.getCodigo());

        int numberRandom = (int)(Math.random() * ((max - min)+1))+min;
        String codigoProcessado = String.format("%04d", numberRandom);

        int letraRand = (char)((Math.random() * 90-65) + 1) + 65;

        String codPronto = letraRand + codigoProcessado + cnpjProcessado;

        pedido.setCodigo(codPronto.toUpperCase());

        /*Status*/
        pedido.setStatus(pedidoDTO.getStatus().toUpperCase());

        /*Data Criação*/
        pedido.setDataCriacao(pedidoDTO.getDataCriacao());

        /*Id do fornecedor*/
        FornecedorDTO idFornecedor = fornecedorService.findById(pedidoDTO.getIdFornecedor());
        Fornecedor fornecedor = categoriaService.converter(idFornecedor);
        pedido.setFornecedor(fornecedor);

        /*Lista de Itens*/
        pedido.setItemList(saveItems(pedidoDTO.getItemDTOList()));

        /*Validação na API*/
        FuncionarioDTO funcionarioDTO = this.funcionarioService.findById(pedidoDTO.getId());
        Funcionario funcionario = funcionarioService.converter(funcionarioDTO);

        InvoiceDTO invoiceDTO = new InvoiceDTO(
                fornecedor.getCnpj(),
                funcionario.getUuid(),
                this.createInvoiceItemDTOSet(pedidoDTO.getItemDTOList()),
                pedidoDTO.getTotalValue()
        );

        this.APIHBemployeeNotificar(invoiceDTO);

        pedido = this.pedidoRepository.save(pedido);

        return pedidoDTO.of(pedido);
    }

    /*public List<PedidoDTO> findAllByFornecedorId(Long id){
        LOGGER.info("Executando busca de fornecedor por id");
        List<Pedido> pedidoList = this.pedidoRepository.
    }*/

    public List<Item> saveItems(List<ItemDTO> itemDTOList) {
        LOGGER.info("Salvando itens");
        List<Item> itemList = new ArrayList<>();

        for(ItemDTO itemDTO : itemDTOList) {
            Item item = new Item(this.produtoService.findById(itemDTO.getIdProduto()), itemDTO.getQuantidadeProduto());
            itemList.add(item);
        }
        return this.itemRepository.saveAll(itemList);
    }


    public Pedido findById(Long id){
        Optional<Pedido> pedidoOptional = this.pedidoRepository.findById(id);

        if (pedidoOptional.isPresent()){
            return pedidoOptional.get();
        }
        throw new IllegalArgumentException(String.format("ID não existente", id));
    }

    /*public void savePedidoinPeriodo(Periodo periodo, Pedido pedido){
        if (pedido.getDataCriacao().isBefore(periodo.getDataFim()) && pedido.getDataCriacao().isAfter(periodo.getDataInicio())){
            periodo.get
        }
    }*/
    /*
    public List<PedidoDTO> findAll(){
        List<PedidoDTO> pedidoDTOList = new ArrayList<>();

        List<Pedido> pedidoList = this.pedidoRepository.findAll
    }
    */

    public void validarProdutoMesmoFornecedor(PedidoDTO pedidoDTO){
        List<Produto> produtoList = new ArrayList<>();

        pedidoDTO.getItemDTOList().forEach(itemDTO -> produtoList.add(this.produtoService.findById(itemDTO.getIdProduto())));

        produtoList.forEach(produto -> this.validateProdutosPorFornecedor(produto, pedidoDTO.getIdFornecedor()));
    }

    public void validateProdutosPorFornecedor(Produto produto, Long idFornecedor) {
        if(produto.getLinhaCategoria().getCategoria().getFornecedor().getId() != idFornecedor) {
            throw new IllegalArgumentException("Todos os produtos devem ser do fornecedor informado");
        }
    }

    public void retirarPedidoUpdate(Long id){
        Pedido pedido = this.findById(id);

        if (pedido.getStatus().equalsIgnoreCase("ativo")){
            pedido.setStatus("retirado");
        }
        this.pedidoRepository.save(pedido);
    }

    public Set<InvoiceItemDTO> createInvoiceItemDTOSet(List<ItemDTO> itemDTOList){
        Set<InvoiceItemDTO> invoiceItemDTOSet = new HashSet<>();

        for (ItemDTO itemDTO : itemDTOList){
            invoiceItemDTOSet.add(new InvoiceItemDTO(itemDTO.getQuantidadeProduto(), this.produtoService.findById(itemDTO.getIdProduto()).getNome_produto()));
        }
        return invoiceItemDTOSet;
    }

    public ResponseEntity<InvoiceDTO> APIHBemployeeNotificar(InvoiceDTO invoiceDTO){
        try{

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Authorization", "f59ffa10-1b67-11ea-978f-2e728ce88125");
            httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<InvoiceDTO> httpEntityFun = new HttpEntity<>(invoiceDTO, httpHeaders);

            return this.restTemplate.postForEntity("http://10.2.54.25:9999/api/invoice", httpEntityFun, InvoiceDTO.class);
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("Falha ao notificar a API HBEmployee sobre pedido");
        }
    }

}
