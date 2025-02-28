package com.bloodbank_webapp.backend.service;

import com.bloodbank_webapp.backend.model.CommunityStories;
import com.bloodbank_webapp.backend.repository.CommunityStoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityStoriesService {

    @Autowired
    private CommunityStoriesRepository communityStoriesRepository;

    // Create & Save a new story
    public CommunityStories createStory(CommunityStories story) {
        return communityStoriesRepository.save(story);
    }

    // Update story status (approve/reject)
    public Optional<CommunityStories> updateStoryStatus(Long storyId, CommunityStories.StoryStatus status) {
        Optional<CommunityStories> story = communityStoriesRepository.findById(storyId);
        if (story.isPresent()) {
            CommunityStories updatedStory = story.get();
            updatedStory.setStatus(status);
            communityStoriesRepository.save(updatedStory);
        }
        return story;
    }

    // Delete a story by ID
    public void deleteStory(Long storyId) {
        communityStoriesRepository.deleteById(storyId);
    }

    // Get all stories
    public List<CommunityStories> getAllStories() {
        return communityStoriesRepository.findAll();
    }

    // Get all approved stories
    public List<CommunityStories> getApprovedStories() {
        return communityStoriesRepository.findByStatus(CommunityStories.StoryStatus.APPROVED);
    }
}
