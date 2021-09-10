package ru.stqa.pft.mantis.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "mantis_user_table")
public class UserData {

    @Id
    @Column
    private int id;

    @Column
    private String username;

    @Column
    private String email;

    @Column
    private String password;

    @Column (name = "access_level")
    @Type( type = "short")
    private short accessLevel;

    public int getId() {
        return id;
    }

    public UserData setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserData setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserData setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserData setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public UserData setAccessLevel(short accessLevel) {
        this.accessLevel = accessLevel;
        return this;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return id == userData.id &&
                accessLevel == userData.accessLevel &&
                Objects.equals(username, userData.username) &&
                Objects.equals(email, userData.email) &&
                Objects.equals(password, userData.password);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accessLevel=" + accessLevel +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, accessLevel);
    }
}
