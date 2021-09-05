package com.softserve.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.softserve.entities.ActorEntity;
import com.softserve.entities.FilmEntity;
import com.softserve.utils.FileSQLReader;

import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionUtil;
import io.reactivex.Flowable;
import io.reactivex.internal.functions.Functions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgConnection;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.reactivex.sqlclient.Pool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.RowSet;
import io.vertx.reactivex.sqlclient.Tuple;
import io.vertx.sqlclient.PoolOptions;
import net.bytebuddy.implementation.bytecode.Throw;

public class VertxDBActions {

    private PgConnectOptions connectOptions;
    private Pool client;
    private PoolOptions poolOptions;

    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/pagila";

    public static final String USER = "postgres";
    public static final String PASS = "P@ssw0rd";


    public Vertx getOptions() {
        VertxOptions options = new VertxOptions();

        options.setBlockedThreadCheckInterval(5);
        options.setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS);

        options.setMaxEventLoopExecuteTime(100);
        options.setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS);

        options.setMaxWorkerExecuteTime(10);
        options.setMaxWorkerExecuteTimeUnit(TimeUnit.SECONDS);

        options.setWarningExceptionTime(20);
        options.setWarningExceptionTimeUnit(TimeUnit.SECONDS);

        return Vertx.vertx(options);
    }

    public void connect() {

        connectOptions = new PgConnectOptions()
                .setHost("127.0.0.1")
                .setPort(5432)
                .setDatabase("pagila")
                .setUser(USER)
                .setCachePreparedStatements(true)
                .setPreparedStatementCacheMaxSize(100)
                .setSsl(false)
                .setConnectTimeout(5000)
                .setIdleTimeout(5000)
                .setSslHandshakeTimeout(5000)
                .setPassword(PASS);

        poolOptions = new PoolOptions().setMaxSize(5);

        client = PgPool.pool(connectOptions, poolOptions);

    }

    public void close() throws Exception {
        if (!client.rxGetConnection().test().hasSubscription()) {
            client.rxClose();
            client.close();
        } else {
            Thread.sleep(1000);
            close();
        }
    }

    public void simpleQuery() {

        Flowable<String> titles =
                client.rxGetConnection().flatMapPublisher( connection ->
                connection.rxPrepare("SELECT * FROM film WHERE release_year=$1")
                        .flatMap(statement -> statement.query().rxExecute(Tuple.of(2006)))
                .flattenAsFlowable(Functions.identity())
                .map(row -> row.getString("title"))
                        .doOnError(Throwable::printStackTrace)
        );

        titles.doOnNext(title -> System.out.println(title)).subscribe();

    }
    
    public List<FilmEntity> getFilmsByYear(int year) throws IOException, Exception{
        String filePath = "src\\test\\resources\\queries\\films_by_year.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, year);
//        String query = String.format(text, "$1");
        List<FilmEntity> filmEntities = new ArrayList<FilmEntity>();
        //
        client.preparedQuery(query).execute(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> rows = ar.result();
                for (Row row : rows) {
                    FilmEntity filmEntity = new FilmEntity();
                    filmEntity.setFilm_id(row.getInteger("film_id"));
                    filmEntity.setTitle(row.getString("title"));
                    filmEntity.setDescription(row.getString("description"));
                    filmEntity.setRelease_year(row.getInteger("release_year"));
                    filmEntities.add(filmEntity);
                }
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
        });
        
        
        //
//        Flowable<Row> films =
//                client.rxGetConnection().flatMapPublisher( connection ->
//                connection.rxPrepare("SELECT * FROM film WHERE release_year=$1")
//                        .flatMap(statement -> statement.query().rxExecute(Tuple.of(year)))
//                .flattenAsFlowable(Functions.identity())
//                        .doOnError(Throwable::printStackTrace)
//        );
//        
//        System.out.println(row->);
        
//        films.doOnNext(row -> {
//            FilmEntity filmEntity = new FilmEntity();
//            filmEntity.setFilm_id(row.getInteger("film_id"));
//            filmEntity.setTitle(row.getString("title"));
//            filmEntity.setDescription(row.getString("description"));
//            filmEntity.setRelease_year(row.getInteger("release_year"));
//            filmEntities.add(filmEntity);
//        }).subscribe();
//        
//        Collector<Row, ?, FilmEntity> collector = Collectors.toMap(
//                row -> row.getString("film_id")
//              );
        
        Thread.sleep(1000);
        
        return filmEntities;
    }
    
    public List<ActorEntity> getActorsByFilmName(String filmName) throws IOException, Exception{
        String filePath = "src\\test\\resources\\queries\\actors_by_film_name.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, filmName);
        List<ActorEntity> actorEntities = new ArrayList<ActorEntity>();
        //
        client.preparedQuery(query).execute(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> rows = ar.result();
//                System.out.println("Rows: " + rows.size());
                for (Row row : rows) {
                    ActorEntity actorEntity = new ActorEntity();
                    actorEntity.setActor_id(row.getInteger("actor_id"));
                    actorEntity.setFirst_name(row.getString("first_name"));
                    actorEntity.setLast_name(row.getString("last_name"));
                    actorEntities.add(actorEntity);
                }
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
        });
        //
        Thread.sleep(1000);
        return actorEntities;
    }
    
    public List<FilmEntity> getFilmsByActorName(String firstName, String lastName) throws IOException, Exception{
        String filePath = "src\\test\\resources\\queries\\films_by_actor.sql";
        String text = FileSQLReader.getText(filePath);
        String query = String.format(text, firstName, lastName);
        List<FilmEntity> filmEntities = new ArrayList<FilmEntity>();
        //
        client.preparedQuery(query).execute(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> rows = ar.result();
//                System.out.println("query: " + query);
                for (Row row : rows) {
                    FilmEntity filmEntity = new FilmEntity();
                    filmEntity.setFilm_id(row.getInteger("film_id"));
                    filmEntity.setTitle(row.getString("title"));
                    filmEntity.setRelease_year(row.getInteger("release_year"));
                    filmEntities.add(filmEntity);
                }
//                System.out.println("Rows: " + rows.size());
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
        });
        //
        Thread.sleep(1000);

        return filmEntities;
    }


    public void query() {

        connectOptions = new PgConnectOptions()
                .setHost("127.0.0.1")
                .setPort(5432)
                .setDatabase("pagila")
                .setUser(USER)
                .setCachePreparedStatements(true)
                .setPreparedStatementCacheMaxSize(100)
                .setSsl(false)
                .setConnectTimeout(5000)
                .setIdleTimeout(5000)
                .setSslHandshakeTimeout(5000)
                .setPassword(PASS);

        PgConnection.connect(getOptions(), connectOptions)
                .compose(conn -> {
                    System.out.println("Connected");

                    return conn
                            .query("SELECT * FROM film WHERE release_year=2006")
                            .execute()
                            .onComplete(ar -> {
                                conn.close();
                            });
                }).onComplete(res -> {
            if (res.succeeded()) {

                List<String> titles = StreamSupport.stream(res.result().spliterator(),false)
                        .map(row -> row.getString("title"))
                        .collect(Collectors.toList());

                System.out.println(titles);
            } else {
                System.out.println("Could not connect: " + res.cause().getMessage());
            }
        });
    }
}