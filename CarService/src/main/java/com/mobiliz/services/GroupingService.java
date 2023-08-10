package com.mobiliz.services;

import com.mobiliz.dto.request.CreateGroupingRequestDto;
import com.mobiliz.dto.request.CreateSubGroupingRequestDto;
import com.mobiliz.dto.request.UpdateGroupingRequestDto;
import com.mobiliz.dto.response.FindAllWithTreeResponseDto;
import com.mobiliz.mapper.IGroupingMapper;
import com.mobiliz.repository.IGroupingRepository;
import com.mobiliz.repository.entity.Car;
import com.mobiliz.repository.entity.Grouping;
import com.mobiliz.utility.JwtTokenProvider;
import com.mobiliz.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GroupingService extends ServiceManager<Grouping,String> {
    private final IGroupingRepository groupingRepository;
    private final JwtTokenProvider tokenProvider;
    private final CarService carService;

    public GroupingService(IGroupingRepository groupingRepository, JwtTokenProvider tokenProvider, CarService carService) {
        super(groupingRepository);
        this.groupingRepository = groupingRepository;
        this.tokenProvider = tokenProvider;
        this.carService = carService;
    }
    public Grouping create(CreateGroupingRequestDto dto){
        carService.tokenRoleControls(dto.getToken());
        Grouping grouping = IGroupingMapper.INSTANCE.toGrouping(dto);
        return save(grouping);
    }
    public Boolean update(UpdateGroupingRequestDto dto){
        carService.tokenRoleControls(dto.getToken());
        Optional<Grouping> grouping = findById(dto.getGroupingId());
        Grouping groupingUpdated = IGroupingMapper.INSTANCE.toGrouping(grouping.get(),dto);
        update(groupingUpdated);
        return true;
    }
    public Grouping createSubGroup(CreateSubGroupingRequestDto dto){
        carService.tokenRoleControls(dto.getToken());
        Grouping grouping = IGroupingMapper.INSTANCE.toGrouping(dto);
        grouping.setIsSubGroup(true);
        grouping.setSubGroupId(dto.getSubGroupId());
        return save(grouping);
    }
    public Boolean delete(String token,String id){
        carService.tokenRoleControls(token);
        deleteById(id);
        return true;
    }

    /**
     * I try to make response like the tree format which in yours example case
     * also it is slow version, if I will have time, try to do a faster one
     * @return
     */
    public List<FindAllWithTreeResponseDto> findAllWithTree2() {
        List<FindAllWithTreeResponseDto> findAllWithTreeResponseDtoList = new ArrayList<>();
        List<Grouping> groupings = findAll();
        for (Grouping grouping : groupings) {
            FindAllWithTreeResponseDto responseDto = new FindAllWithTreeResponseDto();
            responseDto.setCountry(grouping.getCountry());
            responseDto.setFleet(grouping.getFleet());
            responseDto.setGroup(grouping.getGroup());
            List<String> plates = new ArrayList<>();
            for (String carId : grouping.getCarIds()) {
                Optional<Car> carOptional = carService.findById(carId);
                if (carOptional.isPresent()) {
                    plates.add(carOptional.get().getPlate());
                }
            }
            responseDto.setPlates(plates);
            if (grouping.getIsSubGroup()) {
                List<FindAllWithTreeResponseDto> subGroupList = new ArrayList<>();
                Optional<Grouping> subGroup = findById(grouping.getSubGroupId());
                if (subGroup.isPresent()) {
                    FindAllWithTreeResponseDto subGroupResponseDto = new FindAllWithTreeResponseDto();
                    subGroupResponseDto.setCountry(subGroup.get().getCountry());
                    subGroupResponseDto.setFleet(subGroup.get().getFleet());
                    subGroupResponseDto.setGroup(subGroup.get().getGroup());

                    List<String> subGroupPlates = new ArrayList<>();
                    for (String carId : subGroup.get().getCarIds()) {
                        Optional<Car> carOptional = carService.findById(carId);
                        if (carOptional.isPresent()) {
                            subGroupPlates.add(carOptional.get().getPlate());
                        }
                    }
                    subGroupResponseDto.setPlates(subGroupPlates);
                    subGroupList.add(subGroupResponseDto);
                }
                responseDto.setSubGroup(subGroupList);
            }
            findAllWithTreeResponseDtoList.add(responseDto);
        }
        return findAllWithTreeResponseDtoList;
    }
}
