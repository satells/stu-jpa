package br.com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TipoMovimentacao tipoMovimentacao;
    @Column(nullable = false, updatable = false)
    private LocalDateTime data;
    @Column(nullable = false)
    private String descricao;
    @Column(nullable = false)
    private BigDecimal valor;
    @ManyToOne
    private Conta conta;
    @ManyToMany
    private List<Categoria> categorias;

    public Conta getConta() {
	return conta;
    }

    public void setConta(Conta conta) {
	this.conta = conta;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public BigDecimal getValor() {
	return valor;
    }

    public void setValor(BigDecimal valor) {
	this.valor = valor;
    }

    public TipoMovimentacao getTipoMovimentacao() {
	return tipoMovimentacao;
    }

    public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
	this.tipoMovimentacao = tipoMovimentacao;
    }

    public LocalDateTime getData() {
	return data;
    }

    public void setData(LocalDateTime data) {
	this.data = data;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

}
