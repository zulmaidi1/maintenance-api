package com.panjuak.maintanance.controllers;

import com.panjuak.maintanance.ApiResponse;
import com.panjuak.maintanance.entities.Feature;
import com.panjuak.maintanance.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/features", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @GetMapping
    public ApiResponse<List<Feature>> gets(){
        List<Feature> res = featureService.gets();
        return ApiResponse.<List<Feature>>builder()
                .success(true)
                .message("Success get all features")
                .data(res)
                .build();
    }

    @GetMapping(path = "/{id}")
    public ApiResponse<Feature> get(@PathVariable(name = "id") String id){
        Feature res = featureService.get(id);
        return ApiResponse.<Feature>builder()
                .success(true)
                .message("Success get feature by ID")
                .data(res)
                .build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Feature> create(@RequestBody Feature request){
        Feature res = featureService.create(request);
        return ApiResponse.<Feature>builder()
                .success(true)
                .message("Success create feature")
                .data(res)
                .build();
    }

    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Feature> update(@PathVariable(name = "id") String id, @RequestBody Feature request) throws IllegalAccessException {
        Feature res = featureService.update(id, request);
        return ApiResponse.<Feature>builder()
                .success(true)
                .message("Success update feature by ID")
                .data(res)
                .build();
    }

    @DeleteMapping(path = "/{id}")
    public ApiResponse<Object> delete(@PathVariable(name = "id") String id){
        featureService.delete(id);
        return ApiResponse.builder()
                .success(true)
                .message("Success delete feature by ID")
                .build();
    }
}
