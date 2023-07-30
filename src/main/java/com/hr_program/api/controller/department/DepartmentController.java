package com.hr_program.api.controller.department;

import com.hr_program.api.ApiResponse;
import com.hr_program.api.controller.department.request.RaiseSalariesForDepartmentRequest;
import com.hr_program.api.service.department.DepartmentService;
import com.hr_program.api.service.department.response.DepartmentInfoResponse;
import com.hr_program.api.service.department.response.RaiseSalariesForDepartmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/api/department/{departmentId}")
    public ApiResponse<DepartmentInfoResponse> getDepartmentInfo(@PathVariable Long departmentId) {
        return ApiResponse.ok(departmentService.getDepartmentInfo(departmentId));
    }

    @PostMapping("/api/department/raiseSalariesForDepartment")
    public ApiResponse<List<RaiseSalariesForDepartmentResponse>> raiseSalariesForDepartment(@Valid @RequestBody RaiseSalariesForDepartmentRequest request) {
        return ApiResponse.ok(departmentService.raiseSalariesForDepartment(request.getDepartmentId(), request.getRaiseRate()));
    }
}
