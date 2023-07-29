package com.hr_program.api.service.department.response;

import com.hr_program.domain.location.Location;
import lombok.Builder;

@Builder
public record LocationInfo (
        Long id,
        String streetAddress,
        String postalCode,
        String city,
        String stateProvince,
        String countryId,
        String countryName,
        Long regionId,
        String regionName
) {
    public static LocationInfo from(Location location) {

        /** Locations, Countries, Regions 는 Not Null 연관 관계를 가지고 있으므로 아래 if 문이 성립될 수 있습니다.*/
        if (location == null) {
            return null;
        }

        return LocationInfo.builder()
                .id(location.getLocationId())
                .streetAddress(location.getStreetAddress())
                .postalCode(location.getPostalCode())
                .city(location.getCity())
                .stateProvince(location.getStateProvince())
                .countryId(location.getCountry().getCountryId())
                .countryName(location.getCountry().getCountryName())
                .regionId(location.getCountry().getRegion().getRegionId())
                .regionName(location.getCountry().getRegion().getRegionName())
                .build();
    }
}

