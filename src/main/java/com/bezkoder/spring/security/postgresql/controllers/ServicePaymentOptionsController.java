package com.bezkoder.spring.security.postgresql.controllers;

import com.bezkoder.spring.security.postgresql.models.Response;
import com.bezkoder.spring.security.postgresql.models.ServicePaymentOptions;
import com.bezkoder.spring.security.postgresql.repository.ServicePaymentOptionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/servicepaymentoptions")
public class ServicePaymentOptionsController {
    @Autowired
    ServicePaymentOptionsRepository servicePaymentOptionsRepository;

    @GetMapping("paymentoptionsofservice")
    public Response<List<ServicePaymentOptions>> get(@RequestParam Long service_id) {
        Response<List<ServicePaymentOptions>> res;
        try {
            List<ServicePaymentOptions> all = servicePaymentOptionsRepository.findAll();
            List<ServicePaymentOptions> filtered =  new ArrayList<>();
            for (ServicePaymentOptions option : all) {
                if (option.getService_id() == service_id)
                    filtered.add(option);
            }
            res = new Response<>(filtered, true, "");
        } catch (Exception e) {
            res = new Response<>(null, false, e.getMessage());
        }
        return res;
    }

    @PostMapping("addPaymentOptionToService")
    public Response<ServicePaymentOptions> create(@RequestBody ServicePaymentOptions servicePaymentOption) {
        Response<ServicePaymentOptions> res;
        try {
            servicePaymentOptionsRepository.save(servicePaymentOption);
            res = new Response<ServicePaymentOptions>(servicePaymentOption, true, "");
        } catch (Exception e) {
            res = new Response<>(null, false, e.getMessage());
        }
        return res;
    }
    @DeleteMapping("deleteAllOptionsFromService")
    public Response<String> delete(@RequestParam Long service_id) {
        Response<String> res = new Response<>(null, true, "succesfully removed all payment options from service " +
                "with id " + service_id);
        try {
            List<ServicePaymentOptions> all = servicePaymentOptionsRepository.findAll();
            for (ServicePaymentOptions option : all) {
                if (option.getService_id() == service_id)
                    servicePaymentOptionsRepository.delete(option);
            }
        } catch (Exception e) {
            res.success = false;
            res.info = e.getMessage();
        }
        return res;
    }
}
