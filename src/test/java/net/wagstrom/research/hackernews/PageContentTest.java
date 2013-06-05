package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.PageContent;
import net.wagstrom.research.hackernews.dbobjs.Update;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageContentTest extends TestBase {

    private Logger logger;
    public PageContentTest() {
        super();
        logger = LoggerFactory.getLogger(PageContentTest.class);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        Update u = new Update();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        
        PageContent pc = new PageContent();
        pc.setUpdateId(u.getId());
        pc.setUrl("http://www.sample.com/");
        pc.setText("<html><body><h1>test!</h1></body></html>");
        
        em.getTransaction().begin();
        em.persist(pc);
        em.getTransaction().commit();

        assertEquals((Integer)1, pc.getId());
        final List<PageContent> l = em.createQuery("SELECT pc from PageContent pc")
                .getResultList();
        assertEquals(1, l.size());
    }

}
