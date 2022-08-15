package com.bezkoder.spring.security.postgresql.services;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.ServicePaymentOptions;
import com.bezkoder.spring.security.postgresql.repository.ServicePaymentOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicePaymentOptionsService {
    @Autowired
    ServicePaymentOptionsRepository servicePaymentOptionsRepository;

    public Response<String> linkPaymentOptionsToService(List<ServicePaymentOptions> options) {
        Response<String> res;
        for (ServicePaymentOptions option : options) {
            servicePaymentOptionsRepository.save(option);
        }
        res = new Response<>(null, true, "");
        return res;
    }

    public List<ServicePaymentOptions> getServicePaymentOptions(Long service_id) {
        List<ServicePaymentOptions> res = new ArrayList<>();
        for (ServicePaymentOptions option : servicePaymentOptionsRepository.findAll()) {
            if (option.getService_id() == service_id)
                res.add(option);
        }
        return res;
    }
}
