package com.hr_program.api.controller.department;

import com.hr_program.api.ApiResponse;
import com.hr_program.api.service.department.DepartmentService;
import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/api/department/{departmentId}")
    public ApiResponse<DepartmentInfoResponse> getDepartmentInfo(@PathVariable Long departmentId) {
        return ApiResponse.ok(departmentService.getDepartmentInfo(departmentId));
    }

}
