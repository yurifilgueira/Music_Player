package br.ufrn.imd.model.entities;

public class AdminUser extends User {
    public AdminUser() {
    }

    public AdminUser(Long id, String name, String email, String password) {
        super(id, name, email, password);
    }
}
