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
        String tag = sdasProperties.getTags().get(0);
        // TODO: pass all tags in one query if possible
        SearchParameters searchParameters = new SearchParameters(tag);
        if (lastTweet != null) {
            searchParameters.sinceId(lastTweet.getVendorId());
        }
        tweetRepository.findAll();
        Twitter twitterTemplate = getProviderTemplate();
        SearchResults searchResults = twitterTemplate.searchOperations().search(searchParameters);
        // TODO: fetch next pages
        tweetRepositoryService.storeTweets(searchResults.getTweets());
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
