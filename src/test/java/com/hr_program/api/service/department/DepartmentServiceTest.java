package com.hr_program.api.service.department;

import com.hr_program.api.service.department.response.RaiseSalariesForDepartmentResponse;
import com.hr_program.domain.country.Country;
import com.hr_program.domain.country.CountryRepository;
import com.hr_program.domain.department.Department;
import com.hr_program.domain.department.DepartmentRepository;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import com.hr_program.domain.employee.Employee;
import com.hr_program.domain.employee.EmployeeRepository;
import com.hr_program.domain.employee.exception.InvalidSalaryRaiseRateException;
import com.hr_program.domain.job.Job;
import com.hr_program.domain.job.JobRepository;
import com.hr_program.domain.location.Location;
import com.hr_program.domain.location.LocationRepository;
import com.hr_program.domain.region.Region;
import com.hr_program.domain.region.RegionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.hr_program.util.TestUtil.*;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LocationRepository locationRepository;

    @DisplayName("부서 아이디를 통해 부서 및 위치 정보를 조회한다.")
    @Test
    void findDepartmentAndLocationInfoByDepartmentId() {
        // given
        Region region = createRegion();
        Region saveRegion = regionRepository.save(region);

        Country country = createCountry(saveRegion);
        Country saveCountry = countryRepository.save(country);

        Location location = createLocation(saveCountry);
        Location saveLocation = locationRepository.save(location);

        Department department = createDepartment(1L, "Customer", saveLocation);
        Department save = departmentRepository.save(department);

        // when
        var departmentInfo = departmentService.getDepartmentInfo(save.getDepartmentId());

        // then
        assertThat(departmentInfo.departmentId()).isEqualTo(department.getDepartmentId());
        assertThat(departmentInfo.location().id()).isEqualTo(saveLocation.getLocationId());
    }

    @DisplayName("존재하지 않는 부서 아이디를 통해 부서 및 위치 정보를 조회하면 실패한다.")
    @Test
    void findDepartmentAndLocationInfoByNoexistsDepartmentId() {
        // given
        Long departmentId = 0L;

        // when then
        assertThatThrownBy(() -> departmentService.getDepartmentInfo(departmentId))
                .isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("존재하지 않는 부서 Id입니다.");
    }

    @DisplayName("부서 아이디를 통해 부서 직원들의 급여를 특정 비율로 인상한다.")
    @Test
    void raiseSalariesForDepartment() {
        // given
        Double raiseRate = 2.5;

        Department dep = createDepartment(1L, "Web");
        departmentRepository.save(dep);

        Job job = createJob("WebProg", 105.0);
        jobRepository.save(job);

        Employee employee1 = createEmployee(1L, dep, job, 90.0);
        Employee employee2 = createEmployee(2L, dep, job, 100.0);
        employeeRepository.saveAll(List.of(employee1, employee2));

        // when
        List<RaiseSalariesForDepartmentResponse> responses = departmentService.raiseSalariesForDepartment(dep.getDepartmentId(), raiseRate);

        // then
        assertThat(responses).hasSize(2)
                .extracting("employeeId", "raisedSalary", "isMaxSalary")
                .containsExactlyInAnyOrder(
                        tuple(1L, new BigDecimal(92.25).setScale(3), false),
                        tuple(2L, new BigDecimal(102.5).setScale(3), false)
                );
    }

    @DisplayName("급여 인상 비율이 0보다 크고 100 보다 작지 않은 값이면 예외가 발생한다.")
    @Test
    void raiseSalaryWithInvalidRaiseRate() {
        // given
        Double raiseRate = 110.0;

        Department dep = createDepartment(1L, "Web");
        departmentRepository.save(dep);

        Job job = createJob("WebProg", 105.0);
        jobRepository.save(job);

        Employee employee = createEmployee(2L, dep, job, 100.0);
        employeeRepository.save(employee);

        // when then
        assertThatThrownBy(() -> departmentService.raiseSalariesForDepartment(dep.getDepartmentId(), raiseRate))
                .isInstanceOf(InvalidSalaryRaiseRateException.class)
                .hasMessage("급여 인상률이 유효하지 않은 값입니다.\n0 보다 크고 100 보다 작은 값을 입력해주세요.");
    }

    private static Job createJob(String jobName, Double maxSalary) {
        return Job.builder()
                .jobId(jobName)
                .jobTitle(jobName)
                .maxSalary(new BigDecimal(maxSalary))
                .build();
    }

}