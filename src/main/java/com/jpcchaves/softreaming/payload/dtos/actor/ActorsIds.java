package com.jpcchaves.softreaming.payload.dtos.actor;

import java.util.List;

public class ActorsIds {
    private List<Long> actorsIds;

    public ActorsIds() {
    }

    public ActorsIds(List<Long> actorsIds) {
        this.actorsIds = actorsIds;
    }

    public List<Long> getActorsIds() {
        return actorsIds;
    }

    public void setActorsIds(List<Long> actorsIds) {
        this.actorsIds = actorsIds;
    }
}

