package br.com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.Test;

public class MovimentacaoTest {

    @Test
    public void test() {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
	EntityManager em = emf.createEntityManager();

	Conta conta = new Conta();
	conta.setAgencia(123456);
	conta.setNumero(789546);
	conta.setSaldo(300.00);
	conta.setTitular("Leonardo");

	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	em.persist(conta);
	transaction.commit();

	em.close();

	em = emf.createEntityManager();

	Movimentacao movimentacao = new Movimentacao();
	movimentacao.setData(LocalDateTime.now());
	movimentacao.setDescricao("Churrascaria");
	movimentacao.setValor(new BigDecimal(200.0));
	movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);

	movimentacao.setConta(conta);

	em.getTransaction().begin();
	em.persist(movimentacao);
	em.persist(movimentacao);

	em.getTransaction().commit();

	em.close();
    }

}
