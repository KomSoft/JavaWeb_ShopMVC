package com.komsoft.shopmvc.model;

import com.komsoft.shopmvc.util.Encoding;

public class UserRegisteringData {
    private final String login;
    private String encryptedPassword;
    private final String fullName;
    private final String region;
    private final String gender;
    private final String comment;

    private String savedPassword;
//    Це збочення, але поки так. Щоб не видавати дійсний пароль та була можливість порівняти
//    два хешованих пароля, сюди зберігаємо пароль з бази. Таким чином дійсний пароль назовні
//    не вийде, і не можна буде записати відкритий пароль в базу, бо видаватись буде  encryptedPassword

    public UserRegisteringData(String login, String password, String fullName, String region, String gender, String comment) {
        this.login = login;
        this.encryptedPassword = this.encryptPassword(password);
        this.fullName = fullName;
        this.region = region;
        this.gender = gender;
        this.comment = comment;
    }

    public UserRegisteringData(String login, String fullName, String region, String gender, String comment) {
        this.login = login;
        this.encryptedPassword = null;
        this.fullName = fullName;
        this.region = region;
        this.gender = gender;
        this.comment = comment;
    }

    public String getLogin() {
        return login;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRegion() {
        return region;
    }

    public String getGender() {
        return gender;
    }

    public String getComment() {
        return comment;
    }

    public void setSavedPassword(String savedPassword) {
        this.savedPassword = savedPassword;
    }

    public static String encryptPassword(String password) {
//        return Encoding.bCryptEncryption(password);
        return Encoding.md5EncryptionWithSalt(password);
    }

    public boolean isPasswordsEquals() {
        return (this.encryptedPassword != null && this.encryptedPassword.equals(savedPassword));
    }

    public boolean isPasswordCorrect(String candidatePassword) {
        if (encryptPassword(candidatePassword).equals(savedPassword)) {
//      TODO   for BCrypt - not checked yet
//        if (BCrypt.checkpw(candidatePassword, this.savedPassword)) {
            this.encryptedPassword = savedPassword;
            return true;
        } else {
            return false;
        }
    }

}
