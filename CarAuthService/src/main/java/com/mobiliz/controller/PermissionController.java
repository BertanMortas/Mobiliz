package com.mobiliz.controller;

import com.mobiliz.dto.request.CreatePermissionRequestDto;
import com.mobiliz.dto.request.DeletePermissionRequestDto;
import com.mobiliz.dto.request.UpdatePermissionRequestDto;
import com.mobiliz.repository.entity.Permission;
import com.mobiliz.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mobiliz.constant.ApiUrls.*;

@RestController
@RequestMapping(PERMISSION)
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Permission>> findAll(){
        return ResponseEntity.ok(permissionService.findAll());
    }
    @PostMapping(CREATE)
    public ResponseEntity<Permission> create(CreatePermissionRequestDto dto){
        return ResponseEntity.ok(permissionService.create(dto));
    }
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Boolean> delete(DeletePermissionRequestDto dto){
        return ResponseEntity.ok(permissionService.delete(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Permission> update(UpdatePermissionRequestDto dto){
        return ResponseEntity.ok(permissionService.update(dto));
    }
}
