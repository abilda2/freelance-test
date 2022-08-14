package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.*;
import com.bezkoder.spring.security.postgresql.payload.request.CreateServiceRequest;
import com.bezkoder.spring.security.postgresql.repository.ServiceCategoryRepository;
import com.bezkoder.spring.security.postgresql.repository.ServiceRepository;
import com.bezkoder.spring.security.postgresql.repository.UserRepository;
import com.bezkoder.spring.security.postgresql.services.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    UserRepository userRepository;

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
    public Response<CreateServiceRequest> create(@RequestBody CreateServiceRequest request) {
        Response<CreateServiceRequest> response = new Response<>(null, true, "");
        try {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
            request.service.setUser_id(userRepository.findByUsername(username).get().getId());
            Service s = createService(request.service);
            filesService.LinkFilesWithService(request.serviceFileUrls, s.getId());
            System.out.println("Recent id is - " + s.getId() + " id of user - " + s.getUser_id());
        } catch (Exception e) {
            response.success = false;
            response.info = e.getMessage();
        }
        return response;
    }
    private Service createService(Service service) {
        serviceRepository.save(service);
        return service;
    }
}
