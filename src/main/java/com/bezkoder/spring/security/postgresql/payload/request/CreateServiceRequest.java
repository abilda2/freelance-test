package com.bezkoder.spring.security.postgresql.payload.request;

import com.bezkoder.spring.security.postgresql.models.Service;
import com.bezkoder.spring.security.postgresql.models.ServiceDescription;
import com.bezkoder.spring.security.postgresql.models.ServicePaymentOptions;

import java.util.List;

public class CreateServiceRequest {
    public List<ServicePaymentOptions> paymentOptions;
    public List<ServiceDescription> serviceDescriptions;
    public List<String> serviceFileUrls;
    public Service service;
}
