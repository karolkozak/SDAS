package com.sdas.services;

import com.sdas.models.UserProfile;
import com.sdas.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileRepositoryService {
    private final UserProfileRepository userProfileRepository;

    public UserProfile getUserProfile(TwitterProfile twitterProfile) {
        UserProfile userProfile = userProfileRepository.findUserProfileByVendorUserId(twitterProfile.getId());
        if (userProfile == null) {
            userProfile = UserProfile.builder()
                    .vendorUserId(twitterProfile.getId())
                    .screenName(twitterProfile.getScreenName())
                    .name(twitterProfile.getName())
                    .lang(twitterProfile.getLanguage())
                    .followersCount(twitterProfile.getFollowersCount())
                    .build();
            userProfile = userProfileRepository.save(userProfile);
        }
        return userProfile;
    }

    // TODO: params could be null
    public UserProfile getUserProfile(Long vendorUserId, String screenName) {
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
}
