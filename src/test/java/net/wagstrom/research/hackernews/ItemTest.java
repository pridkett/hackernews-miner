package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.Item;
import net.wagstrom.research.hackernews.dbobjs.Update;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemTest extends TestBase {

    private Logger logger;
    
    public ItemTest() {
        super();
        logger = LoggerFactory.getLogger(ItemTest.class);
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void test() {
        Update update = new Update();
        
        em.getTransaction().begin();
        em.persist(update);
        em.getTransaction().commit();
        
        Item item = new Item();
        item.setUpdateId(update.getId());
        
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        
        assertEquals((Integer)1, item.getId());
        
        List<Item> l = em.createQuery("Select i from Item i").getResultList();
        assertEquals(1, l.size());
    }

}
