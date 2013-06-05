package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

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
    public void testCreate() {
        Update u1 = new Update();
        em.getTransaction().begin();
        em.persist(u1);
        em.getTransaction().commit();
        logger.info("ID: {}", u1.getId());
        logger.info("createDate: {}", u1.getCreateDate());
    }

}
