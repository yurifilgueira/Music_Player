package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.repositories.UserDAO;

public class UserService {

    private UserDAO userDAO;

    public UserService() {
        this.userDAO = UserDAO.getInstance();
    }

    public void putUser(User user){
        userDAO.putUser(user);
    }

    public void deleteRemove(User user){
        userDAO.deleteUser(user);
    }

    public User getByEmail(String email){
       return userDAO.getByEmail(email);
    }

    public boolean containsUser(String email){
        return userDAO.containsUser(email);
    }

    public boolean loginInformationIsValid(String email, String password){
        return userDAO.loginInformationIsValid(email, password);
    }
}