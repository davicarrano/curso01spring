package br.com.davicarrano.curso01.domains.enums;

public enum EstadoPagamento {

	PENDENTE(1, "Pendente"),
	QUITADO (2, "Quitado"),
	CANCELADO(3, "Cancelado");
	
	private Integer cod;
	private String descricao;

	private EstadoPagamento(Integer cod, String descricao) {
		this.setCod(cod);
		this.setDescricao(descricao);
		
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}
		
		for (EstadoPagamento x : EstadoPagamento.values()) {
			if (x.getCod().equals(cod)) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Id inválido:" + cod);
	}
	
}
