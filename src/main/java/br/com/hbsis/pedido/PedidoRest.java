package br.com.hbsis.pedido;

import br.com.hbsis.periodo.PeriodoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedido")
public class PedidoRest {

    public static final Logger LOGGER = LoggerFactory.getLogger(PedidoRest.class);

    private final PedidoService pedidoService;

    @Autowired
    public PedidoRest(PedidoService pedidoService) { this.pedidoService = pedidoService; }

    @PostMapping
    public PedidoDTO save(@RequestBody PedidoDTO pedidoDTO){
        LOGGER.info("Recebendo solicitação de persistência do Pedido", pedidoDTO.getId());
        LOGGER.debug("Payload: [{}]", pedidoDTO);

        return this.pedidoService.save(pedidoDTO);
    }

    @GetMapping("/{id}")
    public PedidoDTO findById(@PathVariable("id") Long id){
        LOGGER.info("Recebendo find by id, ID: {}", id);

        return this.pedidoService.findById(id);
    }
}
