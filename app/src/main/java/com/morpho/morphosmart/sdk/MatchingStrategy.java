package com.morpho.morphosmart.sdk;



public enum MatchingStrategy {
    MORPHO_STANDARD_MATCHING_STRATEGY(0),
    MORPHO_ADVANCED_MATCHING_STRATEGY(1);
    
    private int value;

    private MatchingStrategy(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public String getLabel() {
        int i = this.value;
        if (i == 0) {
            return "Default";
        }
        return "Advanced";
    }

    public static MatchingStrategy fromString(String str) {
        if (str == null) {
            return null;
        }
        for (MatchingStrategy matchingStrategy : values()) {
            if (str.equalsIgnoreCase(matchingStrategy.getLabel())) {
                return matchingStrategy;
            }
        }
        return null;
    }
}
