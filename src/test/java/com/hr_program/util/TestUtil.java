package com.hr_program.util;

import com.hr_program.domain.country.Country;
import com.hr_program.domain.department.Department;
import com.hr_program.domain.employee.Employee;
import com.hr_program.domain.job.Job;
import com.hr_program.domain.jobHistory.JobHistory;
import com.hr_program.domain.location.Location;
import com.hr_program.domain.region.Region;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class TestUtil {

    public static Employee createEmployee(Long id, Job job) {
        return Employee.builder()
                .id(id)
                .lastName("kim")
                .email("kim")
                .hireDate(new Date())
                .salary(new BigDecimal(1000.0))
                .job(job)
                .build();
    }

    public static Employee createEmployee(Long id, Department department, Job job) {
        return Employee.builder()
                .id(id)
                .lastName("kim")
                .email("kim")
                .hireDate(new Date())
                .salary(new BigDecimal(1000.0))
                .department(department)
                .job(job)
                .build();
    }

    public static Employee createEmployee(Long id, Department department, Job job, Double salary) {
        return Employee.builder()
                .id(id)
                .lastName("kim")
                .email("kim")
                .hireDate(new Date())
                .salary(new BigDecimal(salary))
                .department(department)
                .job(job)
                .build();
    }

    public static Department createDepartment(Long departmentId, String departmentName) {
        return Department.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .build();
    }

    public static Department createDepartment(Long departmentId, String departmentName, Location location) {
        return Department.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .location(location)
                .build();
    }

    public static Job createJob(String jobId, String jobTitle) {
        return Job.builder()
                .jobId(jobId)
                .jobTitle(jobTitle)
                .build();
    }

    public static Region createRegion() {
        return Region.builder()
                .regionId(0L)
                .regionName("Earth")
                .build();
    }

    public static Country createCountry(Region region) {
        return Country.builder()
                .countryId("KO")
                .region(region)
                .countryName("Korea")
                .build();
    }

    public static Location createLocation(Country country) {
        return Location.builder()
                .locationId(99L)
                .city("Seoul")
                .country(country)
                .build();
    }

    public static Date getOneYearAgo() {
        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -1);

        return calendar.getTime();
    }

    public static JobHistory createJobHistory(Employee employee, Date startDate, Date endDate, Job job, Department department) {
        return JobHistory.builder()
                .employee(employee)
                .startDate(startDate)
                .endDate(endDate)
                .job(job)
                .department(department)
                .build();
    }
}
