package com.bloodbank_webapp.backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "COMMUNITY_STORIES")
public class CommunityStories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORYID", nullable = false)
    private Long storyId;

    @Column(name = "USERID", nullable = false)
    private Long userId;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private StoryStatus status = StoryStatus.PENDING;

    @Column(name = "DATEPOSTED", nullable = false)
    private LocalDateTime datePosted = LocalDateTime.now();

    public enum StoryStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

    // Constructors
    public CommunityStories() {}

    public CommunityStories(Long userId, String title, String content, StoryStatus status) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.status = status;
        this.datePosted = LocalDateTime.now();
    }

    // Getters & Setters
    public Long getStoryId() {
        return storyId;
    }

    public void setStoryId(Long storyId) {
        this.storyId = storyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public StoryStatus getStatus() {
        return status;
    }

    public void setStatus(StoryStatus status) {
        this.status = status;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }
}
