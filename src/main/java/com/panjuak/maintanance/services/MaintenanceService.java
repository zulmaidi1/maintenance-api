package com.panjuak.maintanance.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.panjuak.maintanance.entities.Maintenance;
import org.springframework.data.web.JsonPath;

import java.util.List;
import java.util.Optional;

public interface MaintenanceService {
    List<Maintenance> all();
    Maintenance get(String id);
    Maintenance create(Maintenance request);
    Maintenance update(String id, Maintenance request);
    void delete(String id);

    Optional<Maintenance> active();
}
