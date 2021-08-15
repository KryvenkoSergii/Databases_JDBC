package com.softserve.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.softserve.entities.ActorEntity;
import com.softserve.entities.FilmEntity;
import com.softserve.utils.FileSQLReader;

public class DatabaseActions {

    private Connection conn;
    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/pagila";
    public static final String USER = "postgres";
    public static final String PASS = "P@ssw0rd";
    
    private PreparedStatement queryFilmsByYear = null;
    private PreparedStatement queryActorsByFilmName = null;
    private PreparedStatement queryFilmsByActorName = null;
    
    public boolean open() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            return true;
        } catch (SQLException e) {
            System.err.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }
    
    public void close() {
        try {
            if (queryFilmsByYear != null) {
                queryFilmsByYear.close();
            }
            if (queryActorsByFilmName != null) {
                queryActorsByFilmName.close();
            }
            if (queryFilmsByActorName != null) {
                queryFilmsByActorName.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Couldn't close connection: " + e.getMessage());
        }
    }
    
    public List<FilmEntity> getFilmsByYear(int year) throws IOException{
        String filePath = "src\\test\\resources\\queries\\films_by_year.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, year);
        //
        try (Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery(query);) {
            List<FilmEntity> filmEntities = new ArrayList<FilmEntity>();
            while (results.next()) {
                FilmEntity filmEntity = new FilmEntity();
                filmEntity.setFilm_id(results.getInt("film_id"));
                filmEntity.setTitle(results.getString("title"));
                filmEntity.setDescription(results.getString("description"));
                filmEntity.setRelease_year(results.getInt("release_year"));
                filmEntities.add(filmEntity);
            }
            return filmEntities;
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
    public List<ActorEntity> getActorsByFilmName(String filmName) throws IOException{
        String filePath = "src\\test\\resources\\queries\\actors_by_film_name.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, filmName);
        //
        try (Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery(query);) {
            List<ActorEntity> actorEntities = new ArrayList<ActorEntity>();
            while (results.next()) {
                ActorEntity actorEntity = new ActorEntity();
                actorEntity.setActor_id(results.getInt("actor_id"));
                actorEntity.setFirst_name(results.getString("first_name"));
                actorEntity.setLast_name(results.getString("last_name"));
                actorEntities.add(actorEntity);
            }
            return actorEntities;
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
    public List<FilmEntity> getFilmsByActorName(String firstName, String lastName) throws IOException{
        String filePath = "src\\test\\resources\\queries\\films_by_actor.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, firstName, lastName);
        //
        try (Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery(query);) {
            List<FilmEntity> filmEntities = new ArrayList<FilmEntity>();
            while (results.next()) {
                FilmEntity filmEntity = new FilmEntity();
                filmEntity.setFilm_id(results.getInt("film_id"));
                filmEntity.setTitle(results.getString("title"));
                filmEntity.setRelease_year(results.getInt("release_year"));
                filmEntities.add(filmEntity);
            }
            return filmEntities;
        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
            return null;
        }
    }
    
    public boolean updateFilm(FilmEntity filmEntity) throws IOException {
        String filePath = "src\\test\\resources\\queries\\update_film.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, "title", filmEntity.getTitle(), "film_id", filmEntity.getFilm_id());
        try {
            conn.createStatement().execute(query);
            return true;
        } catch (SQLException e) {
            System.err.println("Update Query failed: " + e.getMessage());
            return false;
        }
    }
    
    public boolean createFilm(FilmEntity filmEntity) throws IOException {
        String filePath = "src\\test\\resources\\queries\\insert_film.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, filmEntity.getTitle(), filmEntity.getDescription(), filmEntity.getRelease_year(), filmEntity.getLanguage_id());
        try (Statement statement = conn.createStatement(); ResultSet results = statement.executeQuery(query);) {
            return true;
        } catch (SQLException e) {
            System.err.println("Create Query failed: " + e.getMessage());
            return false;
        }
    }
    
}
