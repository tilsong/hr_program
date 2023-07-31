package com.hr_program.domain.department;

import com.hr_program.domain.country.Country;
import com.hr_program.domain.country.CountryRepository;
import com.hr_program.domain.department.exception.DepartmentNotFoundException;
import com.hr_program.domain.location.Location;
import com.hr_program.domain.location.LocationRepository;
import com.hr_program.domain.region.Region;
import com.hr_program.domain.region.RegionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static com.hr_program.util.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

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
        Long departmentId = 10L;

        Region region = createRegion();
        Region saveRegion = regionRepository.save(region);

        Country country = createCountry(saveRegion);
        Country saveCountry = countryRepository.save(country);

        Location location = createLocation(saveCountry);
        Location saveLocation = locationRepository.save(location);

        Department department = createDepartment(departmentId, saveLocation);
        departmentRepository.save(department);

        // when
        Department findDepartment = departmentRepository.findDepartmentById(departmentId)
                .orElseThrow(null);

        // then
        assertThat(findDepartment.getDepartmentId()).isEqualTo(departmentId);
        assertThat(findDepartment.getLocation().getLocationId()).isEqualTo(saveLocation.getLocationId());
    }

    @DisplayName("존재하지 않는 부서 아이디를 통해 부서 및 위치 정보를 조회하면 실패한다.")
    @Test
    void findDepartmentAndLocationInfoByNoexistsDepartmentId() {
        // given
        Long departmentId = 0L;

        // when then
        assertThatThrownBy(() -> departmentRepository.findDepartmentById(departmentId)
                .orElseThrow(() -> new DepartmentNotFoundException(departmentId)))
                .isInstanceOf(DepartmentNotFoundException.class)
                .hasMessage("존재하지 않는 부서 Id입니다.");
    }

    private Department createDepartment(Long departmentId, Location location) {
        return Department.builder()
                .departmentId(departmentId)
                .departmentName("Web")
                .location(location)
                .build();
    }
}