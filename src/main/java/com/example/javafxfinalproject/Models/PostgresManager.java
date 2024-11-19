package com.example.javafxfinalproject.Models;

import java.sql.*;

public class PostgresManager {
    public static void test() {
        String connectionString = "jdbc:postgresql://ep-lively-rain-a2ixsukt.eu-central-1.aws.neon.tech/javafx-demo-1?user=javafx-demo-1_owner&password=mCU34cBibVpN&sslmode=require";

        try {
            Connection connection = DriverManager.getConnection(connectionString);

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM playing_with_neon");
            while(rs.next()) {
                System.out.println(rs.getString(2));
            }
            rs.close();
            st.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}
