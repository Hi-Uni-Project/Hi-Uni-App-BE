package com.project.hiuni.domain.user.dto.response;

import com.project.hiuni.domain.user.entity.ProfileImage;
import com.project.hiuni.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserResponse {

	private Long userId;

	private String socialEmail;

	private String univName;

	private String majorName;

	private String univEmail;

	private String nickname;

	private ProfileImage profileImage;


	@Builder
	private UserResponse(
		Long userId,
		String socialEmail,
		String univName,
		String majorName,
		String univEmail,
		String nickname,
		ProfileImage profileImage
	) {
		this.userId = userId;
		this.socialEmail = socialEmail;
		this.univName = univName;
		this.majorName = majorName;
		this.univEmail = univEmail;
		this.nickname = nickname;
		this.profileImage = profileImage;
	}

	public static UserResponse from(User user) {
		return UserResponse.builder()
			.userId(user.getId())
			.socialEmail(user.getSocialEmail())
			.univName(user.getUnivName())
			.majorName(user.getMajorName())
			.univEmail(user.getUnivEmail())
			.nickname(user.getNickname())
			.profileImage(user.getProfileImage())
			.build();
	}
}
