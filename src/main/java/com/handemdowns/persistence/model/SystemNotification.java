package com.handemdowns.persistence.model;

import com.handemdowns.persistence.model.converter.SystemNotificationTypeConverter;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "system_notification", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"type", "details", "createDate"})
@ToString
@Builder(builderMethodName = "objectBuilder")
public class SystemNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Convert(converter = SystemNotificationTypeConverter.class)
    @Column(name = "type", nullable = false)
    private SystemNotificationType type;

    @Column(name = "details", nullable = false)
    private String details;

	@Column(name = "create_date", nullable = false)
	private Date createDate;

	public static SystemNotificationBuilder builder(SystemNotificationType type, String details) {
		return objectBuilder()
				.type(type)
				.details(details)
				.createDate(new Date());
	}
}