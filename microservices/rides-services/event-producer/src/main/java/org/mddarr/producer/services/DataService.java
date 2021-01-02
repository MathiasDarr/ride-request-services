package org.mddarr.producer.services;

import org.mddarr.producer.models.Driver;
import org.mddarr.rides.event.dto.AvroDriver;
import org.mddarr.rides.event.dto.AvroRideRequest;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataService {

    public static List<Driver> getDriversFromDB(){
        List<Driver> avroDrivers = new ArrayList<>();
        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgresdb",
                "postgres", "postgres");
             PreparedStatement pst = con.prepareStatement("SELECT driverid, first_name, last_name, average_shift_length FROM drivers");
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
                    .getConnection("jdbc:postgresql://localhost:5432/postgresdb",
                            "postgres", "postgres");

                session_id = UUID.randomUUID().toString();
                stmt = c.createStatement();
                String sql = String.format("INSERT INTO driving_session (\"session_id\",\"driverid\", \"session_length\",\"session_status\") "
                        + "VALUES ('%s', '%s', '%d','%s' );",session_id, driverID, average_session_length, "MATCHING");
                stmt.executeUpdate(sql);
                return session_id;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return "-1";
        }
    }





//    public static List<AvroDriver> getDriversFromDB(){
//        List<AvroDriver> avroDrivers = new ArrayList<>();
//        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgresdb",
//                "postgres", "postgres");
//             PreparedStatement pst = con.prepareStatement("SELECT driverid, first_name, last_name FROM drivers");
//             ResultSet rs = pst.executeQuery()) {
//            while (rs.next()) {
//                avroDrivers.add(new AvroDriver (rs.getString(1),rs.getString(2),rs.getString(3) ));
//            }
//        } catch (SQLException e) {
//            System.err.println(e.getClass().getName()+": "+e.getMessage());
//        }
//        return avroDrivers;
//    }

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
