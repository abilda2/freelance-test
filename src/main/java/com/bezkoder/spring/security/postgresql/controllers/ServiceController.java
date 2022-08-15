package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.*;
import com.bezkoder.spring.security.postgresql.payload.request.ServiceInfo;
import com.bezkoder.spring.security.postgresql.repository.ServiceRepository;
import com.bezkoder.spring.security.postgresql.repository.UserRepository;
import com.bezkoder.spring.security.postgresql.services.FilesService;
import com.bezkoder.spring.security.postgresql.services.ServiceDescriptionService;
import com.bezkoder.spring.security.postgresql.services.ServiceInfoService;
import com.bezkoder.spring.security.postgresql.services.ServicePaymentOptionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/service")
public class ServiceController {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    FilesService filesService;
    @Autowired
    ServicePaymentOptionsService servicePaymentOptionsService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ServiceDescriptionService serviceDescriptionService;
    @Autowired
    ServiceInfoService serviceInfoService;
    @GetMapping("/all")
    public Response<List<Service>> allAccess() {
        Response<List<Service>> res;
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String username = userDetails.getUsername();
        try {
            res = new Response<>(serviceRepository.findAll(), true, username);
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR')")
    public Response<ServiceInfo> create(@RequestBody ServiceInfo request) {
        Response<ServiceInfo> response = new Response<>(null, true, "");
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            request.service.setUser_id(userRepository.findByUsername(username).get().getId());
            Service s = createService(request.service);
            filesService.linkFilesWithService(request.serviceFileUrls, s.getId());
            for (ServicePaymentOptions option : request.paymentOptions) {
                option.setService_id(s.getId());
            }
            for (ServiceDescription description : request.serviceDescriptions) {
                description.setService_id(s.getId());
            }
            servicePaymentOptionsService.linkPaymentOptionsToService(request.paymentOptions);
            serviceDescriptionService.linkServiceDescriptions(request.serviceDescriptions);
            response = new Response<>(request, true, "succesfully created");
        } catch (Exception e) {
            response.success = false;
            response.info = e.getMessage();
        }
        return response;
    }
    @GetMapping("/servicesofcategory")
    @PreAuthorize("hasRole('MODERATOR')")
    public Response<List<ServiceInfo>> getServicesOfCategory(@RequestParam Long serviceCategoryId) {
        Response<List<ServiceInfo>> res;
        try {
            List<ServiceInfo> info = new ArrayList<>();
            for (Service service : serviceRepository.findAll()) {
                if (service.getService_category_id() == serviceCategoryId) {
                    info.add(serviceInfoService.getServiceInfo(service.getId()));
                }
            }
            res = new Response<>(info, true, "");
        } catch (Exception e) {
            res = new Response<>(null, false, e.getMessage());
        }
        return res;
    }
    @GetMapping("/serviceinfo")
    @PreAuthorize("hasRole('MODERATOR')")
    public Response<ServiceInfo> getServiceInfo(@RequestParam Long serviceId) {
        Response<ServiceInfo> res;

        try {
            res = new Response<>(serviceInfoService.getServiceInfo(serviceId), true, "");
        } catch (Exception e) {
            res = new Response<>(null, false, e.getMessage());
        }

        return res;
    }
    private Service createService(Service service) {
        serviceRepository.save(service);
        return service;
    }
}
