package social.factory;

import social.Plataforma;
import social.config.Config;
import social.core.CanalMidiaSocial;
import social.legacy.*;
import social.adapter.*;
import social.strategy.*;

public final class SocialMediaFactory {
    private SocialMediaFactory() {}

    public static CanalMidiaSocial<?> criar(Plataforma p, Config cfg) {
        return switch (p) {
            case TWITTER -> new TwitterAdapter(new TwitterApi(cfg.get("TWITTER_TOKEN")), new TwitterTruncStrategy(280));
            case INSTAGRAM -> new InstagramAdapter(new InstagramSdk(cfg.get("INSTAGRAM_TOKEN")), new TextoPlanoStrategy());
            case LINKEDIN -> new LinkedInAdapter(new LinkedInClient(cfg.get("LINKEDIN_TOKEN")), new TextoPlanoStrategy());
            case TIKTOK -> new TikTokAdapter(new TikTokApi(cfg.get("TIKTOK_TOKEN")), new TextoPlanoStrategy());
        };
    }
}