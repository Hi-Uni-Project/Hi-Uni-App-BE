package com.project.hiuni.domain.user.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.user.dto.request.UserPostRequest;
import com.project.hiuni.domain.user.v1.service.SocialProvider;
import com.project.hiuni.global.security.core.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String socialEmail;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SocialProvider socialProvider;

	@Column(nullable = false)
	private String univName;

	private String majorName;

	@Column(nullable = false)
	private String univEmail;

	@Column(unique = true, nullable = false)
	private String nickname;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private UserStatus status;

	private boolean marketingConsent;

	private boolean improvementConsent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private Image image;

	@Builder
	private User(
		Long id,
		String socialEmail,
		SocialProvider socialProvider,
		String univName,
		String majorName,
		String univEmail,
		String nickname,
		String imageUrl,
		Role role,
		UserStatus status,
		boolean marketingConsent,
		boolean improvementConsent,
		Image image
	) {

		this.id = id;
		this.socialEmail = socialEmail;
		this.socialProvider = socialProvider;
		this.univName = univName;
		this.majorName = majorName;
		this.univEmail = univEmail;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.role = role;
		this.status = status;
		this.marketingConsent = marketingConsent;
		this.improvementConsent = improvementConsent;
		this.image = image;
	}

	/**
	 * 일반 사용자를 생성하는 메서드 입니다
	 *
	 * @param socialEmail    소셜로그인 후 조회된 사용자의 이메일
	 * @param socialProvider 소셜로그인 제공자 (예: "google", "kakao")
	 * @param univName       대학교 이름
	 * @param majorName      전공 이름
	 * @param univEmail      대학교 이메일
	 * @param nickname       사용자 닉네임
	 * @param imageUrl       사용자 프로필 이미지 URL
	 * @return 생성된 User 객체
	 */
	public static User createStandardUserOf(
		String socialEmail,
		SocialProvider socialProvider,
		String univName,
		String majorName,
		String univEmail,
		String nickname,
		String imageUrl,
		boolean isMarketingConsent
	) {

		return User.builder()
			.id(null)
			.socialEmail(socialEmail)
			.socialProvider(socialProvider)
			.univName(univName)
			.majorName(majorName)
			.univEmail(univEmail)
			.nickname(nickname)
			.imageUrl(imageUrl)
			.role(Role.ROLE_USER)
			.marketingConsent(isMarketingConsent)
			.build();
	}

	public static User createStandardUserOf(
		UserPostRequest request,
		Image image
	) {

		return User.builder()
			.socialEmail(request.socialEmail())
			.socialProvider(request.socialProvider())
			.univName(request.univName())
			.majorName(request.majorName())
			.univEmail(request.univEmail())
			.nickname(request.nickname())
			.imageUrl(request.imageUrl())
			.role(Role.ROLE_USER)
			.marketingConsent(request.marketingConsent())
			.improvementConsent(request.improvementConsent())
			.image(image)
			.status(UserStatus.ACTIVE)
			.build();
	}


	/**
	 * 어드민 사용자를 생성하는 메서드 입니다
	 *
	 * @param socialEmail    소셜로그인 후 조회된 사용자의 이메일
	 * @param socialProvider 소셜로그인 제공자 (예: "google", "kakao")
	 * @param univName       대학교 이름
	 * @param majorName      전공 이름
	 * @param univEmail      대학교 이메일
	 * @param nickname       사용자 닉네임
	 * @param imageUrl       사용자 프로필 이미지 URL
	 * @return 생성된 User 객체
	 */
	public static User createAdminUserOf(String socialEmail, SocialProvider socialProvider,
		String univName, String majorName,
		String univEmail, String nickname, String imageUrl) {
		return User.builder()
			.id(null)
			.socialEmail(socialEmail)
			.socialProvider(socialProvider)
			.univName(univName)
			.majorName(majorName)
			.univEmail(univEmail)
			.nickname(nickname)
			.imageUrl(imageUrl)
			.role(Role.ROLE_ADMIN)
			.build();
	}

	public void cancelMarketingConsent() {
		this.marketingConsent = false;
	}

	public void agreeMarketingConsent() {
		this.marketingConsent = true;
	}
}
