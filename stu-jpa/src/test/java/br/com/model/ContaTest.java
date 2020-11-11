package br.com.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ContaTest {
    EntityManagerFactory emf;
    EntityManager em;

    @After
    public void fecha_entity() {
	emf.close();
    }

    @Before
    public void inicia_entity() {
	emf = Persistence.createEntityManagerFactory("contas");
	em = emf.createEntityManager();
    }

    @Test
    public void altera_conta() {
	Conta conta = em.find(Conta.class, 6L);
	MatcherAssert.assertThat(conta.getId(), Matchers.equalTo(6L));
	em.getTransaction().begin();
	conta.setSaldo(2999.99);
	em.getTransaction().commit();

    }

    @Test
    public void insere_conta() {
	Conta conta = new Conta();

	conta.setTitular("Leonardo");
	conta.setNumero(1234);
	conta.setAgencia(4321);
	conta.setSaldo(199.30);

	em.getTransaction().begin();
	em.persist(conta);
	em.getTransaction().commit();

    }

}
