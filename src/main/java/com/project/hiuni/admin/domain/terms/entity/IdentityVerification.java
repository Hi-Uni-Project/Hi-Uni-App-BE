package com.project.hiuni.admin.domain.terms.entity;


import com.project.hiuni.admin.common.BaseEntity;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "identity_verification")
@Entity
public class IdentityVerification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private TermsInfo termsInfo;

	@Builder
	private IdentityVerification(Long id, TermsInfo termsInfo) {
		this.id = id;
		this.termsInfo = termsInfo;
	}

	public static IdentityVerification of(TermsInfo termsInfo) {
		return IdentityVerification.builder()
			.termsInfo(termsInfo)
			.build();
	}

	public String getContents(){
		return this.termsInfo.getAgreeContents();
	}

	public String getVersion(){
		return this.termsInfo.getAgreeVersion();
	}
}
