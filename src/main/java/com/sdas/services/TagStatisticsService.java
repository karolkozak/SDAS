package com.sdas.services;

import com.sdas.models.Tag;
import com.sdas.models.TweetEntity;
import com.sdas.repositories.TagRepository;
import com.sdas.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagStatisticsService {
    private final TagRepository tagRepository;
    private final TweetRepository tweetRepository;

    public void createTagEntityForTagName(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        tagRepository.save(tag);
    }

    public void updateStatisticsForTag(String tagName) {
        Tag tag = tagRepository.findByTagNameEquals(tagName);
        List<TweetEntity> tweets = tweetRepository.findByTag(tagName);
        tag.setNumberOfTweets(tweets.size());
        tag.setReferencedTweets(tweets);
        tagRepository.save(tag);
    }
}
