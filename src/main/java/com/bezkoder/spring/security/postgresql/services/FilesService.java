package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.ServiceFile;
import com.bezkoder.spring.security.postgresql.repository.ServiceFilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilesService {
    @Autowired
    ServiceFilesRepository serviceFilesRepository;

    public Response<String> linkFilesWithService(List<String> fileUrls, Long service_id) {
        Response<String> res;
        try {
            for (String fileUrl : fileUrls) {
                ServiceFile serviceFile = new ServiceFile();
                serviceFile.setService_id(service_id);
                serviceFile.setFile_url(fileUrl);
                serviceFilesRepository.save(serviceFile);
            }
            res = new Response<>(null, true, "success");
        } catch (Exception e) {
            String exceptionInfo = e.getMessage() + "\nStacktrace - " + e.getStackTrace();
            res = new Response<>(null, false, exceptionInfo);
        }
        return res;
    }

    public List<String> getServiceFileUrls(Long service_id) {
        List<String> res = new ArrayList<>();
        List<ServiceFile> all = serviceFilesRepository.findAll();
        for (ServiceFile file : all) {
            if (file.getService_id() == service_id)
                res.add(file.getFile_url());
        }
        return res;
    }

}
