package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityManagerTest {
    private Logger logger = null;

    private EntityManagerFactory emf;
    
    private EntityManager em;
 
    @Before
    public void initEmfAndEm() {
        logger = LoggerFactory.getLogger(HackernewsMiner.class);
 
        emf = Persistence.createEntityManagerFactory("persistence-unit");
        em = emf.createEntityManager();
    }
 
    @After
    public void cleanup() {
        em.close();
    }
 
    @Test
    public void emptyTest() {
    }
}

