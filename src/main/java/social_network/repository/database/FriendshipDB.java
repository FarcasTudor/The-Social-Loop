package social_network.repository.database;

import social_network.domain.Friendship;
import social_network.domain.FriendshipStatus;
import social_network.repository.Repository;
import social_network.repository.memory.InMemoryRepository;

import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class FriendshipDB extends InMemoryRepository<Long, Friendship> {

    final String url;
    final String username;
    final String password;

    public FriendshipDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public Friendship findOne(Long id) {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from friendships where id = \"id\"")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
;
                    Long id1 = resultSet.getLong("id1");

                    Long id2 = resultSet.getLong("id2");
                    System.out.println(id1 + " " + id2 + "id-ul friendshipului:  " + id);
                    String date = resultSet.getString("date");
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                    LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
                    Friendship friendship = new Friendship(id1, id2, localDateTime);
                    friendship.setId(id);
                    return friendship;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public Iterable<Friendship> findAll() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM friendships");
            ResultSet resultSet = statement.executeQuery();

            Set<Friendship> friendships = new HashSet<>();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                String friendsFrom = resultSet.getString("date");
                String status = resultSet.getString("status");
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                LocalDateTime localDateTime = LocalDateTime.parse(friendsFrom, formatter);
                Friendship friendship = new Friendship(id1, id2, localDateTime);
                friendship.setId(id);
                FriendshipStatus friendshipStatus = FriendshipStatus.valueOf(status);
                friendship.setStatus(friendshipStatus);
                friendships.add(friendship);

            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship save(Friendship entity) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO friendships (id, id1, id2, date,status) VALUES (?, ?, ?, ?, ?)");
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getId1());
            statement.setLong(3, entity.getId2());
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String dateTime = entity.getDate().format(formatter);
            statement.setString(4, dateTime);
            statement.setString(5, entity.getStatus().toString());
            statement.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Friendship delete(Long id) {
        String idS = id.toString();
        String sql = "delete from friendships where id ='" + idS + "'" ;
        try     {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
    }
        return null;
    }

    @Override
    public Friendship update(Friendship entity1, Friendship entity2) {
        String id1 = entity1.getId().toString();
        String id2 = entity2.getId().toString();
        String sql = "update friendships set id = ?, id1 = ?, id2 = ?, date = ?, status = ? where id = ?";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, entity2.getId());
            ps.setLong(2, entity2.getId1());
            ps.setLong(3, entity2.getId2());
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            String dateTime = entity2.getDate().format(formatter);
            ps.setString(4, dateTime);
            ps.setString(5,entity2.getStatus().toString());
            ps.setLong(6, entity1.getId());
            ps.executeUpdate();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int containerSize(){
        Iterable<Friendship> friendships = findAll();
        int capacity = 0;
        for(Friendship friendship : friendships){
            capacity++;
        }
        return capacity;
    }
}
