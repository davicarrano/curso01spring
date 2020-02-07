package br.com.davicarrano.curso01.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Cliente;
import br.com.davicarrano.curso01.domains.ItemPedido;
import br.com.davicarrano.curso01.domains.PagamentoComBoleto;
import br.com.davicarrano.curso01.domains.Pedido;
import br.com.davicarrano.curso01.domains.Produto;
import br.com.davicarrano.curso01.domains.enums.EstadoPagamento;
import br.com.davicarrano.curso01.repositories.ItemPedidoRepository;
import br.com.davicarrano.curso01.repositories.PagamentoRepository;
import br.com.davicarrano.curso01.repositories.PedidoRepository;
import br.com.davicarrano.curso01.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ClienteService clienteService;
	
	public Pedido buscarPedidoUnico(Integer id) {
		Optional<Pedido> pedidoOptional = pedidoRepository.findById(id);
		return pedidoOptional.orElseThrow(()-> new ObjectNotFoundException("Pedido não encontrado com o id: "+id));
	}


	public List<Pedido> buscarTodosPedidos() {
		List<Pedido> lista = pedidoRepository.findAll();
		if (lista.size() == 0) {
			throw new ObjectNotFoundException("Lista de Pedidos não encontrada!");
		}
		
		return pedidoRepository.findAll(); 
				
	}


	@Transactional
	public Pedido inserir(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if(pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = ((PagamentoComBoleto)pedido.getPagamento());
			pagto.setDataVencimento(new Date());
		}
		
		Cliente cli = clienteService.buscar(pedido.getCliente().getId());
		pedido.setCliente(cli);
		
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido item : pedido.getItens()) {
			Produto p = produtoService.buscar(item.getProduto().getId());
			
			item.setDesconto(0.0);
			item.setPreco( p.getPreco());
			item.setPedido(pedido);
			item.setProduto(p);
			
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		emailService.sendEmailHtmlConfirmacaoPedido(pedido);
		return pedido;
	}

}
