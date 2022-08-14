package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.Role;
import com.bezkoder.spring.security.postgresql.models.ServiceDescription;
import com.bezkoder.spring.security.postgresql.models.ServiceFile;
import com.bezkoder.spring.security.postgresql.repository.ServiceFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/file")
public class FilesController {
    @Autowired
    ServiceFilesRepository serviceFilesRepository;

    @PostMapping("/linkFileWithService")
    public Response<ServiceFile> create(@RequestBody ServiceFile file) {
        Response<ServiceFile> res;
        try {
            serviceFilesRepository.save(file);
            res = new Response<>(file, true, "file with url " + file.getFile_url() + "was linked to" +
                    "service with id " + file.getService_id());
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }

    @DeleteMapping("/deleteAllFilesFromService")
    public Response<String> delete(@RequestParam Long service_id) {
        Response<String> res;
        try {
            List<ServiceFile> allServiceFiles = serviceFilesRepository.findAll();
            for (ServiceFile serviceFile : allServiceFiles) {
                serviceFilesRepository.delete(serviceFile);
            }
            res = new Response<>(null, true, "Successfully deleted all files form service with" +
                    "id " + service_id);
        } catch (Exception e) {
            res = new Response<>(null, false, e.getMessage());
        }
        return res;
    }
}
