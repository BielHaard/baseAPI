package com.base.base.utils;

import java.util.regex.Pattern;

public class VerifyPassword {
    public static void passwordValidator(String password) throws Exception {
        if (password.length() < 8) {
            throw new Exception("A senha deve ter pelo menos 8 caracteres.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new Exception("A senha deve conter pelo menos uma letra maiúscula.");
        }
        Pattern SpecialCharacter = Pattern.compile("[!@#$%^&*(),.?\":{}|<>]");
        if (!SpecialCharacter.matcher(password).find()) {
            throw new Exception("A senha deve conter pelo menos um caractere especial.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new Exception("A senha deve conter pelo menos um número.");
        }
    }
}
