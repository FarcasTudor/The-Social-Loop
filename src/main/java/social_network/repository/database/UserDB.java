package social_network.repository.database;

import social_network.domain.User;
import social_network.repository.Repository;
import social_network.repository.memory.InMemoryRepository;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserDB extends InMemoryRepository<Long, User> {

    final String url;
    final String username;
    final String password;

    public UserDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }


    @Override
    public User findOne(Long id) {
        String idS = id.toString();
        String sql = "SELECT * from users where id ='" + idS + "'" ;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Long id1 = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Integer age = resultSet.getInt("age");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(firstName, lastName, age, username, password);
                user.setId(id1);
                return user;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exists(Long id) {
        Iterable<User> users = findAll();
        for(User u : users)
        {
            if(u.getId().equals(id))
                return true;
        }
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = statement.executeQuery()) {
            Set<User> users = new HashSet<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Integer age = resultSet.getInt("age");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                User user = new User(firstName, lastName, age, username, password);
                user.setId(id);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User save(User entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must be not null");
        }
        if (exists(entity.getId())) {
            throw new IllegalArgumentException("Entity already exists");
        }
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getFirstName());
            statement.setString(3, entity.getLastName());
            statement.setInt(4, entity.getAge());
            statement.setString(5, entity.getUsername());
            statement.setString(6, entity.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(Long id) {
        String idU = id.toString();
        if (exists(id)) {
            String sql = "DELETE FROM users WHERE id = '" + idU + "'";
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                return null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    return null;
    }

    public int containerSize(){
        Iterable<User> users = findAll();
        int capacity = 0;
        for(User u : users){
            capacity++;
        }
        return capacity;
    }
}

