package social.config;

import java.util.HashMap;
import java.util.Map;

public final class Config {
    private final Map<String, String> props = new HashMap<>();


    public String get(String key) { return props.get(key); }
    public void set(String key, String value) { props.put(key, value); }


    public static Config fromEnv() {
        Config c = new Config();
        System.getenv().forEach((k,v) -> c.set(k, v));
        return c;
    }
}