package com.bezkoder.spring.security.postgresql.payload.request;

import com.bezkoder.spring.security.postgresql.models.Service;
import com.bezkoder.spring.security.postgresql.models.ServiceDescription;
import com.bezkoder.spring.security.postgresql.models.ServicePaymentOptions;

import java.util.List;

public class ServiceInfo {
    public List<ServicePaymentOptions> paymentOptions;
    public List<ServiceDescription> serviceDescriptions;
    public List<String> serviceFileUrls;
    public Service service;

    public List<ServicePaymentOptions> getPaymentOptions() {
        return paymentOptions;
    }

    public void setPaymentOptions(List<ServicePaymentOptions> paymentOptions) {
        this.paymentOptions = paymentOptions;
    }

    public List<ServiceDescription> getServiceDescriptions() {
        return serviceDescriptions;
    }

    public void setServiceDescriptions(List<ServiceDescription> serviceDescriptions) {
        this.serviceDescriptions = serviceDescriptions;
    }

    public List<String> getServiceFileUrls() {
        return serviceFileUrls;
    }

    public void setServiceFileUrls(List<String> serviceFileUrls) {
        this.serviceFileUrls = serviceFileUrls;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
