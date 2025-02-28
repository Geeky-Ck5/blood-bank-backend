package com.bloodbank_webapp.backend.controller;

import com.bloodbank_webapp.backend.model.CommunityStories;
import com.bloodbank_webapp.backend.service.CommunityStoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/community-stories")
public class CommunityStoriesController {

    @Autowired
    private CommunityStoriesService communityStoriesService;

    // 1. Create a new community story
    @PostMapping
    public ResponseEntity<CommunityStories> createStory(@RequestBody CommunityStories story) {
        return ResponseEntity.ok(communityStoriesService.createStory(story));
    }

    // 2. Update status (APPROVE or REJECT)
    @PutMapping("/{storyId}/status")
    public ResponseEntity<CommunityStories> updateStoryStatus(
            @PathVariable Long storyId,
            @RequestParam CommunityStories.StoryStatus status) {

        Optional<CommunityStories> updatedStory = communityStoriesService.updateStoryStatus(storyId, status);
        return updatedStory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. Delete a story
    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@PathVariable Long storyId) {
        communityStoriesService.deleteStory(storyId);
        return ResponseEntity.noContent().build();
    }

    // 4. Get all stories
    @GetMapping
    public ResponseEntity<List<CommunityStories>> getAllStories() {
        return ResponseEntity.ok(communityStoriesService.getAllStories());
    }

    // 5. Get all approved stories
    @GetMapping("/approved")
    public ResponseEntity<List<CommunityStories>> getApprovedStories() {
        return ResponseEntity.ok(communityStoriesService.getApprovedStories());
    }
}
