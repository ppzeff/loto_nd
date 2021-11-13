package com.ppzeff.tinkoff.service;

import com.ppzeff.tinkoff.model.ModelRatesForGraph;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ModelService {
    List<ModelRatesForGraph> getRatesByData(int code, long period);
     String toStringByCode(int code);
}
