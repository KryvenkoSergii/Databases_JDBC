package com.softserve.tests;

import java.io.IOException;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.softserve.dao.DatabaseActions;
import com.softserve.dao.VertxDBActions;
import com.softserve.entities.ActorEntity;
import com.softserve.entities.FilmEntity;

public class AppTestVertx {

    private VertxDBActions vertxDBActions;

    @BeforeClass
    public void openConnection() {
        vertxDBActions = new VertxDBActions();
        vertxDBActions.connect();
    }

    @AfterClass
    public void closeConnection() throws Exception {
        vertxDBActions.close();
    }

    @Test
    public void checkGetFilmsByYear() throws Exception {
        List<FilmEntity> filmEntities = vertxDBActions.getFilmsByYear(2006);
        for (FilmEntity filmEntity : filmEntities) {
//            System.out.println(filmEntity);
        }
        Assert.assertTrue(filmEntities.size() > 0, "there aren't films from 2006");
    }

    @Test
    public void checkGetActorsByFilmName() throws Exception {
        List<ActorEntity> actorEntities = vertxDBActions.getActorsByFilmName("BIRD INDEPENDENCE");
        for (ActorEntity actorEntity : actorEntities) {
//            System.out.println(actorEntity);
        }
        Assert.assertTrue(actorEntities.size() > 0, "there aren't actors for this film or there isn't a film with this name");
    }

    @Test
    public void checkGetFilmsByActorName() throws Exception {
        List<FilmEntity> filmEntities = vertxDBActions.getFilmsByActorName("RICHARD", "PENN");
        for (FilmEntity filmEntity : filmEntities) {
//            System.out.println(filmEntity);
        }
        Assert.assertTrue(filmEntities.size() > 0, "there aren't films with this actor");
    }
}
