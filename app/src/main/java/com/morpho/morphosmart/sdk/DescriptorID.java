package com.morpho.morphosmart.sdk;

public enum DescriptorID {
    BINDESC_CUSTOM_DESCRIPTOR(195),
    BINDESC_FLASH_SIZE(183),
    BINDESC_LICENSES(191),
    BINDESC_MAX_DB(118),
    BINDESC_MAX_USER(117),
    BINDESC_OEM_PID(187),
    BINDESC_OEM_SN(188),
    BINDESC_PID(185),
    BINDESC_PRODUCT_NAME(184),
    BINDESC_SENSOR_ID(189),
    BINDESC_SENSOR_SN(190),
    BINDESC_SN(186),
    BINDESC_SOFT(182),
    BINDESC_VERSION(116);
    
    private int value;

    private DescriptorID(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }
}
