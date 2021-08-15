package com.softserve.entities;

public class ActorEntity {

    private int actor_id;
    private String first_name;
    private String last_name;
    private String last_update;

    public ActorEntity(int actor_id, String first_name, String last_name, String last_update) {
        this.actor_id = actor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.last_update = last_update;
    }

    public ActorEntity() {
        this.actor_id = -1;
        this.first_name = "default";
        this.last_name = "default";
        this.last_update = "default";
    }

    public int getActor_id() {
        return actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    @Override
    public String toString() {
        return "ActorEntity [actor_id=" + actor_id + ", first_name=" + first_name + ", last_name=" + last_name + "]";
    }

}
