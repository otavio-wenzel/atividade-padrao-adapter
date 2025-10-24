package social.legacy;

import java.util.List;

public final class TwitterApi {
    private final String bearerToken;
    public TwitterApi(String bearerToken) { this.bearerToken = bearerToken; }

    public String postTweet(String text, List<String> mediaUrls) throws RuntimeException {
        if (bearerToken == null) throw new RuntimeException("AUTH");
        if (text == null || text.isBlank()) throw new RuntimeException("INVALID_TEXT");
        return "tw_" + Math.abs(text.hashCode());
    }
    public TwitterMetrics getMetrics(String tweetId) { return new TwitterMetrics(42, 7, 3, 123); }
    public boolean deleteTweet(String tweetId) { return tweetId != null; }

    public record TwitterMetrics(long likes, long replies, long retweets, long views) {}
}