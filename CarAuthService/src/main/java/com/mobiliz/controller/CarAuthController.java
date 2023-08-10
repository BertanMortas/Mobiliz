package com.mobiliz.controller;

import com.mobiliz.service.CarAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.mobiliz.constant.ApiUrls.CAR_AUTH;

@RestController
@RequestMapping(CAR_AUTH)
@RequiredArgsConstructor
public class CarAuthController {
    private final CarAuthService carAuthService;
}
