package br.ufrn.imd.services.util;

import java.util.regex.Pattern;

public class EmailValidator {

    public static boolean emailIsValid(String email){
        String emailRegex = "^[a-zA=Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@"+
                "(?:[a-zA-Z0-9-]+\\.)+[a-z"+
                "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(emailRegex);

        if(email == null){
            return false;
        }

        return pattern.matcher(email).matches();

    }
}