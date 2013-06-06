package net.wagstrom.research.hackernews;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParserTest {
    private static final Logger logger = LoggerFactory.getLogger(ParserTest.class);
    
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
            assertEquals("https://news.ycombinator.com/news2",
                    p.getNextURL());
        } catch (IOException e) {
            logger.error("IO Exception caught: ", e);
            fail();
        }
    }
    
    @Test
    public void testItemParse() {
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("item.html");
            Parser p = new Parser();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = r.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            p.runItem(builder.toString(), "https://news.ycombinator.com/");

            assertEquals(101, p.getItems().size());
            assertEquals("https://news.ycombinator.com/x?fnid=3U5kcdzfVZedom1z7Jm78R",
                    p.getNextURL());
        } catch (IOException e) {
            logger.error("IO Exception caught: ", e);
            fail();
        }
    }
    
    @Test
    public void testItemPage2Parse() {
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("item.page2.html");
            Parser p = new Parser();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = r.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            p.runItem(builder.toString(), "https://news.ycombinator.com/");

            assertEquals(99, p.getItems().size());
            assertEquals("https://news.ycombinator.com/x?fnid=cqiYhM0gF9A8w9oXoiVXvu",
                    p.getNextURL());
        } catch (IOException e) {
            logger.error("IO Exception caught: ", e);
            fail();
        }
    }
    
    @Test
    public void testItemPage3Parse() {
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("item.page3.html");
            Parser p = new Parser();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = r.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            p.runItem(builder.toString(), "https://news.ycombinator.com/");

            assertEquals(9, p.getItems().size());
            assertNull(p.getNextURL());
        } catch (IOException e) {
            logger.error("IO Exception caught: ", e);
            fail();
        }        
    }
    
    @Test
    public void testItemOnePageParse() {
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("item.onepage.html");
            Parser p = new Parser();
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = r.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            p.runItem(builder.toString(), "https://news.ycombinator.com/");

            assertEquals(24, p.getItems().size());
            assertNull(p.getNextURL());
        } catch (IOException e) {
            logger.error("IO Exception caught: ", e);
            fail();
        }
    }
}
