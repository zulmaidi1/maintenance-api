package com.panjuak.maintanance.services;

import com.panjuak.maintanance.entities.Feature;

import java.util.List;

public interface FeatureService {
    List<Feature> gets();
    Feature create(Feature request);
    Feature get(String id);
    Feature update(String id, Feature request) throws IllegalAccessException;
    void delete(String id);
}
