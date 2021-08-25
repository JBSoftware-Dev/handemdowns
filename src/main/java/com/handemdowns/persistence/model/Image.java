package com.handemdowns.persistence.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "image", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"filename", "extension", "contentType", "size"})
@ToString(exclude={"ad"})
@Builder(builderMethodName = "objectBuilder")
@JsonIgnoreProperties("ad")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "filename", nullable = false)
    private String filename;

	@Column(name = "extension", nullable = false)
	private String extension;
	
	@Column(name = "content_type", nullable = false)
    private String contentType;

    @Column(name = "size", nullable = false)
    private Long size;

	@ManyToOne(optional = false)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

	public static ImageBuilder builder(String filename, String extension, String contentType, Long size, Ad ad) {
		return objectBuilder()
				.filename(filename)
				.extension(extension)
				.contentType(contentType)
				.size(size)
				.ad(ad);
	}
}