package br.ufrn.imd.model.entities;

import java.util.concurrent.atomic.AtomicLong;

public class VipUser extends User{


    public VipUser() {
    }

    public VipUser(Long id, String email, String name, String password) {
        super(id, email, name, password);
    }
}
