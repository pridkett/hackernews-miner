package net.wagstrom.research.hackernews;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.wagstrom.research.hackernews.dbobjs.Item;
import net.wagstrom.research.hackernews.dbobjs.ItemUpdate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Parser {
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);
    private ArrayList<Item> items;
    private String nextURL = null;
    
    public Parser() {
        items = new ArrayList<Item>();
    }
    
    private enum ParseMode {
        FIRST, SECOND, SPACER, FINAL;
    }
    
    public void run(String html, String baseuri) {
        Item i = null;
        
        Document doc = Jsoup.parse(html, baseuri);
        Element main = doc.select("table tr").get(3).select("td table tbody").first();
        
        // This regular expression creates 8 groups:
        // 1 - number of points
        // 2 - submitter name
        // 3 - number of time since submission
        // 4 - time unit since submission
        // 5 - either text for # of comments (eg "28 comments") or "discuss"
        // 6 - either text for # of comments (eg "28 comments") or null
        // 7 - number of comments or null if none
        // 8 - null or "discuss" if no comments
        String regex = "([0-9]+) points by ([^ ]+) ([0-9]+) (minutes?|hours?) ago \\| ((([0-9]+) comments?)|(discuss))";
        // String regex = "(\\d+) points";
        Pattern p = Pattern.compile(regex);
        
        ParseMode pm = ParseMode.FIRST;
        for (Element e : main.select("tr")) {
            logger.trace("parsing: {}", e.html());

            if (pm.equals(ParseMode.FIRST)) {
                Element link = e.select("td[class=title] a").first();
                // this often means we're at the final spacer
                if (link == null) {
                    pm = ParseMode.FINAL;
                    continue;
                }
                i = new Item();
                i.setUrl(link.attr("href"));
                i.setText(link.text());
                i.setIsComment(false);

                logger.trace("href: {}", link.attr("href"));
                logger.trace("text: {}", link.text());
                pm = ParseMode.SECOND;
            } else if (pm.equals(ParseMode.SECOND)) {
                if (e.text().trim().equals("")) {
                    logger.warn("null text?!? {}", e.html());
                } else {
                    logger.trace(e.text());
                    logger.trace("Matches: {}", e.text().matches(regex));
                    
                    Matcher m = p.matcher(e.text());
                    logger.trace("Group Count: {}", m.groupCount());
                    if (m.find()) {
                        for (int k = 1; k < m.groupCount()+1; k ++) {
                            logger.trace("{} = {}", k, m.group(k));
                        }
                        int points = Integer.parseInt(m.group(1));
                        String submitter = m.group(2);
                        int since = Integer.parseInt(m.group(3));
                        String sinceTime = m.group(4);
                        int numComments = 0;
                        if (m.group(8) == null) {
                            numComments = Integer.parseInt(m.group(7));
                        }
                        int hn_id = Integer.parseInt(e.select("span").first().attr("id").split("_")[1]);
                        
                        i.setHnCreateDate(since, sinceTime);
                        i.setItemId(hn_id);
                        i.setUser(submitter);
                        
                        ItemUpdate iu = new ItemUpdate();
                        iu.setNumComments(numComments);
                        iu.setPoints(points);
                        
                        i.addUpdate(iu);
                    } else {
                        logger.error("DOES NOT MATCH REGEX: {}", e.text());
                    }
                    pm = ParseMode.SPACER;
                }
            } else if (pm.equals(ParseMode.SPACER)) {
                if (i != null) {
                    items.add(i);
                    i = null;
                }
                if (!(e.attr("style").indexOf("height:5px") >= 0)) {
                    logger.warn("style attribute appear to have changed for spacers");
                }
                pm = ParseMode.FIRST;
            } else if (pm.equals(ParseMode.FINAL)){
                Element nextLink = e.select("td[class=title] a").first();
                String nextHref = nextLink.attr("href");
                logger.trace("Next href: {}", nextHref);
                this.nextURL = baseuri + "/" + nextURL;
            }
        }
    }

    public String getNextURL() {
        return nextURL;
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
