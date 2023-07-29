package com.hr_program.domain.country;

import com.hr_program.domain.region.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "countries")
public class Country {

    @Id
    @Column(name = "country_id")
    private String countryId;

    @Column(name = "country_name")
    private String countryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", referencedColumnName = "region_id")
    private Region region;
}
