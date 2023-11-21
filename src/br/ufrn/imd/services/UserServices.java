package br.ufrn.imd.services;

import br.ufrn.imd.Repository.UserDAO;
import br.ufrn.imd.model.entities.User;

public class UserServices {

    UserDAO userDAO;

    public UserServices() {
        this.userDAO = new UserDAO();
    }

    public void putUser(User user){
        userDAO.putUser(user);
    }

    public void deleteRemove(User user){
        userDAO.deleteUser(user);
    }

    public User getByEmaiil(String email){
       return userDAO.getByEmail(email);
    }

    public boolean containsUser(String email){
        return userDAO.containsUser(email);
    }

    public boolean loginInformationIsValid(String email, String password){
        return userDAO.loginInformationIsValid(email, password);
    }

}
