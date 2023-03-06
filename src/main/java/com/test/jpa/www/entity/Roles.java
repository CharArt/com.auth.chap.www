package com.test.jpa.www.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<Users> users;

    @ManyToMany(mappedBy = "roles")
    private List<GoogleUser> googleUsers;

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.id != null ? id.hashCode() : 0);
        result = 31 * result + (this.role != null ? role.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Roles)) return false;

        Roles role = (Roles) o;

        if (this.id != role.id) return false;
        if (this.role != null ? !role.equals(role.role) : role.role != null) return false;
        return this.hashCode() == role.hashCode();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add(this.id.toString());
        joiner.add(this.role);
        return joiner.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<GoogleUser> getGoogleUsers() {
        return googleUsers;
    }

    public void setGoogleUsers(List<GoogleUser> googleUsers) {
        this.googleUsers = googleUsers;
    }
}
