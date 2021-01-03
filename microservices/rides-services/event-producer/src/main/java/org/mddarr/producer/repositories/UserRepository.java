package org.mddarr.producer.repositories;

import org.mddarr.producer.models.Driver;
import org.mddarr.producer.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    public static List<User> getUsers(){
        List<User> users = new ArrayList<>();

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgresdb","postgres", "postgres");
             PreparedStatement pst = con.prepareStatement("SELECT userid, first_name, last_name FROM users limit 50");
             ResultSet rs = pst.executeQuery()) {
             while (rs.next()) {
                users.add(new User(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return users;
    }

}
