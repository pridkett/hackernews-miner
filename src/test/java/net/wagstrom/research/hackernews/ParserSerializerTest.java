package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.wagstrom.research.hackernews.dbobjs.Item;
import net.wagstrom.research.hackernews.dbobjs.ItemUpdate;
import net.wagstrom.research.hackernews.dbobjs.User;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserSerializerTest extends TestBase {
    private static final Logger logger = LoggerFactory.getLogger(ParserSerializerTest.class);
    
    @Test
    public void test() {
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("index.html");
            Parser p = new Parser();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = r.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            p.run(builder.toString(), "https://news.ycombinator.com/");

            assertEquals(30, p.getItems().size());
            ParserSerializer ps = new ParserSerializer(p, em);
            ps.run();
            
            final List<User> ul = em.createQuery("Select u from User u", User.class).getResultList();
            assertEquals(30, ul.size());
            
            final List<Item> il = em.createQuery("Select i from Item i", Item.class).getResultList();
            assertEquals(30, il.size());
            
            final List<ItemUpdate> iul = em.createQuery("Select iu from ItemUpdate iu", ItemUpdate.class).getResultList();
            assertEquals(30, iul.size());
            
        } catch (IOException e) {
            logger.error("IO Exception caught: ", e);
        }
    }
}
