package net.wagstrom.research.hackernews;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestBase {
    private Logger logger;
    protected EntityManagerFactory emf;
    
    protected EntityManager em;
 
    @Before
    public void initEmfAndEm() {
        logger = LoggerFactory.getLogger(TestBase.class);
 
        emf = Persistence.createEntityManagerFactory("persistence-unit");
        em = emf.createEntityManager();
    }
 
    @After
    public void cleanup() {
        em.close();
    }
}
