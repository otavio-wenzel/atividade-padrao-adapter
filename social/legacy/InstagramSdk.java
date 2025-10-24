package social.legacy;

public final class InstagramSdk {
    private final String accessToken;
    public InstagramSdk(String accessToken) { this.accessToken = accessToken; }
    
    public String publishPhoto(String caption, String imageUrl) {
    if (accessToken == null) throw new RuntimeException("AUTH");
    if (imageUrl == null || imageUrl.isBlank()) throw new RuntimeException("INVALID_MEDIA");
    return "ig_" + Math.abs((caption+imageUrl).hashCode());
    }
    public Insights getInsights(String mediaId) { return new Insights(77, 11, 4, 456); }
    public boolean removeMedia(String mediaId) { return mediaId != null; }
    
    public record Insights(long likes, long comments, long shares, long impressions) {}
}