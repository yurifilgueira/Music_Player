package br.ufrn.imd.Repository;

import br.ufrn.imd.Repository.exceptions.UserNotFoundException;
import br.ufrn.imd.model.entities.User;

import java.util.*;

public class UserDAO {

    private List<User> users;
    private Map<String, String> loginInformation;
    private UserDAO userDAO;

    public UserDAO() {
        users = new ArrayList<>();
        loginInformation = new HashMap<>();
    }

    public UserDAO getInstance(){
        if (userDAO == null){
             return new UserDAO();
        }
        return userDAO;
    }

    public void putUser(User user){
        addLoginInformations(user.getEmail(), user.getPassword());
        users.add(user);
    }

    public void addLoginInformations(String email, String password){
        loginInformation.put(email, password);
    }

    public void deleteUser(User user){
        removeLoginInformations(user.getEmail(), user.getPassword());
        users.remove(user);
    }

    public void removeLoginInformations(String email, String password){
        loginInformation.remove(email, password);
    }

    public User getByEmail(String email){

        int idx = indexOfUser(email);

        if (idx == -1) {
            throw new UserNotFoundException("User not found.");
        }

        return users.get(idx);
    }

    public int indexOfUser(String email){
        for (int i = 0; i < users.size(); i++) {
            if (Objects.equals(email, users.get(i).getEmail())) {
                return i;
            }
        }

        return -1;
    }

    public boolean containsUser(String email){
        return loginInformation.containsKey(email);
    }

    public boolean loginInformationIsValid(String email, String password){
        User user = getByEmail(email);

        return Objects.equals(loginInformation.get(email), password);

    }
}
