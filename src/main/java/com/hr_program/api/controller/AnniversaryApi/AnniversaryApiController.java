package com.hr_program.api.controller.AnniversaryApi;

import com.hr_program.api.ApiResponse;
import com.hr_program.api.controller.AnniversaryApi.request.AnniversaryInfoRequest;
import com.hr_program.api.service.AnniversaryApI.AnniversaryApiService;
import com.hr_program.api.service.AnniversaryApI.response.AnniversaryInfoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AnniversaryApiController {

    private final AnniversaryApiService anniversaryApiService;

    @GetMapping("/api/anniversary")
    public ApiResponse<List<AnniversaryInfoResponse>> getAnniversaryInfo(@Valid AnniversaryInfoRequest request) {
        return ApiResponse.ok(anniversaryApiService.getAnniversaryInfo(request.year(), request.month()));
    }
}
