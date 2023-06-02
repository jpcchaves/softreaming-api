package com.jpcchaves.softreaming.payload.dtos.directors;

import java.util.List;

public class DirectorsIdsDtos {
    private List<Long> directorsIds;

    public DirectorsIdsDtos() {
    }

    public DirectorsIdsDtos(List<Long> directorsIds) {
        this.directorsIds = directorsIds;
    }

    public List<Long> getDirectorsIds() {
        return directorsIds;
    }

    public void setDirectorsIds(List<Long> directorsIds) {
        this.directorsIds = directorsIds;
    }
}
