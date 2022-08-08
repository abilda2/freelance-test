package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.ServiceCategory;
import com.bezkoder.spring.security.postgresql.repository.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/servicecategory")
public class ServiceCategoryController {
    @Autowired
    ServiceCategoryRepository serviceCategoryRepository;

    @GetMapping("/all")
    public Response<List<ServiceCategory>> allAccess() {
        Response<List<ServiceCategory>> res;
        try {
            res = new Response<>(serviceCategoryRepository.findAll(), true);
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }
    @PostMapping("/create")
    @PreAuthorize("hasRole('MODERATOR')")
    public Response<ServiceCategory> create(@Valid @RequestBody ServiceCategory serviceCategory) {
        Response<ServiceCategory> res;
        try {
            if (serviceCategoryRepository.existsByName(serviceCategory.getName())) {
                throw new Exception("serviceCategory with name \"" + serviceCategory.getName() + "\" already exists");
            }
            serviceCategoryRepository.save(serviceCategory);
            res = new Response<>(serviceCategory, true, "new service category was succesfully created");
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }
    @PutMapping("/edit")
    @PreAuthorize("hasRole('MODERATOR')")
    public Response<ServiceCategory> edit(@RequestParam String oldName,@RequestParam String newName) {
        Response<ServiceCategory> res;
        ServiceCategory serviceCategory;
        try {
            if (!serviceCategoryRepository.existsByName(oldName)) {
                throw new Exception("serviceCategory with name \"" + oldName + "\"does not exist");
            }
            serviceCategory = serviceCategoryRepository.findByName(oldName).get();
            serviceCategory.setName(newName);
            serviceCategoryRepository.save(serviceCategory);
            res = new Response<>(serviceCategory, true,
                    "service category with name \"" + oldName +"\" was succesfully renamed to " + newName);
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('MODERATOR')")
    public Response<ServiceCategory> delete(@RequestParam String name) {
        Response<ServiceCategory> res;
        try {
            if (!serviceCategoryRepository.existsByName(name)) {
                throw new Exception("serviceCategory with name \"" + name + "\"does not exists");
            }
            ServiceCategory serviceCategory = new ServiceCategory(name);
            serviceCategoryRepository.delete(serviceCategory);
            res = new Response<>(serviceCategory, true,
                    "service category named \"" + name + "\"was succesfully deleted");
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }
}
