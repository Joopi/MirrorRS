package hooks;

public class Hook {
    private String obfuscatedOwner;
    private String obfuscatedField;
    private long multiplier;

    public Hook(String obfuscatedOwner, String obfuscatedField, long multiplier) {
        this.obfuscatedOwner = obfuscatedOwner;
        this.obfuscatedField = obfuscatedField;
        this.multiplier = multiplier;
    }

    public String getObfuscatedOwner() {
        return obfuscatedOwner;
    }
    public String getObfuscatedField() {
        return obfuscatedField;
    }

    public long getMultiplier() {
        return multiplier;
    }
}
