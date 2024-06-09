package com.ismhac.jspace.model;

import com.ismhac.jspace.model.primaryKey.UserNotificationId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user_notification")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNotification extends BaseEntity{
    @EmbeddedId
    UserNotificationId id;
    boolean read;
}
