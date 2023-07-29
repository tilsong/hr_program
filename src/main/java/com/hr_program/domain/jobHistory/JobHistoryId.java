package com.hr_program.domain.jobHistory;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class JobHistoryId implements Serializable {
    private Long employee;
    private Date startDate;
}
