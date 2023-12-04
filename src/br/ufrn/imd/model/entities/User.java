package br.ufrn.imd.model.entities;

import java.util.*;

public abstract class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Set<String> directories;

    public User() {
        this.directories = new HashSet<>();
    }

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.directories = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getDirectories() {
        return directories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Id: " + id
                + "; Username:" + name
                + "; Email: " + email;
    }

    public void addDirectory(String directory) {
        directories.add(directory);
    }
}
