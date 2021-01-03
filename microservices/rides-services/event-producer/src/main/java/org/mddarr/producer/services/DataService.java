package org.mddarr.producer.services;

import org.mddarr.producer.models.Driver;
import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroRideRequest;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataService {

    public DataService(){

    }

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

    public static String insertDrivingSession(String driverID, Integer average_session_length) {

        Statement stmt = null;
        Connection c = null;
        String session_id;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgresdb","postgres", "postgres");

                session_id = UUID.randomUUID().toString();
                stmt = c.createStatement();
                String sql = String.format("INSERT INTO driving_session (\"session_id\",\"driverid\", \"session_length\",\"session_status\") "
                        + "VALUES ('%s', '%s', '%d','%s' );",session_id, driverID, average_session_length, "MATCHING");
                stmt.executeUpdate(sql);
            c.close();
            return session_id;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return "-1";
        }
    }


    public static String insertSession(String session_id, String driverID, Integer session_length, Integer session_start, Integer session_end) {

        Statement stmt = null;
        Connection c = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgresdb","postgres", "postgres");

            stmt = c.createStatement();
            String sql = String.format("INSERT INTO sessions (\"session_id\",\"driverid\", \"session_length\",\"session_start\", \"session_end\") "
                    + "VALUES ('%s', '%s', '%d','%d', %d );",session_id, driverID, session_length, session_start, session_end);
            stmt.executeUpdate(sql);
            c.close();
            return session_id;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return "-1";
        }
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



    public static List<AvroRideRequest> getRideRequestsFromDB(){
        List<AvroRideRequest> avroRideRequests = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgresdb",
                "postgres", "postgres");
             PreparedStatement pst = con.prepareStatement("SELECT request_id, userid,riders FROM ride_requests");
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                avroRideRequests.add(new AvroRideRequest (rs.getString(1),rs.getString(2),rs.getInt(3) ));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return avroRideRequests;
    }


}
