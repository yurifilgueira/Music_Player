package br.ufrn.imd.model.entities;

public class VipUser extends User{


    public VipUser() {
    }

    public VipUser(Long id, String name, String email, String password) {
        super(id, name, email, password);
    }
}
