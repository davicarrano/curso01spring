package br.com.davicarrano.curso01.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.davicarrano.curso01.domains.Categoria;
import br.com.davicarrano.curso01.domains.Cidade;
import br.com.davicarrano.curso01.domains.Cliente;
import br.com.davicarrano.curso01.domains.Endereco;
import br.com.davicarrano.curso01.domains.Estado;
import br.com.davicarrano.curso01.domains.ItemPedido;
import br.com.davicarrano.curso01.domains.PagamentoComBoleto;
import br.com.davicarrano.curso01.domains.PagamentoComCartao;
import br.com.davicarrano.curso01.domains.Pedido;
import br.com.davicarrano.curso01.domains.Produto;
import br.com.davicarrano.curso01.domains.enums.EstadoPagamento;
import br.com.davicarrano.curso01.domains.enums.Perfil;
import br.com.davicarrano.curso01.domains.enums.TipoCliente;
import br.com.davicarrano.curso01.repositories.CategoriaRepository;
import br.com.davicarrano.curso01.repositories.CidadeRepository;
import br.com.davicarrano.curso01.repositories.ClienteRepository;
import br.com.davicarrano.curso01.repositories.EnderecoRepository;
import br.com.davicarrano.curso01.repositories.EstadoRepository;
import br.com.davicarrano.curso01.repositories.ItemPedidoRepository;
import br.com.davicarrano.curso01.repositories.PagamentoRepository;
import br.com.davicarrano.curso01.repositories.PedidoRepository;
import br.com.davicarrano.curso01.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired 
	private BCryptPasswordEncoder pswdEnc;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiate() throws ParseException {
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Quartos");
		Categoria cat4 = new Categoria(null, "Banheiro");
		Categoria cat5 = new Categoria(null, "Cama e mesa");
		Categoria cat6 = new Categoria(null, "Armarinho");
		Categoria cat7 = new Categoria(null, "Sala de estar");
		Categoria cat8 = new Categoria(null, "Area de convivencia");
		Categoria cat9 = new Categoria(null, "Garagens");
		Categoria cat10 = new Categoria(null, "Jardins");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		Produto p4 = new Produto(null, "Mesa de Escritorio", 180.00);
		Produto p5 = new Produto(null, "Toalha", 20.00);
		Produto p6 = new Produto(null, "Colcha", 80.00);
		Produto p7 = new Produto(null, "TV", 1980.00);
		Produto p8 = new Produto(null, "Rocadeira", 780.00);
		Produto p9 = new Produto(null, "Abajur", 240.00);
		Produto p10 = new Produto(null, "Pendente", 970.00);
		Produto p11 = new Produto(null, "Shampoo", 65.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2,p4));
		cat3.getProdutos().addAll(Arrays.asList(p5,p6));
		cat4.getProdutos().addAll(Arrays.asList(p1,p2,p3,p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9,p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2,cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1,cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Divinopolis", e1);
		Cidade c2 = new Cidade(null, "Sjdr", e1);
		Cidade c3 = new Cidade(null, "Campinas", e2);
		Cidade c4 = new Cidade(null, "Ubatuba", e2);

		Cliente cli1 = new Cliente(null, "davi", "davi@", "123", TipoCliente.PESSOA_FISICA,pswdEnc.encode("12345") );
		cli1.getTelefones().addAll(Arrays.asList("991202418", "32134951"));
		Endereco end1 = new Endereco(null, "Rua sao paulo", "2265", "casa", "Ipiranga", "35502455", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Maria conceicao resende", "541", "casa", "Solar da Serra", "36302656",
				cli1, c2);
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));

		Cliente cli2 = new Cliente(null, "andressa SA", "andressa@sa.com", "456", TipoCliente.PESSOA_JURIDICA, pswdEnc.encode("67890"));
		cli2.getTelefones().addAll(Arrays.asList("991264334", "33792347"));
		Endereco end3 = new Endereco(null, "Av Paulista", "10", "apt", "Centro", "11000000", cli2, c3);
		cli2.getEnderecos().addAll(Arrays.asList(end3));
		cli2.addPerfil(Perfil.ADMIN);
		
		SimpleDateFormat dataPedido1 = new SimpleDateFormat("dd/MM/yyy HH:mm");
		SimpleDateFormat dataVencimento1 = new SimpleDateFormat("dd/MM/yyy HH:mm");
		SimpleDateFormat datapagamento1 = new SimpleDateFormat("dd/MM/yyy HH:mm");
		Pedido ped1 = new Pedido(null,dataPedido1.parse("30/09/2017 10:32") , cli1, end2);
		PagamentoComBoleto pag1 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped1, dataVencimento1.parse("30/11/2017 00:00"), datapagamento1.parse("31/12/2017 00:00"));
		ped1.setPagamento(pag1);

		SimpleDateFormat dataPedido2 = new SimpleDateFormat("dd/MM/yyy HH:mm");
		SimpleDateFormat dataVencimento2 = new SimpleDateFormat("dd/MM/yyy HH:mm");
		SimpleDateFormat datapagamento2 = new SimpleDateFormat("dd/MM/yyy HH:mm");
		Pedido ped2 = new Pedido(null,dataPedido2.parse("01/05/2019 09:10") , cli2, end3);
		PagamentoComCartao pag2 = new PagamentoComCartao(null,EstadoPagamento.QUITADO,ped2,5);
		ped2.setPagamento(pag2);
		
		cli1.getPedidos().add(ped1);
		cli2.getPedidos().add(ped2);

		
		ItemPedido item1 = new ItemPedido(ped1, p1, 0.0, 10, 50.00);
		ItemPedido item2 = new ItemPedido(ped1, p3, 0.5, 16, 100.00);
		ItemPedido item3 = new ItemPedido(ped2, p2, 0.4, 20, 25.00);
		
		ped1.getItens().add(item1);
		ped1.getItens().add(item2);
		ped2.getItens().add(item3);
		
		p1.getItens().add(item1);
		p2.getItens().add(item3);
		p3.getItens().add(item2);
		
		

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6,cat7,cat8, cat9, cat10));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		estadoRepository.saveAll(Arrays.asList(e1, e2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3, c4));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(end1, end2, end3));

		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pag1,pag2));
		
		itemPedidoRepository.saveAll(Arrays.asList(item1,item2,item3));
		
	}

}
