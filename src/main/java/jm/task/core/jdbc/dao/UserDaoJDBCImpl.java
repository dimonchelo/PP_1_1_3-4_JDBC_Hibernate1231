package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User" +
                    "(id BIGINT not null auto_increment," +
                    " name TEXT, " +
                    "lastname TEXT, " +
                    "age INT, " +
                    "PRIMARY KEY (id))");
        } catch (SQLException e) {
            System.out.println("не созда");
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS User");
        } catch (SQLException e) {
            System.out.println("не удал все");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO User(name, lastname, age) VALUES(?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не добавл");
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM User WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("не удал");
        }

    }

    public List<User> getAllUsers() {
        List<User> allPeople = new ArrayList<>();
        try(Statement statement = connection.createStatement()) {
            String sql = "SELECT * FROM User";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                allPeople.add(user);
            }
        } catch (SQLException e) {
            System.out.println("не выводит всех");
        }
        return allPeople;

    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM User";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("не очищ");
        }

    }
}