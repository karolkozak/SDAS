package com.sdas.services;

public abstract class SocialMediaDataFetcher<T, U> {
    abstract public void fetchData();
    abstract protected U getProviderTemplate();
    abstract protected T getLastSocialDataEntity();
}
