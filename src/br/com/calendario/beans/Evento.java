package br.com.calendario.beans;

import com.towel.el.annotation.Resolvable;

public class Evento {

	@Resolvable(colName="Descrição")
	private String descricao;
	private String observacao;
	
	@Resolvable(colName="Dia")
	private Integer dia;
	@Resolvable(colName="Mes")
	private Integer mes;
	@Resolvable(colName="Ano")
	private Integer ano;
	
	@Resolvable(colName="Anual")
	private Boolean repeteAno;
	@Resolvable(colName="Mensal")
	private Boolean repeteMes;
	
	public Evento(){
		
	}
	
	public Evento(Integer dia, Integer mes, Integer ano,
			Boolean repeteMes, Boolean repeteAno, String descricao,
			String observacao) {
		super();
		this.dia = dia;
		this.mes = mes;
		this.ano = ano;
		this.repeteMes = repeteMes;
		this.repeteAno = repeteAno;
		this.descricao = descricao;
		this.observacao = observacao;
	}
	
	public Integer getDia() {
		return dia;
	}
	public void setDia(Integer dia) {
		this.dia = dia;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Boolean getRepeteAno() {
		return repeteAno;
	}
	public void setRepeteAno(Boolean repeteAno) {
		this.repeteAno = repeteAno;
	}
	public Boolean getRepeteMes() {
		return repeteMes;
	}
	public void setRepeteMes(Boolean repeteMes) {
		this.repeteMes = repeteMes;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}