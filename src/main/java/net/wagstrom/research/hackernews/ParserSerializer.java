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
    
    public Update run(Update u) {
        
        HashMap<String, User> users = new HashMap<String, User>();
        HashSet<String> usernames = new HashSet<String>();
        HashMap<Integer, Item> items = new HashMap<Integer, Item>();
        HashSet<Integer> itemids = new HashSet<Integer>();
        
        
        // process all of the usernames
        logger.warn("Number of items: {}", p.getItems().size());
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
                if (!items.keySet().contains(i.getParentId()) &&
                        i.getParentId() != null) {
                    Item parent = new Item();
                    parent.setUpdate(u);
                    parent.setItemId(i.getParentId());
                    i.setParent(parent);
                    items.put(parent.getItemId(), parent);
                    ItemUpdate iu = new ItemUpdate();
                    i.addUpdate(iu);
                    em.persist(parent);
                }
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
        return u;
    }

    public Update run() {
        Update u = new Update();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();

        return run(u);
    }
}
