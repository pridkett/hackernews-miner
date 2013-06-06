package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.Item;
import net.wagstrom.research.hackernews.dbobjs.Update;
import net.wagstrom.research.hackernews.dbobjs.User;

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
        User u = new User();
        u.setName("testUser");
        
        em.getTransaction().begin();
        em.persist(update);
        em.persist(u);
        em.getTransaction().commit();
        
        Item item = new Item();
        item.setUpdate(update);
        item.setUser(u);
        
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        
        assertEquals((Integer)1, item.getId());
        
        List<Item> l = em.createQuery("Select i from Item i").getResultList();
        assertEquals(1, l.size());
    }
    
    @Test
    public void testChildren() {
        Update update = new Update();
        User u = new User();
        u.setName("testUser");
        
        em.getTransaction().begin();
        em.persist(update);
        em.persist(u);
        em.getTransaction().commit();
        
        Item parent = new Item();
        parent.setUpdate(update);
        parent.setUser(u);
        Item child1 = new Item();
        Item child2 = new Item();
        child1.setUpdate(update);
        child2.setUpdate(update);
        child1.setUser(u);
        child2.setUser(u);
        child1.setParent(parent);
        child2.setParent(parent);
        
        
        em.getTransaction().begin();
        em.persist(parent);
        em.persist(child1);
        em.persist(child2);
        em.getTransaction().commit();
        
        Collection<Item> children = 
                em.createQuery("Select i from Item i where i.parent=:parent",
                        Item.class)
                        .setParameter("parent", parent).getResultList();
        assertEquals(2, children.size());
    }
}
