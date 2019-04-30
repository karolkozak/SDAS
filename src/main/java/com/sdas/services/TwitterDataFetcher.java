package com.sdas.services;

import com.sdas.models.TweetEntity;
import com.sdas.properties.SdasProperties;
import com.sdas.properties.TwitterProperties;
import com.sdas.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.SearchParameters;
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
            SearchParameters searchParameters = new SearchParameters(tag);
            searchParameters.count(100);
            searchParameters.sinceId(lastRunTweetId);
            tweetRepository.findAll();
            Twitter twitterTemplate = getProviderTemplate();
            SearchResults searchResults;
            do {
                searchResults = twitterTemplate.searchOperations().search(searchParameters);
                tweetRepositoryService.storeTweets(searchResults.getTweets(), tag);
                searchParameters.sinceId(getLastSocialDataEntity().getVendorId());
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
