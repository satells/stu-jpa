package br.com.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
public class ContaTest {
    EntityManagerFactory emf;
    EntityManager em;
    static Long id;

    @Before
    public void create_entity_manager() {

	em = emf.createEntityManager();
    }

    @After
    public void close_entity_manager() {
	System.out.println("--------------------------------------------------------------------------------------------------------------");
	em.close();
    }

    @Parameters
    public static Collection<Object[]> data() {
	return Arrays.asList(new Object[][] { { Persistence.createEntityManagerFactory("contas") } });
    }

    public ContaTest(EntityManagerFactory emf) {
	this.emf = emf;
    }

    @Test
    public void testA_insere_conta() {
	Conta conta = new Conta();

	conta.setId(null);
	conta.setTitular("Leonardo");
	conta.setNumero(1234);
	conta.setAgencia(4321);
	conta.setSaldo(199.30);

	MatcherAssert.assertThat(conta.getId(), Matchers.equalTo(null));

	em.getTransaction().begin();
	em.persist(conta);

	em.getTransaction().commit();

	id = conta.getId();
	MatcherAssert.assertThat(id, Matchers.greaterThan(0L));
	em.close();

	em = emf.createEntityManager();
	conta = em.find(Conta.class, id);

	MatcherAssert.assertThat(conta.getId(), Matchers.equalTo(id));
	MatcherAssert.assertThat(conta.getTitular(), Matchers.equalTo("Leonardo"));
	MatcherAssert.assertThat(conta.getNumero(), Matchers.equalTo(1234));
	MatcherAssert.assertThat(conta.getAgencia(), Matchers.equalTo(4321));
	MatcherAssert.assertThat(conta.getSaldo(), Matchers.equalTo(199.30));
    }

    @Test
    public void testB_altera_conta() {

	Conta conta = em.find(Conta.class, id);

	em.getTransaction().begin();
	conta.setTitular("rubens");
	conta.setNumero(9999);
	conta.setAgencia(987);
	conta.setSaldo(999999.99);

	em.getTransaction().commit();

	em.close();

	em = emf.createEntityManager();
	conta = em.find(Conta.class, id);
	MatcherAssert.assertThat(conta.getSaldo(), Matchers.equalTo(999999.99));
	MatcherAssert.assertThat(conta.getNumero(), Matchers.equalTo(9999));
	MatcherAssert.assertThat(conta.getAgencia(), Matchers.equalTo(987));
	MatcherAssert.assertThat(conta.getTitular(), Matchers.equalTo("rubens"));
    }

    @Test
    public void testC_deleta_conta() {
	Conta conta = em.find(Conta.class, id);

	em.getTransaction().begin();
	em.remove(conta);
	em.getTransaction().commit();

	conta = em.find(Conta.class, id);

	MatcherAssert.assertThat(conta, Matchers.equalTo(null));
    }
}