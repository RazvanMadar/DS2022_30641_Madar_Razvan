package ro.tuc.ds2020.services;

import ro.tuc.ds2020.dtos.ConsumptionDTO;

public interface ConsumptionService {
    Long saveConsumption(ConsumptionDTO consumptionDTO);
}
