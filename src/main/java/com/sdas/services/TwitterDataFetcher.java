package com.sdas.services;

import com.sdas.repositories.TweetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TwitterDataFetcher {
    private final Environment environment;
    private final TweetRepository tweetRepository;

    @Value("sdas.dataProvider")
    private String providerName;

    @Value("sdas.socialMediaUrl")
    private String twitterUrl;

    public void fetchData() {
        // TODO: implement fetching
        Twitter twitterTemplace = getProviderTemplate();
        twitterTemplace.searchOperations().search("", 100);
    }

    public Twitter getProviderTemplate() {
        String consumerKey = environment.getProperty(providerName + ".consumerKey");
        String consumerSecret = environment.getProperty(providerName + ".consumerSecret");
        String accessToken = environment.getProperty(providerName + ".accessToken");
        String accessTokenSecret = environment.getProperty(providerName + ".accessTokenSecret");

        return new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
    }
}
