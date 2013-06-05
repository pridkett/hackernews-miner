package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.Update;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTest extends TestBase {

    private Logger logger;
   
    public UpdateTest() {
        super();
        logger = LoggerFactory.getLogger(UpdateTest.class);
    }
    
    @Test
    public void testInsert() {
        Update u1 = new Update();
        em.getTransaction().begin();
        em.persist(u1);
        em.getTransaction().commit();
        assertEquals((Integer)1, u1.getId());
        assertNotNull(u1.getCreateDate());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void testInsertAndRetrieve() {
        Update u1 = new Update();
        em.getTransaction().begin();
        em.persist(u1);
        em.getTransaction().commit();
        
        final List<Update> l = em.createQuery("SELECT u from Update u")
                .getResultList();
        
        assertEquals(1, l.size());
    }

}
