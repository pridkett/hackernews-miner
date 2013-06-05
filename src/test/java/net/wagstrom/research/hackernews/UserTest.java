package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.User;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserTest extends TestBase {
    
    private Logger logger;
    
    public UserTest() {
        super();
        logger = LoggerFactory.getLogger(UserTest.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        User u1 = new User();
        u1.setName("testUser1");
        User u2 = new User();
        u2.setName("testUser2");
        
        em.getTransaction().begin();
        em.persist(u1);
        em.persist(u2);
        em.getTransaction().commit();
        
        final List<User> l = (List<User>)em.createQuery("SELECT u from User u").getResultList();
        assertEquals(2, l.size());

        assertNotNull(u1.getCreateDate());
        assertNotNull(u2.getCreateDate());
        assertEquals((Integer)1, u1.getId());
        assertEquals((Integer)2, u2.getId());
    }

}
