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
                            .tags(new TreeSet<>())
                            .inReplyToStatus(getTweetEntity(tweet.getInReplyToStatusId()))
                            .inReplyToUser(userService.getUserProfile(tweet.getInReplyToUserId(), tweet.getInReplyToScreenName()))
                            .build();
                })
                .forEach(tweet -> checkIfSavedAndSave(tweet, tag));
    }

    private void checkIfSavedAndSave(TweetEntity tweetToSave, String tag) {
        TweetEntity savedTweet = tweetRepository.findTweetEntityByVendorId(tweetToSave.getVendorId());
        if (savedTweet != null && savedTweet.getText() != null) {
            addTagAndSave(savedTweet, tag);
        } else if(savedTweet != null) {
            overwriteEmptyNode(savedTweet, tweetToSave, tag);
        } else {
            addTagAndSave(tweetToSave, tag);
        }
    }

    private void addTagAndSave(TweetEntity tweet, String tag) {
        Set<String> storedTags = tweet.getTags();
        storedTags.add(tag);
        tweet.setTags(storedTags);
        tweetRepository.save(tweet);
    }

    /**
     * Used when there is empty TweetEntity node on database created via REPLY_TO relationship.
     * This method fills stored tweet and overwrites it
     */
    private void overwriteEmptyNode(TweetEntity emptyNode, TweetEntity tweetToSave, String tag) {
        tweetToSave.setId(emptyNode.getId());
        addTagAndSave(tweetToSave, tag);
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
