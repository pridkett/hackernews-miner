package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.Story;
import net.wagstrom.research.hackernews.dbobjs.Update;
import net.wagstrom.research.hackernews.dbobjs.User;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoryTest extends TestBase {
    
    private Logger logger;
    
    public StoryTest() {
        super();
        logger = LoggerFactory.getLogger(StoryTest.class);
    }

    @Test
    public void test() {
        Story s1 = new Story();
        Story s2 = new Story();
        
        User user1 = new User();
        user1.setName("testUser");
        Update update1 = new Update();
        em.getTransaction().begin();
        em.persist(user1);
        em.persist(update1);
        em.getTransaction().commit();
        
        s1.setUserId(user1.getId());
        s2.setUserId(user1.getId());
        s1.setUpdateId(update1.getId());
        s2.setUpdateId(update1.getId());
        em.getTransaction().begin();
        em.persist(s1);
        em.persist(s2);
        em.getTransaction().commit();
        
        assertEquals((Integer)1, s1.getId());
        assertEquals((Integer)2, s2.getId());
        final List<Story> l = em.createQuery("SELECT s from Story s")
                .getResultList();
        assertEquals(2, l.size());
    }

}
