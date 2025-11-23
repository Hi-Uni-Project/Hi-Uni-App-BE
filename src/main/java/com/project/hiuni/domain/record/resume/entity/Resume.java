package com.project.hiuni.domain.record.resume.entity;

import com.project.hiuni.domain.record.resume.achievement.entity.Achievement;
import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.education.entity.Education;
import com.project.hiuni.domain.record.resume.exception.CustomInvalidException;
import com.project.hiuni.domain.record.resume.language.entity.Language;
import com.project.hiuni.domain.record.resume.link.entity.Link;
import com.project.hiuni.domain.record.resume.project.entity.Project;
import com.project.hiuni.domain.record.resume.skill.entity.Skill;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.global.exception.ErrorCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resume")
public class Resume {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private Integer birthYear;

  private String title;

  @Lob
  private String aboutMe;

  private String imageUrl;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
  private List<Career> careers = new ArrayList<>();

  @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
  private List<Project> projects = new ArrayList<>();

  @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
  private List<Education> educations = new ArrayList<>();

  @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
  private List<Language> languages = new ArrayList<>();

  @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
  private List<Achievement> achievements = new ArrayList<>();

  @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
  private List<Link> links = new ArrayList<>();

  @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL)
  private List<Skill> skills = new ArrayList<>();

  public void addCareer(Career career) {
    this.careers.add(career);
    career.setResume(this);
  }

  public void addProject(Project project) {
    this.projects.add(project);
    project.setResume(this);
  }

  public void addEducation(Education education) {
    this.educations.add(education);
    education.setResume(this);
  }

  public void addLanguage(Language language) {
    this.languages.add(language);
    language.setResume(this);
  }

  public void addAchievement(Achievement achievement) {
    this.achievements.add(achievement);
    achievement.setResume(this);
  }

  public void addLink(Link link) {
    this.links.add(link);
    link.setResume(this);
  }

  public void addSkill(Skill skill) {
    this.skills.add(skill);
    skill.setResume(this);
  }

  public void updateImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public void updateName(String name) {
    this.name = name;
  }

  public void updateBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  public void updateGender(Gender gender) {
    this.gender = gender;
  }

  public void updateTitle(String title) {
    this.title = title;
  }

  public void updateAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  private Resume(User user, String name, Gender gender, Integer birthYear, String title, String aboutMe, String imageUrl) {
    this.user = user;
    this.name = name;
    this.gender = gender;
    this.birthYear = birthYear;
    this.title = title;
    this.aboutMe = aboutMe;
    this.imageUrl = imageUrl;
  }

  public static Resume of(User user, String name, Gender gender, Integer birthYear, String title, String aboutMe, String imageUrl) {
    return new Resume(user, name, gender, birthYear, title, aboutMe, imageUrl);
  }


}
