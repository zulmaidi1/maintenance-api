package com.panjuak.maintanance.controllers;

import com.github.fge.jsonpatch.JsonPatch;
import com.panjuak.maintanance.ApiResponse;
import com.panjuak.maintanance.entities.Maintenance;
import com.panjuak.maintanance.services.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(
        path = "/api/maintenance",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class MaintenanceController {
    @Autowired
    private MaintenanceService maintenanceService;

    @GetMapping
    public ApiResponse<List<Maintenance>> get(){
        List<Maintenance> res = maintenanceService.all();

        return ApiResponse.<List<Maintenance>>builder()
                .success(true)
                .message("Success get all list maintenance")
                .data(res)
                .build();
    }

    @GetMapping(path = "/{id}")
    public ApiResponse<Maintenance> get(@PathVariable(name = "id") String id){
        Maintenance res = maintenanceService.get(id);

        return ApiResponse.<Maintenance>builder()
                .success(true)
                .message("Success get maintenance by ID")
                .data(res)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Maintenance> create(@RequestBody Maintenance request){
        Maintenance res = maintenanceService.create(request);
        return ApiResponse.<Maintenance>builder()
                .success(true)
                .message("Success create new maintenance")
                .data(res)
                .build();
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Maintenance> update(@PathVariable(name = "id") String id, @RequestBody Maintenance request){
        Maintenance res = maintenanceService.update(id, request);
        return ApiResponse.<Maintenance>builder()
                .success(true)
                .message("Success update maintenance by ID")
                .data(res)
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ApiResponse<String> delete(@PathVariable(name = "id") String id){
        maintenanceService.delete(id);
        return  ApiResponse.<String>builder()
                .success(true)
                .message("Success delete maintenance by ID")
                .build();
    }

    @GetMapping(path = "/active")
    public ApiResponse<Optional<Maintenance>> active(){
        Optional<Maintenance> res = maintenanceService.active();
        return ApiResponse.<Optional<Maintenance>>builder()
                .success(true)
                .message("Success get maintenance active")
                .data(res)
                .build();
    }
}
