package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.Karma;
import net.wagstrom.research.hackernews.dbobjs.Update;
import net.wagstrom.research.hackernews.dbobjs.User;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KarmaTest extends TestBase {
    
    private Logger logger;
    public KarmaTest() {
        super();
        logger = LoggerFactory.getLogger(KarmaTest.class);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        User user = new User();
        user.setName("testUser");
        Update update = new Update();
        em.getTransaction().begin();
        em.persist(update);
        em.persist(user);
        em.getTransaction().commit();
        
        Karma k = new Karma();
        k.setUserId(user.getId());
        k.setUpdateId(update.getId());
        k.setKarma(1);
        
        em.getTransaction().begin();
        em.persist(k);
        em.getTransaction().commit();
        
        assertEquals((Integer)1, k.getId());
        
        final List<Karma> l = em.createQuery("SELECT k from Karma k")
                .getResultList();
        assertEquals(1, l.size());
    }

}
