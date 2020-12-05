package HibernateJDBCTest;

import org.hibernate.Session;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        Test application = new Test();
        application.deleteUser(3);
        for(User u:application.getAll()){
            System.out.println(u.toString());
        }
        User student = new User();
        student.setAge(23);
        student.setId(3);
        student.setUsername("MNB");
        application.Add(student);
        for(User u:application.getAll()){
            System.out.println(u.toString());
        }
        application.updateAge(3,42);
        for(User u:application.getAll()){
            System.out.println(u.toString());
        }
    }
    public List<User> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        @SuppressWarnings("unchecked")
        List<User> employees = (List<User>) session.createQuery("FROM User s ORDER BY s.username ASC").list();

        session.getTransaction().commit();
        session.close();
        return employees;
    }
    public void Add(User student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(student);
        session.getTransaction().commit();
        session.close();
    }
    public void updateAge(int id,int age){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User u = (User) session.get(User.class,id);
        u.setAge(age);
        session.getTransaction().commit();
    }
    public void deleteUser(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        User student = (User) session.get(User.class, id);
        session.delete(student);
        session.getTransaction().commit();
        session.close();
    }
}
