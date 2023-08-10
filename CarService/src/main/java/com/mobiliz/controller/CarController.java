package com.mobiliz.controller;
import com.mobiliz.dto.request.CreateCarRequestDto;
import com.mobiliz.dto.request.DeleteCarRequestDto;
import com.mobiliz.dto.request.UpdateCarRequestDto;
import com.mobiliz.repository.entity.Car;
import com.mobiliz.repository.entity.view.CarFindAllView;
import com.mobiliz.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.mobiliz.constant.ApiUrls.*;

@RestController
@RequestMapping(CAR)
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> create(@RequestBody @Valid CreateCarRequestDto dto){
        return ResponseEntity.ok(carService.create(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Car> update(@RequestBody @Valid UpdateCarRequestDto dto){
        return ResponseEntity.ok(carService.update(dto));
    }
    @PutMapping(DELETE_BY_ID)
    public ResponseEntity<Boolean> delete(@RequestBody @Valid DeleteCarRequestDto dto){
        return ResponseEntity.ok(carService.delete(dto));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<CarFindAllView>> findAll(){
        return ResponseEntity.ok(carService.findAllWhereStatusActive());
    }
}
