package com.hr_program.domain.region;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @Column(name = "region_id")
    private Long regionId;

    @Column(name = "region_name")
    private String regionName;

    @Builder
    public Region(Long regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }
}
