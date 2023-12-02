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
import java.util.stream.IntStream;

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
                bufferedWriter.write("common");
            }
            else {
                bufferedWriter.write("vip");
            }

            bufferedWriter.newLine();
            bufferedWriter.append(String.valueOf(user.getId()));
            bufferedWriter.newLine();
            bufferedWriter.append(String.valueOf(user.getName()));
            bufferedWriter.newLine();
            bufferedWriter.append(String.valueOf(user.getEmail()));
            bufferedWriter.newLine();
            bufferedWriter.append(String.valueOf(user.getPassword()));
            bufferedWriter.newLine();

            //bufferedWriter.newLine();

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
                List<String> usersInfo = new ArrayList<>();

                while (line != null) {
                    usersInfo.add(line);

                    line = bufferedReader.readLine();
                }

                IntStream.iterate(0, i -> i < usersInfo.size(), i -> i + 5).mapToObj(i -> collectUserData(usersInfo.subList(i, i + 5))).forEachOrdered(this::putUser);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public User collectUserData(List<String> userData){

        if(userData.size() < 5){
            return null;
        }

        Long id = Long.parseLong(userData.get(1));
        String name = userData.get(2);
        String email = userData.get(3);
        String password = userData.get(4);

        if(userData.get(0).equals("vip")){
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

    public User getById(int id){
        for (User user : users) {
            if(user.getId() == id){
                return user;
            }
        }

        return null;
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