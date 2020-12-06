package RelationTest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

import java.util.List;

public class Test {
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    public static void main(String[] args) {
       Session s = sessionFactory.openSession();
       s.beginTransaction();
       Library l = new Library();
       l.setName("First");
       s.save(l);
       Book b = new Book();
       b.setLocation(l);
       b.setTitle("Whatever");
       s.save(b);
       s.getTransaction().commit();
       s.close();
       s = sessionFactory.openSession();
       s.beginTransaction();
       b = s.get(Book.class,1);
       System.out.println("Book{id: "+b.getId()+",title: "+b.getTitle()+"}");
       s.getTransaction().commit();
    }
}
