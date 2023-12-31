package br.ufrn.imd.services;

import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.repositories.UserDAO;

import java.util.List;

public class UserService {
    private UserDAO userDAO;
    private static UserService userService;

    private UserService() {
        this.userDAO = UserDAO.getInstance();
    }

    public static UserService getInstance(){
        if(userService == null){
            userService = new UserService();
        }

        return userService;
    }

    public void putUser(User user){
        userDAO.putUser(user);
    }

    public void deleteUser(User user){
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

    public void loadUsers(){
        userDAO.loadUsers();
    }

    public List<User> getUsers(){
        return userDAO.getUsers();
    }

    public User getById(Long id){
        return userDAO.getById(id);
    }

}