package com.handemdowns.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.handemdowns.persistence.model.converter.StatusConverter;
import lombok.*;
import org.unbescape.html.HtmlEscape;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "ad", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"title", "description", "location"})
@ToString(exclude={"images", "usersWatching"})
@Builder(builderMethodName = "objectBuilder")
@JsonIgnoreProperties({"images", "usersWatching"})
public class Ad {
    private static final int EXPIRATION = 60 * 24 * 90;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

	@Column(name = "title", nullable = false)
    private String title;
	
	@Column(name = "description", length = 1000, nullable = false)
    private String description;
	
	@ManyToOne(optional = false)
    @JoinColumn(name = "category", nullable = false)
    private Category category;

	@ManyToOne(optional = false)
    @JoinColumn(name = "location", nullable = false)
    private Location location;

	@Column(name = "postal_code")
	private String postalCode;
	
	@Column(name = "will_deliver", nullable = false)
    private Boolean willDeliver;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email", updatable = false)
	private String email;

	@Column(name = "status")
	@Convert(converter = StatusConverter.class)
	private Status status;

	@Column(name = "create_date", nullable = false)
	private Date createDate;

	@Column(name = "post_date")
	private Date postDate;

    @Column(name = "hold_date")
    private Date holdDate;

	@Column(name = "expiry_date", nullable = false)
    private Date expiryDate;

	@Column(name = "token", nullable = false)
	private String token;

	@OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<Image> images;

	@ManyToMany(mappedBy = "watchlist")
	private Collection<User> usersWatching;

	@Column(name = "report_count")
	private int reportCount;

	public void addImage(Image image) {
		this.images.add(image);
		image.setAd(this);
	}

	public void removeImage(Image image) {
		this.images.remove(image);
		image.setAd(null);
	}

	public boolean isAnnonymous() {
		return getUser() == null;
	}

	public void incrementReportCount() {
		this.reportCount++;
	}

	public Image getFirstImage() {
		Optional<Image> adImage = getImages().stream().findFirst();
		return adImage.isPresent() ? adImage.get() : null;
	}

    @SuppressWarnings("unused")// Used by Thymeleaf
	public Long dateToEpoch(Date date) {
        return date.getTime() / 1000;
    }

	@SuppressWarnings("unused")// Used by Thymeleaf
    public String nl2br(String string) {
        return HtmlEscape.escapeHtml4Xml(string).replace(System.getProperty("line.separator"), "<br />");
    }

	private static Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

	public static AdBuilder builder(User user, String token) {
		return objectBuilder()
				.user(user)
				.token(token)
				.images(new ArrayList<>())
				.createDate(new Date())
				.expiryDate(calculateExpiryDate(EXPIRATION))
				.reportCount(0);
	}
}