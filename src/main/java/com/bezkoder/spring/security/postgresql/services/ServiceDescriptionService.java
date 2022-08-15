package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.ServiceDescription;
import com.bezkoder.spring.security.postgresql.repository.ServiceDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceDescriptionService {
    @Autowired
    ServiceDescriptionRepository serviceDescriptionRepository;
    public void linkServiceDescriptions(List<ServiceDescription> serviceDescs) {
        for (ServiceDescription desc : serviceDescs)
            serviceDescriptionRepository.save(desc);
    }

    public List<ServiceDescription> getServiceDescriptions(Long serviceId) {
        List<ServiceDescription> all = serviceDescriptionRepository.findAll();
        List<ServiceDescription> filtered = new ArrayList<>();
        for (ServiceDescription desc : all)
            if (desc.getService_id() == serviceId)
                filtered.add(desc);
        return filtered;
    }
}
