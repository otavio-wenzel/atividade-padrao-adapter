package social.legacy;

public final class LinkedInClient {
    private final String token;
    public LinkedInClient(String token) { this.token = token; }
    
    public String share(String text, byte[] image) {
    if (token == null) throw new RuntimeException("AUTH");
    return "li_" + Math.abs(text.hashCode());
    }
    public Stats statsOf(String shareId) { return new Stats(10, 2, 1, 222); }
    public boolean deleteShare(String shareId) { return shareId != null; }
      
    public record Stats(long reactions, long comments, long reshares, long views) {}
}