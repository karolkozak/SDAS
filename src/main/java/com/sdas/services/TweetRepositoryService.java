package com.sdas.services;

import com.sdas.models.TweetEntity;
import com.sdas.models.UserProfile;
import com.sdas.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class TweetRepositoryService {
    private final TweetRepository tweetRepository;
    private final UserProfileRepositoryService userService;

    public void storeTweets(List<Tweet> tweets, String tag) {
        tweets.stream()
                .map(tweet -> {
                    UserProfile userProfile = userService.getUserProfile(tweet.getUser());
                    return TweetEntity.builder()
                            .vendorId(tweet.getId())
                            .createdAt(tweet.getCreatedAt())
                            .fromUser(userProfile)
                            .text(tweet.getText())
                            .inReplyToStatus(getTweetEntity(tweet.getInReplyToStatusId()))
                            .inReplyToUser(userService.getUserProfile(tweet.getInReplyToUserId(), tweet.getInReplyToScreenName()))
                            .build();
                })
                .forEach(tweet -> checkTagAndSave(tweet, tag));
    }

    private void checkTagAndSave(TweetEntity tweetToSave, String tag) {
        TweetEntity sameTweetFromDb = tweetRepository.findTweetEntityByVendorId(tweetToSave.getVendorId());
        Set<String> tweetTags = new TreeSet<>();
        if (sameTweetFromDb != null) {
            tweetTags = sameTweetFromDb.getTags();
        }
        tweetTags.add(tag);
        tweetToSave.setTags(tweetTags);
        tweetRepository.save(tweetToSave);
    }

    /**
     * When we desire to get single tweetEntity we should ask Twitter API by vendorId.
     * As it is just inReplyToStatus we do not have to fetch whole Tweet
     */
    private TweetEntity getTweetEntity(Long vendorId) {
        if (vendorId == null) {
            return null;
        }
        TweetEntity tweetEntity = tweetRepository.findTweetEntityByVendorId(vendorId);
        if (tweetEntity == null) {
            tweetEntity = TweetEntity.builder()
                    .vendorId(vendorId)
                    .build();
            tweetRepository.save(tweetEntity);
        }
        return tweetEntity;
    }
}
