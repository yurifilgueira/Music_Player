package br.ufrn.imd.repositories;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.repositories.exceptions.UserNotFoundException;
import br.ufrn.imd.model.entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class UserDAO {

    private AtomicLong idGenerator;
    private List<User> users;
    private Map<String, String> loginInformation;
    private static UserDAO userDAO;

    public UserDAO() {
        this.users = new ArrayList<>();
        this.loginInformation = new HashMap<>();
        this.idGenerator = new AtomicLong();

        putUser(new CommonUser(null, "Admin","admin@email.com", "1234"));
    }

    public static UserDAO getInstance(){
        if (userDAO == null){
             userDAO = new UserDAO();
        }

        return userDAO;
    }

    public void setUserId(User user){
        user.setId(idGenerator.incrementAndGet());
    }

    public void putUser(User user){
        setUserId(user);
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