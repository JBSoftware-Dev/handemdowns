package com.handemdowns.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "password_reset_token", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"token", "user", "expiryDate"})
@ToString
@Builder(builderMethodName = "objectBuilder")
public class PasswordResetToken {
	private static final int EXPIRATION = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "token", nullable = false)
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@Column(name = "expiry_date", nullable = false)
	private Date expiryDate;
	
	public void updateToken(String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	private static Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	public static PasswordResetTokenBuilder builder(String token, User user) {
		return objectBuilder()
				.token(token)
				.user(user)
				.expiryDate(calculateExpiryDate(EXPIRATION));
	}
}