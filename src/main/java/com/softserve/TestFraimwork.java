package com.softserve;

import java.io.IOException;
import java.util.List;

import com.softserve.dao.DatabaseActions;
import com.softserve.dao.VertxDBActions;
import com.softserve.entities.ActorEntity;
import com.softserve.entities.FilmEntity;

public class TestFraimwork {

    public static void main(String[] args) throws IOException {
        
//        DatabaseActions databaseActions = new DatabaseActions();
//        databaseActions.open();
//        List<FilmEntity> filmEntities = databaseActions.getFilmsByYear(2006);
//        for (FilmEntity filmEntity : filmEntities) {
////            System.out.println(filmEntity);
//        }
//        
//        List<ActorEntity> actorEntities = databaseActions.getActorsByFilmName("BIRD INDEPENDENCE");
//        for (ActorEntity actorEntity : actorEntities) {
////            System.out.println(actorEntity);
//        }
//        
//        filmEntities = databaseActions.getFilmsByActorName("RICHARD", "PENN");
//        for (FilmEntity filmEntity : filmEntities) {
////            System.out.println(filmEntity);
//        }
//        
//        FilmEntity filmEntity = new FilmEntity();
//        filmEntity.setTitle("test title 1");
//        filmEntity.setDescription("test description 1");
//        filmEntity.setRelease_year(2001);
//        filmEntity.setLanguage_id(1);
////        databaseActions.createFilm(filmEntity);
//        filmEntities = databaseActions.getFilmsByYear(filmEntity.getRelease_year());
//        for (FilmEntity filmEntity1 : filmEntities) {
//            System.out.println(filmEntity1);
//            filmEntity.setFilm_id(filmEntity1.getFilm_id());
//        }
//        
//        filmEntity.setTitle("test title 1.2");
//        databaseActions.updateFilm(filmEntity);
//        filmEntities = databaseActions.getFilmsByYear(filmEntity.getRelease_year());
//        for (FilmEntity filmEntity1 : filmEntities) {
//            System.out.println(filmEntity1);
//        }
//        
//        databaseActions.close();
        
        VertxDBActions vertxDBActions = new VertxDBActions();
        vertxDBActions.open();
        vertxDBActions.simpleQuery();
        vertxDBActions.close();

    }

}
