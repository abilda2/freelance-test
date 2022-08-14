package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.ServiceDescription;
import com.bezkoder.spring.security.postgresql.repository.ServiceDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/servicedescription")
public class ServiceDescriptionController {
    @Autowired
    ServiceDescriptionRepository serviceDescriptionRepository;

    @GetMapping("/get")
//    @PreAuthorize("hasRole('MODERATOR')")
    public Response<List<ServiceDescription>> getServiceDescription(@RequestParam Long service_id) {
        Response<List<ServiceDescription>> response;
        try {
            List<ServiceDescription> descriptions = serviceDescriptionRepository.findAll();
            List<ServiceDescription> filtered = new ArrayList<>();
            for (ServiceDescription description : descriptions) {
                if (description.getService_id() == service_id)
                    filtered.add(description);
            }
            response = new Response<>(filtered, true, "");
            System.out.println(service_id);
        } catch (Exception e) {
            response = new Response<>(null, false, e.getMessage());
        }
        return response;
    }
    @PostMapping("/create")
    public Response<ServiceDescription> createServiceDescription(@RequestBody ServiceDescription serviceDesc) {
        Response<ServiceDescription> res = new Response<>(serviceDesc, true, "");
        try {
            serviceDescriptionRepository.save(serviceDesc);
        } catch (Exception e) {
            res.success = false;
            res.info = e.getMessage();
        }
        return res;
    }

}
