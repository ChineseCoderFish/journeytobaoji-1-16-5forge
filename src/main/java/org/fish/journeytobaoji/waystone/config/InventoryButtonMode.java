package org.fish.journeytobaoji.waystone.config;

public class InventoryButtonMode {

    private final String value;

    public InventoryButtonMode(String value) {
        this.value = value;
    }

    public boolean isEnabled() {
        return !"NONE".equals(value);
    }

    public boolean isReturnToNearest() {
        return "NEAREST".equals(value);
    }

    public boolean isReturnToAny() {
        return "ANY".equals(value);
    }

    public boolean hasNamedTarget() {
        return isEnabled() && !isReturnToNearest() && !isReturnToAny();
    }

    public String getNamedTarget() {
        return value;
    }
}
