package com.project.hiuni.domain.user.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.auth.entity.Auth;
import com.project.hiuni.domain.auth.entity.SocialProvider;
import com.project.hiuni.domain.user.dto.request.UserPostRequest;
import com.project.hiuni.global.security.core.Role;
import jakarta.persistence.CascadeType;
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auth_id")
	private Auth auth;

	@Column(unique = true, nullable = false)
	private String socialId;

	@Column
	private String socialEmail;

	@Enumerated(EnumType.STRING)
	private SocialProvider socialProvider;

	private String univName;

	private String majorName;

	private String univEmail;

	private String nickname;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "profile_image_id")
	private ProfileImage profileImage;

	@Builder
	private User(
		Long id,
		Auth auth,
		String socialId,
		String socialEmail,
		SocialProvider socialProvider,
		String univName,
		String majorName,
		String univEmail,
		String nickname,
		String imageUrl,
		Role role,
		UserStatus userStatus,
		ProfileImage profileImage
	) {

		this.id = id;
		this.auth = auth;
		this.socialId = socialId;
		this.socialEmail = socialEmail;
		this.socialProvider = socialProvider;
		this.univName = univName;
		this.majorName = majorName;
		this.univEmail = univEmail;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.role = role;
		this.userStatus = userStatus;
		this.profileImage = profileImage;
	}

	/**
	 * 소셜로그인 시 일반 사용자를 생성하는 메서드 입니다.
	 * @param socialId 소셜로그인 후 조회된 사용자의 고유 ID
	 * @param auth     인증 정보 (Auth 객체)
	 * @param socialEmail 소셜로그인 후 조회된 사용자의 이메일
	 * @param socialProvider 소셜로그인 제공자 (예: "google", "kakao")
	 * @return 생성된 User 객체
	 */
	public static User createStandardUserForSocial(String socialId, Auth auth, String socialEmail, SocialProvider socialProvider) {
		return User.builder()
				.id(null)
				.auth(auth)
				.socialId(socialId)
				.socialEmail(socialEmail)
				.socialProvider(socialProvider)
				.univName(null)
				.majorName(null)
				.univEmail(null)
				.nickname(null)
				.imageUrl(null)
				.role(Role.ROLE_USER)
				.userStatus(UserStatus.ACTIVE)
				.profileImage(null)
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

	public void changeNickname(String newNickname) {
		this.nickname = newNickname;
	}

	public void withdraw() {
		this.userStatus = UserStatus.WITHDRAWN;
	}

	public void deleteImage() {
		this.profileImage = null;
	}

	public void changeProfileImage(ProfileImage profileImage) {
		this.profileImage = profileImage;
	}
}
