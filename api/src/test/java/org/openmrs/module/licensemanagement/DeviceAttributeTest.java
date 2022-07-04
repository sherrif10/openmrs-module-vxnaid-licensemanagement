package org.openmrs.module.licensemanagement;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class DeviceAttributeTest {

    private final static String TEST_DEVICE_MAC_UUID = "AAAA-BBBB-CCCC-DDDD";

    @Test
    public void shouldReturnOwner() {
        final DeviceAttribute deviceAttribute = buildTestDeviceAttribute();

        Device device = deviceAttribute.getOwner();

        assertNotNull(device);
        assertEquals(Integer.valueOf(100), device.getDeviceId());
        assertEquals(TEST_DEVICE_MAC_UUID, device.getDeviceMac());
    }

    @Test
    public void shouldSetOwner() {
        DeviceAttribute deviceAttribute = new DeviceAttribute();
        assertNull(deviceAttribute.getDevice());

        deviceAttribute.setOwner(buildTestDevice());

        assertNotNull(deviceAttribute.getDevice());
        assertEquals(Integer.valueOf(100), deviceAttribute.getDevice().getDeviceId());
        assertEquals(TEST_DEVICE_MAC_UUID, deviceAttribute.getDevice().getDeviceMac());
    }

    @Test
    public void shouldReturnEqualityResultWhenComparingTwoTheSameDeviceAttributes() {
        DeviceAttribute deviceAttribute1 = buildTestDeviceAttribute();
        DeviceAttribute deviceAttribute2 = buildTestDeviceAttribute();

        int actual = deviceAttribute1.compareTo(deviceAttribute2);

        assertEquals(0, actual);
    }

    private DeviceAttribute buildTestDeviceAttribute() {
        DeviceAttribute deviceAttribute = new DeviceAttribute();
        deviceAttribute.setDevice(buildTestDevice());
        deviceAttribute.setAttributeType(buildTestDeviceAttributeType());
        deviceAttribute.setValueReferenceInternal("test value");
        deviceAttribute.setVoided(false);
        return deviceAttribute;
    }

    private Device buildTestDevice() {
        Device device = new Device();
        device.setDeviceId(100);
        device.setDeviceMac(TEST_DEVICE_MAC_UUID);
        return device;
    }

    private DeviceAttributeType buildTestDeviceAttributeType() {
        DeviceAttributeType deviceAttributeType = new DeviceAttributeType();
        deviceAttributeType.setDeviceAttributeTypeId(100);
        deviceAttributeType.setName("IRIS_MATCHING");
        deviceAttributeType.setMinOccurs(1);
        deviceAttributeType.setMaxOccurs(1);
        return deviceAttributeType;
    }
}
