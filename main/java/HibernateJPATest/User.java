package HibernateJPATest;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Serializable {
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "age", nullable = false)
    private int age;
    @Id
    @Column(name = "id", nullable = false)
    private int id;


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                id == user.id &&
                Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, age, id);
    }

    @Override
    public String toString() {
        return "HibernateTest.User{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}
