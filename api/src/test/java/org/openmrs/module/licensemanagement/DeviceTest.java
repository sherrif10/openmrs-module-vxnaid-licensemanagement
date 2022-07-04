package org.openmrs.module.licensemanagement;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeviceTest {

  private final Device device = new Device();

  private final DeviceAttributeType type = buildTestDeviceAttributeType();

  @Test
  public void shouldSetAttributeWhenAttributesAreEmpty() {
    device.setAttributes(null);

    device.setAttribute(buildTestDeviceAttribute("value1"));

    assertNotNull(device.getAttributes());
    assertEquals(1, device.getAttributes().size());
  }

  @Test
  public void shouldReplaceExistingAttributeWhenOnlyOneAttributeIsAvailable() {
    device.setAttributes(
        new HashSet<DeviceAttribute>(
            Collections.singletonList(buildTestDeviceAttribute("oldValue"))));

    device.setAttribute(buildTestDeviceAttribute("newValue"));

    assertEquals(1, device.getAttributes().size());
    assertEquals("newValue", device.getAttributes().iterator().next().getValue());
  }

  @Test
  public void shouldVoidAllExistingAttributesWhenNumberOfAttributesIsGreaterThanOne() {
    device.setAttributes(
        new HashSet<DeviceAttribute>(
            Arrays.asList(
                buildTestDeviceAttribute("testValue1"), buildTestDeviceAttribute("testValue2"))));

    device.setAttribute(buildTestDeviceAttribute("testValue3"));

    assertEquals(1, device.getAttributes().size());
    assertEquals("testValue3", device.getAttributes().iterator().next().getValue());
  }

  private DeviceAttribute buildTestDeviceAttribute(String value) {
    DeviceAttribute deviceAttribute = new DeviceAttribute();
    deviceAttribute.setDevice(new Device());
    deviceAttribute.setAttributeType(type);
    deviceAttribute.setValue(value);
    return deviceAttribute;
  }

  private DeviceAttributeType buildTestDeviceAttributeType() {
    DeviceAttributeType type = new DeviceAttributeType();
    type.setName("test_name");
    return type;
  }
}
