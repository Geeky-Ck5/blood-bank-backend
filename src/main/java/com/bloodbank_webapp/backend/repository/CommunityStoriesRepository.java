package com.bloodbank_webapp.backend.repository;

import com.bloodbank_webapp.backend.model.CommunityStories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommunityStoriesRepository extends JpaRepository<CommunityStories, Long> {
    List<CommunityStories> findByStatus(CommunityStories.StoryStatus status);
}
