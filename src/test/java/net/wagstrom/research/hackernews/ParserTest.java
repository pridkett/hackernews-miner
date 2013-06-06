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
        } catch (IOException e) {
            logger.error("IO Exception caught: ", e);
            fail();
        }
    }
}
