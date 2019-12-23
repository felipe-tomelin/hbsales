package br.com.hbsis.pedido;

import br.com.hbsis.categoriaProduto.CategoriaService;
import br.com.hbsis.fornecedor.Fornecedor;
import br.com.hbsis.fornecedor.FornecedorDTO;
import br.com.hbsis.fornecedor.FornecedorService;
import br.com.hbsis.periodo.Periodo;
import br.com.hbsis.periodo.PeriodoDTO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PedidoService.class);

    private final PedidoRepository pedidoRepository;
    private final FornecedorService fornecedorService;
    private final CategoriaService categoriaService;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, FornecedorService fornecedorService, CategoriaService categoriaService) {
        this.pedidoRepository = pedidoRepository;
        this.fornecedorService = fornecedorService;
        this.categoriaService = categoriaService;
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
    }

    public PedidoDTO save(PedidoDTO pedidoDTO){

        this.validate(pedidoDTO);

        LOGGER.info("Salvando Pedido");
        LOGGER.debug("Pedido: [{}]", pedidoDTO);

        Pedido pedido = new Pedido();

        /*Codigo*/
        String codigoZero = categoriaService.zeroLeft(pedidoDTO.getCodigo());
        String cnpjProcessado = categoriaService.last4cnpj(pedidoDTO.getCodigo());
        String codPronto = "PED" + cnpjProcessado + codigoZero;
        pedido.setCodigo(codPronto);

        /*Status*/
        pedido.setStatus(pedidoDTO.getStatus());

        /*Data Criação*/
        pedido.setDataCriacao(pedidoDTO.getDataCriacao());

        /*Id do fornecedor*/
        FornecedorDTO idFornecedor = fornecedorService.findById(pedidoDTO.getIdFornecedor());
        Fornecedor idForn = categoriaService.converter(idFornecedor);
        pedido.setFornecedor(idForn);

        pedido = this.pedidoRepository.save(pedido);

        return pedidoDTO.of(pedido);
    }

    public PedidoDTO findById(Long id){
        Optional<Pedido> pedidoOptional = this.pedidoRepository.findById(id);

        if (pedidoOptional.isPresent()){
            return PedidoDTO.of(pedidoOptional.get());
        }
        throw new IllegalArgumentException(String.format("ID não existente", id));
    }

}
