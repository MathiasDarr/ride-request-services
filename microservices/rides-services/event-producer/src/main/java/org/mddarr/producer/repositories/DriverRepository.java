package org.mddarr.producer.repositories;

import org.mddarr.producer.models.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverRepository {

    public static List<Driver> getDriversFromDB(){
        List<Driver> avroDrivers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgresdb","postgres", "postgres");
             PreparedStatement pst = con.prepareStatement("SELECT driverid, first_name, last_name, average_shift_length FROM drivers limit 50");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                avroDrivers.add(new Driver (rs.getString(1),rs.getString(2),rs.getString(3), rs.getInt(4) ));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return avroDrivers;
    }


    public static Driver getDriver(String driverID){
        Statement stmt = null;
        Connection c = null;
        String session_id;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgresdb","postgres", "postgres");

            PreparedStatement pst = c.prepareStatement("SELECT driverid, first_name, last_name, average_shift_length FROM drivers WHERE driverid = ?; ");
            pst.setString(1, driverID);
            ResultSet rs = pst.executeQuery();
            rs.next();
            Driver driver = new Driver (rs.getString(1),rs.getString(2),rs.getString(3), rs.getInt(4));
            c.close();
            return driver;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return null;
        }
    }


}
