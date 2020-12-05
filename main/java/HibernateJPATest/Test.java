package HibernateJPATest;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Test {
    private static final EntityManagerFactory MANAGER = Persistence.createEntityManagerFactory("TestUnit");
    public static void main(String[] args) {
        delete(4);
        for (var u :readAll()){
            System.out.println(u.toString());
        }
        create(4,"GHJ",24);
        for (var u :readAll()){
            System.out.println(u.toString());
        }
    }
    public static void create(int id, String name, int age) {
        EntityManager manager = MANAGER.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            User stu = new User();
            stu.setId(id);
            stu.setUsername(name);
            stu.setAge(age);
            manager.persist(stu);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }
    }
    public static List<User> readAll() {

        List<User> students = null;

        EntityManager manager = MANAGER.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            students = manager.createQuery("SELECT s FROM User s Where s.username = s.username", User.class).getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }
        return students;
    }
    public static void delete(int id) {
        EntityManager manager = MANAGER.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            User stu = manager.find(User.class, id);
            manager.remove(stu);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }
    }
}
