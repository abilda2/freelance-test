package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.payload.request.ServiceInfo;
import com.bezkoder.spring.security.postgresql.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ServiceInfoService {
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    ServiceDescriptionService serviceDescriptionService;
    @Autowired
    FilesService filesService;
    @Autowired
    ServicePaymentOptionsService servicePaymentOptionsService;

    public ServiceInfo getServiceInfo(@RequestParam Long serviceId) {
        ServiceInfo info = new ServiceInfo();

        info.setService(serviceRepository.findById(serviceId).get());
        info.setServiceDescriptions(serviceDescriptionService.getServiceDescriptions(serviceId));
        info.setServiceFileUrls(filesService.getServiceFileUrls(serviceId));
        info.setPaymentOptions(servicePaymentOptionsService.getServicePaymentOptions(serviceId));

        return info;
    }
}
