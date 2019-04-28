package com.sdas.services;

import com.sdas.models.TweetEntity;
import com.sdas.properties.SdasProperties;
import com.sdas.properties.TwitterProperties;
import com.sdas.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitterDataFetcher extends SocialMediaDataFetcher<TweetEntity, Twitter> {
    private final TwitterProperties twitterProperties;
    private final SdasProperties sdasProperties;
    private final TweetRepository tweetRepository;
    private final TweetRepositoryService tweetRepositoryService;

    public void fetchData() {
        TweetEntity lastTweet = getLastSocialDataEntity();
        long lastRunTweetId;
        if (lastTweet != null) {
            lastRunTweetId = lastTweet.getVendorId();
        } else {
            lastRunTweetId = 1;
        }
        for (String tag : sdasProperties.getTags()) {
            long lastTweetId = lastRunTweetId;
            tweetRepository.findAll();
            Twitter twitterTemplate = getProviderTemplate();
            SearchResults searchResults;
            do {
                // TODO: handle rate exceeded exception
                // TODO: why does different package size return different dataset
                searchResults = twitterTemplate.searchOperations().search(tag, 100, lastTweetId, Long.MAX_VALUE);
                tweetRepositoryService.storeTweets(searchResults.getTweets(), tag);
                // TODO: find last Id for current tag not from whole db, should br fixed after updating problem
                lastTweetId = getLastSocialDataEntity().getVendorId();
            } while (searchResults.getTweets().size() != 0);
        }
    }

    protected Twitter getProviderTemplate() {
        String consumerKey = twitterProperties.getConsumerKey();
        String consumerSecret = twitterProperties.getConsumerSecret();
        String accessToken = twitterProperties.getAccessToken();
        String accessTokenSecret = twitterProperties.getAccessTokenSecret();

        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }

    protected TweetEntity getLastSocialDataEntity() {
        return tweetRepository.findLastByCreatedAt();
    }
}
