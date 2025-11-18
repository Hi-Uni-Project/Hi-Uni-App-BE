package com.project.hiuni.domain.user.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.auth.dto.request.AuthSignUpRequest.Univ;
import com.project.hiuni.domain.auth.entity.Auth;
import com.project.hiuni.domain.auth.entity.SocialProvider;
import com.project.hiuni.domain.record.exception.InsufficientGenerationCountException;
import com.project.hiuni.domain.user.dto.request.UserPostRequest;
import com.project.hiuni.global.exception.ErrorCode;
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

	private String firstMajorName;
	private String secondMajorName;

	private String univEmail;

	private String nickname;

	private String imageUrl;

	private Integer aboutMeCnt;
	private Integer coverletterCnt;

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
		String firstMajorName,
		String secondMajorName,
		String univEmail,
		String nickname,
		String imageUrl,
		Role role,
		UserStatus userStatus,
		ProfileImage profileImage,
		Integer aboutMeCnt,
		Integer coverletterCnt
	) {

		this.id = id;
		this.auth = auth;
		this.socialId = socialId;
		this.socialEmail = socialEmail;
		this.socialProvider = socialProvider;
		this.univName = univName;
		this.firstMajorName = firstMajorName;
		this.secondMajorName = secondMajorName;
		this.univEmail = univEmail;
		this.nickname = nickname;
		this.imageUrl = imageUrl;
		this.role = role;
		this.userStatus = userStatus;
		this.profileImage = profileImage;
		this.aboutMeCnt = aboutMeCnt;
		this.coverletterCnt = coverletterCnt;
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
				.firstMajorName(null)
				.secondMajorName(null)
				.univEmail(null)
				.nickname(null)
				.imageUrl(null)
				.role(Role.ROLE_USER)
				.userStatus(UserStatus.ACTIVE)
				.profileImage(null)
				.aboutMeCnt(5)
				.coverletterCnt(5)
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
		String univName, String firstMajorName, String secondMajorName,
		String univEmail, String nickname, String imageUrl) {
		return User.builder()
			.id(null)
			.socialEmail(socialEmail)
			.socialProvider(socialProvider)
			.univName(univName)
			.firstMajorName(firstMajorName)
				.secondMajorName(secondMajorName)
			.univEmail(univEmail)
			.nickname(nickname)
			.imageUrl(imageUrl)
				.aboutMeCnt(5)
				.coverletterCnt(5)
			.role(Role.ROLE_ADMIN)
			.build();
	}

	/**
	 * 학교 관련 정보를 업데이트하는 메서드 입니다.
	 */
	public void updateUnivInfo(Univ univ) {
		this.univName = univ.getUnivName();
		this.firstMajorName = univ.getFirstMajorName();
		this.secondMajorName = univ.getSecondMajorName();
		this.univEmail = univ.getUnivEmail();
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

	public void decreaseAiAboutMe() {
		if(this.aboutMeCnt <= 0) {
			throw new InsufficientGenerationCountException(ErrorCode.INSUFFICIENT_GENERATION_COUNT);
		}
		this.aboutMeCnt -= 1;
	}

	public void decreaseAiCoverLetter() {
		if(this.coverletterCnt <= 0) {
			throw new InsufficientGenerationCountException(ErrorCode.INSUFFICIENT_GENERATION_COUNT);
		}
		this.coverletterCnt -= 1;
	}

}
