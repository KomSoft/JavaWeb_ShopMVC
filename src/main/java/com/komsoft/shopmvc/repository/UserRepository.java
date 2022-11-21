package com.komsoft.shopmvc.repository;

import com.komsoft.shopmvc.exception.DataBaseException;
import com.komsoft.shopmvc.exception.ValidationException;
import com.komsoft.shopmvc.model.AuthorizedUser;
import com.komsoft.shopmvc.model.UserRegisteringData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class UserRepository {
    private PostgreSQLJDBC postgreSQLJDBC;
    private Connection connection;
    Logger logger = Logger.getLogger(UserRepository.class.getName());


    public UserRepository(PostgreSQLJDBC postgreSQLJDBC) {
        this.postgreSQLJDBC = postgreSQLJDBC;
    }

    public UserRepository() {
        this.postgreSQLJDBC = null;
        this.connection = null;
    }

    public void getConnection() throws DataBaseException {
        if (connection == null) {
            if (postgreSQLJDBC == null) {
                this.postgreSQLJDBC = new PostgreSQLJDBC();
            }
            postgreSQLJDBC.establishConnection();
            connection = postgreSQLJDBC.getConnection();
        }
    }
    public void closeConnection() throws DataBaseException {
        try {
            if (connection != null) {
                connection.close();
            }
            if (postgreSQLJDBC != null) {
                postgreSQLJDBC.closeConnection();
            }
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] Error closing connection" + e.getMessage());
        }
    }

//  TODO - correct for BCrypt password
    public String getFullNameByLoginAndPassword(String login, String password) throws DataBaseException {
        final String GET_FULLNAME_BY_LOGIN_PASSWORD = "SELECT full_name FROM users WHERE login = ? AND password = ?";
        String fullName = null;
        getConnection();
        if (login == null || password == null || login.trim().length() == 0 || password.trim().length() == 0) {
            throw new DataBaseException("[UserRepository] Login or password is null or blank");
        }
        try {
            PreparedStatement statement = connection.prepareStatement(GET_FULLNAME_BY_LOGIN_PASSWORD);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                fullName = resultSet.getString("full_name");
            }
            resultSet.close();
            statement.close();
            return fullName;
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        }
    }

    public String getFullNameByLogin(String login) throws DataBaseException {
        final String GET_FULLNAME_BY_LOGIN = "SELECT full_name FROM users WHERE login = ?";
        String fullName = null;
        getConnection();
        if (login == null || login.trim().length() == 0) {
            throw new DataBaseException("[UserRepository] Login is null or empty");
        }
        try {
            PreparedStatement statement = connection.prepareStatement(GET_FULLNAME_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
               fullName = resultSet.getString("full_name");
            }
            resultSet.close();
            statement.close();
            return fullName;
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        }
    }

    public UserRegisteringData getUserByLogin(String login) throws DataBaseException {
        final String GET_USER_BY_LOGIN = "SELECT * FROM users WHERE login = ?";
        UserRegisteringData user = null;
        getConnection();
        if (login == null || login.trim().length() == 0) {
            throw new DataBaseException("[UserRepository] Login is null or empty");
        }
        try {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
//                String login = resultSet.getString("login");
                String savedPassword = resultSet.getString("password");
                String fullName = resultSet.getString("full_name");
                String region = resultSet.getString("region");
                String gender = resultSet.getString("gender");
                String comment = resultSet.getString("comment");
                user = new UserRegisteringData(login, fullName,region,gender,comment);
                user.setSavedPassword(savedPassword);
            }
            resultSet.close();
            statement.close();
            return user;
        } catch (SQLException e) {
            throw new DataBaseException("[UserRepository] SQLException " + e.getMessage());
        }
    }

    public UserRegisteringData saveUser(UserRegisteringData user) throws DataBaseException, ValidationException {
        final String INSERT_USER = "INSERT INTO users (login, password, full_name, region, gender, comment) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        if (AuthorizedUser.isUserRegisteringDataCorrect(user)) {
            getConnection();
//        check userRegisteringData
            try {
                String fullName = this.getFullNameByLogin(user.getLogin());
                if (fullName != null) {
                    throw new ValidationException(String.format("[UserRepository] User %s already exists", user.getLogin()));
                }
                PreparedStatement statement = connection.prepareStatement(INSERT_USER);
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getEncryptedPassword());
                statement.setString(3, user.getFullName());
                statement.setString(4, user.getRegion());
                statement.setString(5, user.getGender());
                statement.setString(6, user.getComment());
                int id = statement.executeUpdate();
                statement.close();
                if (id == 1) {
                    return user;
                } else {
                    throw new DataBaseException("[UserRepository] Unknown reason. User not saved");
                }
            } catch (SQLException e) {
                throw new DataBaseException("[UserRepository] Can't save user. SQLException " + e.getMessage());
            }
        }
        return null;
    }

}
