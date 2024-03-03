package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.BaseEntity;
import com.ismhac.jspace.model.BaseUser;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class EmployeeID implements Serializable {
    @OneToOne
    @JoinColumn(name = "base_user_id", referencedColumnName = "id", nullable = false)
    private BaseUser baseUser;
}
