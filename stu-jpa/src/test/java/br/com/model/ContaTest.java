package br.com.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
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

    @Parameters
    public static Collection<Object[]> data() {
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");

	return Arrays.asList(new Object[][] { { emf } });
    }

    public ContaTest(EntityManagerFactory emf) {
	this.emf = emf;
    }

    @Test
    public void testB_altera_conta() {
	em = emf.createEntityManager();
	Conta conta = em.find(Conta.class, id);

	MatcherAssert.assertThat(conta.getId(), Matchers.equalTo(id));
	MatcherAssert.assertThat(conta.getTitular(), Matchers.equalTo("Leonardo"));
	MatcherAssert.assertThat(conta.getNumero(), Matchers.equalTo(1234));
	MatcherAssert.assertThat(conta.getAgencia(), Matchers.equalTo(4321));
	MatcherAssert.assertThat(conta.getSaldo(), Matchers.equalTo(199.30));

	em.getTransaction().begin();
	conta.setSaldo(999999.99);
	em.getTransaction().commit();
    }

    @Test
    public void testA_insere_conta() {
	em = emf.createEntityManager();
	Conta conta = new Conta();

	conta.setTitular("Leonardo");
	conta.setNumero(1234);
	conta.setAgencia(4321);
	conta.setSaldo(199.30);

	em.getTransaction().begin();
	em.persist(conta);
	em.getTransaction().commit();

	id = conta.getId();

	MatcherAssert.assertThat(id, Matchers.greaterThan(0L));
	em.close();
    }

    @Test
    public void testC_deleta_conta() {
	em = emf.createEntityManager();

	Conta conta = em.find(Conta.class, id);

	em.getTransaction().begin();
	em.remove(conta);
	em.getTransaction().commit();

	conta = em.find(Conta.class, id);

	MatcherAssert.assertThat(conta, Matchers.equalTo(null));
	em.close();

    }

}
