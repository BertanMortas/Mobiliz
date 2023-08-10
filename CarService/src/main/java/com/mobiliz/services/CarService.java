package com.mobiliz.services;

import com.mobiliz.dto.request.CreateCarRequestDto;
import com.mobiliz.dto.request.DeleteCarRequestDto;
import com.mobiliz.dto.request.UpdateCarRequestDto;
import com.mobiliz.exception.CarManagerException;
import com.mobiliz.exception.ErrorType;
import com.mobiliz.mapper.ICarMapper;
import com.mobiliz.repository.ICarRepository;
import com.mobiliz.repository.entity.Car;
import com.mobiliz.repository.entity.enums.EStatus;
import com.mobiliz.repository.entity.view.CarFindAllView;
import com.mobiliz.utility.JwtTokenProvider;
import com.mobiliz.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService extends ServiceManager<Car,String> {
    private final ICarRepository carRepository;
    private final JwtTokenProvider tokenProvider;

    public CarService(ICarRepository carRepository, JwtTokenProvider tokenProvider) {
        super(carRepository);
        this.carRepository = carRepository;
        this.tokenProvider = tokenProvider;
    }
    public Boolean create(CreateCarRequestDto dto){
        Long companyId = tokenRoleControls(dto.getToken());
        Car car = ICarMapper.INSTANCE.toCar(dto);
        car.setCompanyId(companyId);
        save(car);
        return true;
    }
    public Car update(UpdateCarRequestDto dto){
        Long companyId = tokenRoleControls(dto.getToken());
        Optional<Car> car = findById(dto.getCarId());
        if (car.get().getCompanyId().equals(companyId) && car.isPresent()) {
            Car updatedCar = ICarMapper.INSTANCE.fromUpdateCarRequestDtoToCar(car.get(),dto);
            return update(updatedCar);
        }
        throw new CarManagerException(ErrorType.CAR_NOT_FOUND);
    }
    public Boolean delete(DeleteCarRequestDto dto){
        Long companyId = tokenRoleControls(dto.getToken());
        Optional<Car> car = findById(dto.getCarId());
        if (car.get().getCompanyId().equals(companyId) && car.isPresent()) {
            car.get().setStatus(EStatus.DELETED);
            save(car.get());
            return true;
        }
        throw new CarManagerException(ErrorType.CAR_NOT_FOUND);
    }
    public List<CarFindAllView> findAllWhereStatusActive() {
        return carRepository.findActiveCarFields();
    }
    public Long tokenRoleControls(String token){
        Optional<Long> companyId = tokenProvider.getCompanyIdFromToken(token);
        if (companyId.isEmpty()) {
            throw new CarManagerException(ErrorType.INVALID_TOKEN);
        }
        List<String> roles = tokenProvider.getRolesFromToken(token);
        if (!roles.contains("COMPANY_ADMIN")) {
            throw new CarManagerException(ErrorType.INVALID_ROLE);
        }
        return companyId.get();
    }
}
