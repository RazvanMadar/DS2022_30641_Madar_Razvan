package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.services.ConsumptionService;

@RestController
@RequestMapping("/consumptions")
public class ConsumptionController {
    private final ConsumptionService consumptionService;

    public ConsumptionController(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    @PostMapping
    public ResponseEntity<?> createConsumption(@RequestBody ConsumptionDTO consumptionDTO) {
        Long id = consumptionService.saveConsumption(consumptionDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }
}
