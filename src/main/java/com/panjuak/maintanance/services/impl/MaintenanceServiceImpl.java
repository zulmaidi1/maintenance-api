package com.panjuak.maintanance.services.impl;

import com.panjuak.maintanance.entities.FcmPayload;
import com.panjuak.maintanance.entities.Maintenance;
import com.panjuak.maintanance.entities.MaintenanceFeature;
import com.panjuak.maintanance.repositories.MaintenanceFeatureRepository;
import com.panjuak.maintanance.repositories.MaintenanceRepository;
import com.panjuak.maintanance.services.FcmService;
import com.panjuak.maintanance.services.MaintenanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class MaintenanceServiceImpl implements MaintenanceService {
    @Autowired
    private MaintenanceRepository repository;
    @Autowired
    private MaintenanceFeatureRepository maintenanceFeatureRepository;

    @Autowired
    private FcmService fcmService;

    @Override
    public List<Maintenance> all() {
        return repository.findAll();
    }

    @Override
    public Maintenance get(String id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Maintenance create(Maintenance request) {
        Maintenance maintenance = repository.saveAndFlush(request);

        //Save maintenance feature
        List<MaintenanceFeature> features = new ArrayList<>();

        request.getFeatures().forEach(maintenanceFeature -> {
            maintenanceFeature.setMaintenance(maintenance);
            features.add(maintenanceFeature);
        });

        maintenance.setFeatures(maintenanceFeatureRepository.saveAllAndFlush(features));
        sendNotification(maintenance);
        return maintenance;
    }

    @Override
    public Maintenance update(String id, Maintenance request) {
        try {
            Maintenance maintenance = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            if(request.getFeatures() != null){
                maintenanceFeatureRepository.deleteAll(maintenance.getFeatures());
                List<MaintenanceFeature> features = new ArrayList<>();

                Maintenance finalMaintenance = maintenance;
                request.getFeatures().forEach(maintenanceFeature -> {
                    maintenanceFeature.setMaintenance(finalMaintenance);
                    features.add(maintenanceFeature);
                });

                maintenance.setFeatures(maintenanceFeatureRepository.saveAllAndFlush(features));
            }

            patcher(maintenance, request);
            maintenance = repository.saveAndFlush(maintenance);
            sendNotification(maintenance);
            return maintenance;
        } catch (IllegalAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void delete(String id) {
        Maintenance maintenance = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repository.delete(maintenance);
    }

    @Override
    public Optional<Maintenance> active() {
        return repository.findFirstForTodayOrActive();
    }

    private void patcher(Maintenance oldMaintenance, Maintenance request) throws IllegalAccessException {
        Field[] fieldsModels = Maintenance.class.getDeclaredFields();

        for (Field field : fieldsModels){
            field.setAccessible(true);
            Object value = field.get(request);
            if(value != null){
                field.set(oldMaintenance, value);
            }

            field.setAccessible(false);
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void scheduleMaintenance(){
        LocalDateTime now = LocalDateTime.now();
        Optional<Maintenance> maintenance = repository.findFirstForTodayOrActive();

        if(maintenance.isPresent()){
            if(maintenance.get().getStatus().equals("schedule")){
                maintenance.get().setStatus("process");
            }else if(maintenance.get().getStartTime() != null && maintenance.get().getStatus().equals("process")){
                log.info("This schedule to finish schedule");
                log.info(maintenance.get().getId());
                maintenance.get().setStatus("finish");
                fcmService.sendNotificationByTopic("maintenance", FcmPayload.builder()
                        .title("Maitenance Selesai")
                        .body("Maintenance telah selesai. Terima kasih telah menunggu.")
                        .data(maintenance.get())
                        .build());
            }

            log.info("Maintenance schedule is running");
            repository.saveAndFlush(maintenance.get());
        }
    }

    private void sendNotification(Maintenance maintenance){
        if(maintenance.getStatus().equals("process")){
            fcmService.sendNotificationByTopic(
                    "maintenance",
                    FcmPayload.builder()
                            .title(maintenance.getTitle())
                            .body(maintenance.getDescription())
                            .data(maintenance)
                            .build()
            );
        }else if(maintenance.getStatus().equals("finish")){
            fcmService.sendNotificationByTopic(
                    "maintenance",
                    FcmPayload.builder()
                            .title("Maitenance Selesai")
                            .body("Maintenance telah selesai. Terima kasih telah menunggu.")
                            .data(maintenance)
                            .build()
            );
        }
    }
}
