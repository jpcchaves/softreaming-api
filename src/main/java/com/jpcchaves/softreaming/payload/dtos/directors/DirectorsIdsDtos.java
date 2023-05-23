package com.jpcchaves.softreaming.payload.dtos.directors;

import java.util.List;

public class DirectorsIdsDtos {
    private List<Long> directorIds;

    public DirectorsIdsDtos() {
    }

    public DirectorsIdsDtos(List<Long> directorIds) {
        this.directorIds = directorIds;
    }

    public List<Long> getDirectorIds() {
        return directorIds;
    }

    public void setDirectorIds(List<Long> directorIds) {
        this.directorIds = directorIds;
    }
}
