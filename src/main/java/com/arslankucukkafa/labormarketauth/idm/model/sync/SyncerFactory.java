package com.arslankucukkafa.labormarketauth.idm.model.sync;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SyncerFactory {

    private final GithubSyncer githubSyncer;
    private final GoogleSyncer googleSyncer;

    @Autowired
    public SyncerFactory(GithubSyncer githubSyncer, GoogleSyncer googleSyncer) {
        this.githubSyncer = githubSyncer;
        this.googleSyncer = googleSyncer;
    }

    public Syncer getSyncer(String provider) {
        if (provider.equals("github")) {
            return githubSyncer;
        } else if (provider.equals("google")) {
            return googleSyncer;
        } else {
            throw new IllegalArgumentException("Provider not supported");
        }
    }
}