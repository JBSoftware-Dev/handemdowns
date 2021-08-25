package com.handemdowns.persistence.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ad_report", schema = "handemdowns")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"ad", "reason", "reportedBy", "createDate"})
@ToString
@Builder(builderMethodName = "objectBuilder")
public class AdReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(targetEntity = Ad.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "ad_id", nullable = false)
    private Ad ad;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "reported_by", nullable = false)
    private String reportedBy;

    @Column(name = "create_date", nullable = false)
    private Date createDate;

	public static AdReportBuilder builder(Ad ad, String reason, String reportedBy) {
		return objectBuilder()
				.ad(ad)
				.reason(reason)
				.reportedBy(reportedBy)
				.createDate(new Date());
	}
}