package com.softserve;

import java.io.IOException;
import java.util.List;

import io.reactivex.Flowable;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.reactivex.sqlclient.Pool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.RowSet;
import io.vertx.sqlclient.PoolOptions;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.softserve.dao.DatabaseActions;
import com.softserve.dao.VertxDBActions;
import com.softserve.entities.ActorEntity;
import com.softserve.entities.FilmEntity;

public class TestFraimwork {

    public static void main(String[] args) throws Exception {
        
//        DatabaseActions databaseActions = new DatabaseActions();
//        databaseActions.open();
//        List<FilmEntity> filmEntities = databaseActions.getFilmsByYear(2006);
////        for (FilmEntity filmEntity : filmEntities) {
//////            System.out.println(filmEntity);
////        }
////        
//        List<ActorEntity> actorEntities = databaseActions.getActorsByFilmName("BIRD INDEPENDENCE");
////        for (ActorEntity actorEntity : actorEntities) {
////            System.out.println(actorEntity);
////        }
////        
//        filmEntities = databaseActions.getFilmsByActorName("RICHARD", "PENN");
////        for (FilmEntity filmEntity : filmEntities) {
////            System.out.println(filmEntity);
////        }
////        
////        FilmEntity filmEntity = new FilmEntity();
////        filmEntity.setTitle("test title 1");
////        filmEntity.setDescription("test description 1");
////        filmEntity.setRelease_year(2001);
////        filmEntity.setLanguage_id(1);
//////        databaseActions.createFilm(filmEntity);
////        filmEntities = databaseActions.getFilmsByYear(filmEntity.getRelease_year());
////        for (FilmEntity filmEntity1 : filmEntities) {
////            System.out.println(filmEntity1);
////            filmEntity.setFilm_id(filmEntity1.getFilm_id());
////        }
////        
////        filmEntity.setTitle("test title 1.2");
////        databaseActions.updateFilm(filmEntity);
////        filmEntities = databaseActions.getFilmsByYear(filmEntity.getRelease_year());
////        for (FilmEntity filmEntity1 : filmEntities) {
////            System.out.println(filmEntity1);
////        }
////        
//        databaseActions.close();
        
        VertxDBActions vertxDBActions = new VertxDBActions();
        vertxDBActions.connect();
        //
//        vertxDBActions.simpleQuery();
//        vertxDBActions.query();
        //
//        List<FilmEntity> filmEntities2 = vertxDBActions.getFilmsByYear(2006);
//        for (FilmEntity filmEntity : filmEntities2) {
//            System.out.println(filmEntity);
//        }
//        System.out.println(filmEntities.size());
        //
//        List<ActorEntity> actorEntities2 = vertxDBActions.getActorsByFilmName("BIRD INDEPENDENCE");
//        for (ActorEntity actorEntity : actorEntities2) {
//            System.out.println(actorEntity);
//        }
        //
        List<FilmEntity> filmEntities3 = vertxDBActions.getFilmsByActorName("RICHARD", "PENN");
        for (FilmEntity filmEntity : filmEntities3) {
            System.out.println(filmEntity);
        }
        //
        vertxDBActions.close();
        
    }

}
