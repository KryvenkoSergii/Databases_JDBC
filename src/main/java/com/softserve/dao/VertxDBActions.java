package com.softserve.dao;

import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;

public class VertxDBActions {

    private PgConnectOptions connectOptions;
    private SqlClient client;

    public static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/pagila";

    public static final String USER = "postgres";
    public static final String PASS = "P@ssw0rd";

    public void open() {
        connectOptions = new PgConnectOptions()
                .setHost("localhost")
                .setPort(5432)
                .setDatabase("pagila")
                .setUser(USER)
                .setPassword(PASS);

        // Pool options
        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);

        // Create the client pool
        client = PgPool.client(connectOptions, poolOptions);
    }

    public void close() {
        client.close();
    }

    public void simpleQuery() {
        client.query("SELECT * FROM film WHERE release_year=2006").execute(ar -> {
            if (ar.succeeded()) {
                RowSet<Row> result = ar.result();
                System.out.println("Got " + result.size() + " rows ");
            } else {
                System.out.println("Failure: " + ar.cause().getMessage());
            }
        });
    }
}
