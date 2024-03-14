package com.ismhac.jspace.model.primaryKey;

import com.ismhac.jspace.model.User;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OAuth2InfoId implements Serializable {
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;
}
