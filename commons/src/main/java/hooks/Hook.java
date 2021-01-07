package hooks;

public class Hook {
    private String owner;
    private String name;
    private long multiplier;
    private int access;

    public Hook(String owner, String name, long multiplier, int access) {
        this.owner = owner;
        this.name = name;
        this.multiplier = multiplier;
        this.access = access;
    }

    public long getMultiplier() {
        return multiplier;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public int getAccess() {
        return access;
    }

    public void print() {
        System.out.println(owner + "." + name + " * " + multiplier + "  bitmask: " + access);
    }
}
