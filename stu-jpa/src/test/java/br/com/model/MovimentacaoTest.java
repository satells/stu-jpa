package br.com.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Parameterized.class)
public class MovimentacaoTest {
    EntityManagerFactory emf;
    EntityManager em;

    static Long id;

    @Parameters
    public static Collection<Object[]> data() {
	return Arrays.asList(new Object[][] { { Persistence.createEntityManagerFactory("contas") } });
    }

    public MovimentacaoTest(EntityManagerFactory emf) {
	this.emf = emf;
    }

    @Before
    public void create_entity_manager() {
	em = emf.createEntityManager();
    }

    @After
    public void close_entity_manager() {
	em.close();
    }

    @Test
    public void testA_insere_conta() {
	LocalDateTime data = LocalDateTime.now();

	Movimentacao movimentacao = new Movimentacao();
	movimentacao.setId(null);
	movimentacao.setData(data);
	movimentacao.setDescricao("Churrascaria");
	movimentacao.setValor(new BigDecimal(200.0));
	movimentacao.setTipoMovimentacao(TipoMovimentacao.ENTRADA);
	Conta conta = createConta();
	movimentacao.setConta(conta);

	em.getTransaction().begin();
	em.persist(movimentacao);
	em.getTransaction().commit();

	id = movimentacao.getId();
	movimentacao = em.find(Movimentacao.class, movimentacao.getId());
	MatcherAssert.assertThat(movimentacao.getId(), Matchers.greaterThan(0L));
	MatcherAssert.assertThat(movimentacao.getDescricao(), Matchers.equalTo("Churrascaria"));
	MatcherAssert.assertThat(movimentacao.getData(), Matchers.equalTo(data));
	MatcherAssert.assertThat(movimentacao.getTipoMovimentacao(), Matchers.equalTo(TipoMovimentacao.ENTRADA));
	MatcherAssert.assertThat(movimentacao.getConta(), Matchers.equalTo(conta));

    }

    @Test
    public void testB_altera_movimento() {
	Movimentacao movimentacao = em.find(Movimentacao.class, id);

	movimentacao.setDescricao("pão");
	movimentacao.setTipoMovimentacao(TipoMovimentacao.SAIDA);
	LocalDateTime data = LocalDateTime.now();
	movimentacao.setData(data);
	BigDecimal valor = new BigDecimal(1000);
	movimentacao.setValor(valor);
	em.getTransaction().begin();
	em.persist(movimentacao);
	em.getTransaction().commit();
	em.close();
	em = emf.createEntityManager();

	movimentacao = em.find(Movimentacao.class, id);
	MatcherAssert.assertThat(movimentacao.getDescricao(), Matchers.equalTo("pão"));
	MatcherAssert.assertThat(movimentacao.getData(), Matchers.equalTo(movimentacao.getData()));
	MatcherAssert.assertThat(movimentacao.getId(), Matchers.equalTo(id));
	MatcherAssert.assertThat(movimentacao.getValor(), Matchers.hasValue(valor));

    }

    private Conta createConta() {
	Conta conta = new Conta();
	conta.setAgencia(123456);
	conta.setNumero(789546);
	conta.setSaldo(300.00);
	conta.setTitular("Carlos");

	EntityManager em = emf.createEntityManager();

	EntityTransaction transaction = em.getTransaction();
	transaction.begin();
	em.persist(conta);
	transaction.commit();

	em.detach(conta);

	em.close();

	return conta;
    }

}
