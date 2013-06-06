package net.wagstrom.research.hackernews;

import java.util.HashMap;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wagstrom.research.hackernews.dbobjs.Item;
import net.wagstrom.research.hackernews.dbobjs.ItemUpdate;
import net.wagstrom.research.hackernews.dbobjs.Update;
import net.wagstrom.research.hackernews.dbobjs.User;

public class ParserSerializer {
    private static final Logger logger = LoggerFactory.getLogger(ParserSerializer.class);
    private Parser p;
    private EntityManager em;
    
    public ParserSerializer(Parser p, EntityManager em) {
        this.p = p;
        this.em = em;
    }
    
    public void run() {
        Update u = new Update();
        HashMap<String, User> users = new HashMap<String, User>();
        HashSet<String> usernames = new HashSet<String>();
        HashMap<Integer, Item> items = new HashMap<Integer, Item>();
        HashSet<Integer> itemids = new HashSet<Integer>();
        
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        
        // process all of the usernames
        for (Item i : p.getItems()) {
            usernames.add(i.getUsername());
            itemids.add(i.getItemId());
        }
        
        em.getTransaction().begin();
        TypedQuery<User> q = em.createQuery("SELECT u from User u where u.name in :unamelist", User.class);
        q.setParameter("unamelist", usernames);
        for (User user : q.getResultList()) {
            users.put(user.getName(), user);
        }
        em.getTransaction().commit();
        
        // create the new users
        em.getTransaction().begin();
        usernames.removeAll(users.keySet());
        for (String s : usernames) {
            User user = new User();
            user.setName(s);
            em.persist(user);
            users.put(user.getName(), user);
        }
        em.getTransaction().commit();

        // check to see if we're good for all of the items
        // if they don't exist then save the items
        TypedQuery<Item> iq = em.createQuery("SELECT i from Item i where i.itemId in :itemidlist", Item.class);
        iq.setParameter("itemidlist", itemids);
        for (Item i : iq.getResultList()) {
            items.put(i.getItemId(), i);
        }
        itemids.removeAll(items.keySet());
        em.getTransaction().begin();
        for (Item i : p.getItems()) {
            if (itemids.contains(i.getItemId())) {
                i.setUpdate(u);
                i.setUser(users.get(i.getUsername()));
                em.persist(i);
                items.put(i.getItemId(), i);
            }
        }
        em.getTransaction().commit();
        
        // save the item updates
        em.getTransaction().begin();
        for (Item i : p.getItems()) {
            for (ItemUpdate iu : i.getUpdates()) {
                iu.setUpdateId(u.getId());
                iu.setItemId(i.getItemId());
                em.persist(iu);
            }
        }
        em.getTransaction().commit();
    }
}
