package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.Role;
import com.bezkoder.spring.security.postgresql.models.ServiceCategory;
import com.bezkoder.spring.security.postgresql.repository.RoleRepository;
import com.bezkoder.spring.security.postgresql.repository.ServiceCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/all")
    public Response<List<Role>> allAccess() {
        Response<List<Role>> res;
        try {
            res = new Response<>(roleRepository.findAll(), true);
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }

    @PostMapping("/create")
    public Response<Role> create(@Valid @RequestBody Role role) {
        Response<Role> res;
        try {
            roleRepository.save(role);
            res = new Response<>(role, true, "new role was succesfully created");
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }

}
