package com.jpcchaves.softreaming.services;

import com.jpcchaves.softreaming.entities.User;

public interface SecurityContextService {
    User getCurrentLoggedUser();
}
