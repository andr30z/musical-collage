package br.com.andre.config;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.ext.web.client.WebClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LastFMClient {

    private static final String LAST_FM_URL = "ws.audioscrobbler.com";

    @Inject
    private Vertx vertx;

    private WebClient lastFMClient;

    @Inject
    public void init() {
        this.lastFMClient = WebClient.create(vertx, new WebClientOptions().setDefaultHost(LAST_FM_URL));
    }

    public WebClient getLastFMClient() {
        return lastFMClient;
    }

}
