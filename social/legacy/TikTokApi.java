package social.legacy;

public final class TikTokApi {
    private final String token;
    public TikTokApi(String token) { this.token = token; }
    
    public long uploadVideo(String description, String path) {
    if (token == null) throw new RuntimeException("AUTH");
    if (path == null || path.isBlank()) throw new RuntimeException("INVALID_MEDIA");
    return Math.abs((description+path).hashCode());
    }
    public TikTokStats stats(long videoId) { return new TikTokStats(99, 8, 5, 999); }
    public void delete(long videoId) { /* ok */ }
    
    public record TikTokStats(long likes, long comments, long shares, long views) {}
}