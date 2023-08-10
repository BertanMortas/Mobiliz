package com.mobiliz.controller;

import com.mobiliz.dto.request.CreateGroupingRequestDto;
import com.mobiliz.dto.request.CreateSubGroupingRequestDto;
import com.mobiliz.dto.request.UpdateGroupingRequestDto;
import com.mobiliz.dto.response.FindAllWithTreeResponseDto;
import com.mobiliz.repository.entity.Grouping;
import com.mobiliz.services.GroupingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mobiliz.constant.ApiUrls.*;

@RestController
@RequestMapping(GROUPING)
@RequiredArgsConstructor
public class GroupingController {
    private final GroupingService groupingService;
    @PostMapping(CREATE)
    public ResponseEntity<Grouping> create(@RequestBody CreateGroupingRequestDto dto){
        return ResponseEntity.ok(groupingService.create(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody UpdateGroupingRequestDto dto){
        return ResponseEntity.ok(groupingService.update(dto));
    }
    @PostMapping(CREATE_SUB)
    public ResponseEntity<Grouping> create(@RequestBody CreateSubGroupingRequestDto dto){
        return ResponseEntity.ok(groupingService.createSubGroup(dto));
    }
    @DeleteMapping(DELETE_BY_ID+"/{token}/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable String token,@PathVariable String id){
        return ResponseEntity.ok(groupingService.delete(token,id));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Grouping>> findAll(){
        return ResponseEntity.ok(groupingService.findAll());
    }
    @GetMapping(FIND_ALL+"-tree")
    public ResponseEntity<List<FindAllWithTreeResponseDto>> findAllWithTree2(){
        return ResponseEntity.ok(groupingService.findAllWithTree2());
    }
}
