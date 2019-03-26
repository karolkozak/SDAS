package com.sdas.services;

import com.sdas.models.TweetEntity;
import com.sdas.models.UserProfile;
import com.sdas.repositories.TweetRepository;
import com.sdas.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TweetRepositoryService {
    private final TweetRepository tweetRepository;
    private final UserProfileRepository userProfileRepository;

    public void storeTweets(List<Tweet> tweets) {
        tweets.stream()
                .map(tweet -> {
                    UserProfile userProfile = getUserProfile(tweet.getUser());
                    return TweetEntity.builder()
                            .vendorId(tweet.getId())
                            .createdAt(tweet.getCreatedAt())
                            .fromUser(userProfile)
                            .text(tweet.getText())
                            .inReplyToStatus(getTweetEntity(tweet.getInReplyToStatusId()))
                            .inReplyToUser(getUserProfile(tweet.getInReplyToUserId(), tweet.getInReplyToScreenName()))
                            .build();
                })
                .forEach(tweetRepository::save);
    }

    private UserProfile getUserProfile(TwitterProfile twitterProfile) {
        UserProfile userProfile = userProfileRepository.findUserProfileByVendorUserId(twitterProfile.getId());
        if (userProfile == null) {
            userProfile = UserProfile.builder()
                    .vendorUserId(twitterProfile.getId())
                    .screenName(twitterProfile.getScreenName())
                    .name(twitterProfile.getName())
                    .build();
            userProfile = userProfileRepository.save(userProfile);
        }
        return userProfile;
    }

    // TODO: params could be null
    private UserProfile getUserProfile(Long vendorUserId, String screenName) {
        UserProfile userProfile = userProfileRepository.findUserProfileByVendorUserId(vendorUserId);
        if (userProfile == null) {
            userProfile = UserProfile.builder()
                    .vendorUserId(vendorUserId)
                    .screenName(screenName)
                    .build();
            userProfile = userProfileRepository.save(userProfile);
        }
        return userProfile;
    }

    // TODO: param could be null
    /**
     * When we desire to get single tweetEntity we should ask Twitter API by vendorId.
     * As it is just inReplyToStatus we do not have to fetch whole Tweet
     */
    private TweetEntity getTweetEntity(Long vendorId) {
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
