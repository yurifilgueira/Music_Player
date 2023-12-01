package br.ufrn.imd.repositories;

import br.ufrn.imd.model.entities.CommonUser;
import br.ufrn.imd.model.entities.VipUser;
import br.ufrn.imd.repositories.exceptions.UserNotFoundException;
import br.ufrn.imd.model.entities.User;
import br.ufrn.imd.services.UserService;

import java.io.*;
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

    private UserDAO() {
        this.users = new ArrayList<>();
        this.loginInformation = new HashMap<>();
        this.idGenerator = new AtomicLong();
    }

    public static UserDAO getInstance(){
        if (userDAO == null){
             userDAO = new UserDAO();
        }

        return userDAO;
    }

    public void saveUser(User user) {

        String path = "src/resources/dataResources/users.txt";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true))){

            putUser(user);

            if (user instanceof CommonUser){
                bufferedWriter.write("common;");
            }
            else {
                bufferedWriter.write("vip;");
            }

            bufferedWriter.append(String.valueOf(user.getId())).append(";");
            bufferedWriter.append(String.valueOf(user.getName())).append(";");
            bufferedWriter.append(String.valueOf(user.getEmail())).append(";");
            bufferedWriter.append(String.valueOf(user.getPassword()));

            bufferedWriter.newLine();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void putUser(User user){
        setUserId(user);
        addLoginInformations(user.getEmail(), user.getPassword());
        users.add(user);
    }

    public void setUserId(User user){
        user.setId(idGenerator.incrementAndGet());
    }

    public void addLoginInformations(String email, String password){
        loginInformation.put(email, password);
    }

    public void loadUsers(){

        String path = "src/resources/dataResources/users.txt";

        if (verifyIfFileExists(path)) {

            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {

                String line = bufferedReader.readLine();

                while (line != null) {
                    String[] userData = line.split(";");

                    User user = collectUserData(userData);

                    putUser(user);

                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public User collectUserData(String[] userData){

        Long id = Long.parseLong(userData[1]);
        String name = userData[2];
        String email = userData[3];
        String password = userData[4];

        if(userData[0].equals("vip")){
            return new VipUser(id, name, email, password);
        }

        return new CommonUser(id, name, email, password);

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

    public boolean verifyIfFileExists(String path){
        File file = new File(path);

        return file.exists();

    }

}