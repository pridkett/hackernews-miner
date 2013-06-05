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

public class EntityManagerTest extends TestBase{
    private Logger logger = null;

    public EntityManagerTest() {
        super();
        logger = LoggerFactory.getLogger(EntityManagerTest.class);
    }

 
    @Test
    public void emptyTest() {
    }
}

