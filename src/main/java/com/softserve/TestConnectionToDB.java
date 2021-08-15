package com.softserve;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnectionToDB {

    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/pagila";
    public static final String USER = "postgres";
    public static final String PASS = "P@ssw0rd";

    public static void main(String[] args) {

        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                System.out.println("\n================== Success connection ========================");
                //
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery("SELECT * FROM film where release_year=2006 limit 100");
//                while (result.next()) {
//                    System.out.println(result.getInt("film_id") + "\t" + result.getString("title") + "\t\t"
//                            + result.getInt("release_year"));
//                }
                
                ResultSet result2 = statement.executeQuery("SELECT film.film_id, film.title, film.release_year, actor.first_name, actor.last_name \r\n"
                        + "FROM film \r\n"
                        + "join film_actor on film.film_id=film_actor.film_id\r\n"
                        + "join actor on film_actor.actor_id=actor.actor_id\r\n"
                        + "where title='BIRD INDEPENDENCE'");
//                while (result2.next()) {
//                    System.out.println(result2.getInt("film_id") + "\t" + result2.getString("title") + "\t\t"
//                            + result2.getInt("release_year") + "\t" + result2.getString("first_name") + "\t" + result2.getString("last_name"));
//                }
                
                ResultSet result3 = statement.executeQuery("SELECT film.film_id, film.title, film.release_year, actor.first_name, \r\n"
                        + "actor.last_name \r\n"
                        + "FROM film \r\n"
                        + "join film_actor on film.film_id=film_actor.film_id\r\n"
                        + "join actor on film_actor.actor_id=actor.actor_id\r\n"
                        + "where actor.first_name='RICHARD' and actor.last_name='PENN'");
                while (result3.next()) {
                    System.out.println(result3.getInt("film_id") + "\t" + result3.getString("title") + "\t\t"
                            + result3.getInt("release_year") + "\t" + result3.getString("first_name") + "\t" + result3.getString("last_name"));
                }
                
                //
                result.close();
                result2.close();
                result3.close();
                statement.close();
                conn.close();
                System.out.println("\nConnection closed");
            } else {
                System.out.println("Failed to make connection to database");
            }
        } catch (SQLException e) {
            System.err.println("Something went wrong: " + e.getMessage());
        }

    }

}
