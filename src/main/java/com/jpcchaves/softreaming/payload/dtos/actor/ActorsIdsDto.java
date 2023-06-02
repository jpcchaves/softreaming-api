package com.jpcchaves.softreaming.payload.dtos.actor;

import java.util.List;

public class ActorsIdsDto {
    private List<Long> actorsIds;

    public ActorsIdsDto() {
    }

    public ActorsIdsDto(List<Long> actorsIds) {
        this.actorsIds = actorsIds;
    }

    public List<Long> getActorsIds() {
        return actorsIds;
    }

    public void setActorsIds(List<Long> actorsIds) {
        this.actorsIds = actorsIds;
    }
}

