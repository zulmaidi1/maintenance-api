package com.panjuak.maintanance.services.impl;

import com.panjuak.maintanance.entities.Feature;
import com.panjuak.maintanance.entities.Maintenance;
import com.panjuak.maintanance.repositories.FeatureRepository;
import com.panjuak.maintanance.services.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;

@Service
public class FeatureServiceImpl implements FeatureService {
    @Autowired
    private FeatureRepository repository;
    @Override
    public List<Feature> gets() {
        return repository.findAll();
    }

    @Override
    public Feature create(Feature request) {
        return repository.saveAndFlush(request);
    }

    @Override
    public Feature get(String id) {
        return getFeature(id);
    }

    @Override
    public Feature update(String id, Feature request) throws IllegalAccessException {
        Feature feature = getFeature(id);
        patcher(feature, request);
        return repository.saveAndFlush(feature);
    }

    @Override
    public void delete(String id) {
        Feature feature = getFeature(id);
        repository.delete(feature);
    }

    Feature getFeature(String id){
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void patcher(Feature existData, Feature request) throws IllegalAccessException {
        Field[] fieldsModels = Feature.class.getDeclaredFields();

        for (Field field : fieldsModels){
            field.setAccessible(true);
            Object value = field.get(request);
            if(value != null){
                field.set(existData, value);
            }

            field.setAccessible(false);
        }
    }
}
