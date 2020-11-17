package br.com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 60)
    private String titular;
    @Column(nullable = false, updatable = false)
    private Integer agencia;
    @Column(nullable = false, updatable = false)
    private Integer numero;
    @Column(nullable = false)
    private Double saldo;

    public Conta() {

    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getTitular() {
	return titular;
    }

    public void setTitular(String titular) {
	this.titular = titular;
    }

    public Integer getAgencia() {
	return agencia;
    }

    public void setAgencia(Integer agencia) {
	this.agencia = agencia;
    }

    public Integer getNumero() {
	return numero;
    }

    public void setNumero(Integer numero) {
	this.numero = numero;
    }

    public Double getSaldo() {
	return saldo;
    }

    public void setSaldo(Double saldo) {
	this.saldo = saldo;
    }

}
