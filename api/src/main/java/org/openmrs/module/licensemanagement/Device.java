/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement;

import org.apache.commons.lang3.ObjectUtils;
import org.openmrs.Attributable;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.CustomValueDescriptor;
import org.openmrs.customdatatype.Customizable;
import org.openmrs.module.licensemanagement.api.DeviceService;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The Device Class.
 * <p>
 * This entity represents a single device.
 * </p>
 */
@Entity(name = "licensemanagement.Device")
@Table(name = "licensemgmt_device")
public class Device extends BaseOpenmrsMetadata
    implements Serializable, Attributable<Device>, Customizable<DeviceAttribute> {

  private static final long serialVersionUID = -4975247714948978930L;

  @Id
  @GeneratedValue
  @Column(name = "device_id")
  private Integer deviceId;

  @Column(name = "device_mac", length = 255)
  private String deviceMac;

  @Column(name = "device_linux", length = 255)
  private String deviceLinux;

  @Column(name = "device_windows", length = 255)
  private String deviceWindows;

  // Repeat the field from BaseCustomizableMetadata to add Hibernate mapping (unfortunately the BaseCustomizableMetadata
  // has no annotations)
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER, mappedBy = "device")
  @OrderBy("voided asc")
  private Set<DeviceAttribute> attributes = new LinkedHashSet<DeviceAttribute>();

  @Override
  public Integer getId() {
    return getDeviceId();
  }

  @Override
  public void setId(Integer deviceId) {
    setDeviceId(deviceId);
  }

  /**
   * Gets Device ID. Take notice, that this is the same value as {@link #getId()}.
   *
   * @return deviceId, or null if this object is not persisted yet
   */
  public Integer getDeviceId() {
    return deviceId;
  }

  /**
   * Sets Device ID, Take notice, that this sets the same property as {@link #setId(Integer)}.
   *
   * @param deviceId, the ID to set
   */
  public void setDeviceId(Integer deviceId) {
    this.deviceId = deviceId;
  }

  /**
   * @return the Device MAC
   */
  public String getDeviceMac() {
    return deviceMac;
  }

  /**
   * Sets Device MAC.
   *
   * @param deviceMac, the Device MAC to set
   */
  public void setDeviceMac(String deviceMac) {
    this.deviceMac = deviceMac;
  }


    /**
   * @return the Device LINUX
   */
  public String getDeviceLinux() {
    return deviceLinux;
  }

  /**
   * Sets Device LINUX.
   *
   * @param deviceLINUX, the Device LINUX to set
   */
  public void setDeviceLinux(String deviceLinux) {
    this.deviceLinux = deviceLinux;
  }

     /**
   * @return the Device Windows
   */
  public String getDeviceWindows() {
    return deviceWindows;
  }

  /**
   * Sets Device Windows.
   *
   * @param deviceWindows, the Device Windows to set
   */
  public void setDeviceWindows(String deviceWindows) {
    this.deviceWindows = deviceWindows;
  }

  @Override
  public Device hydrate(String deviceId) {
    try {
      return Context.getService(DeviceService.class).getDevice(Integer.valueOf(deviceId));
    } catch (Exception any) {
      return new Device();
    }
  }

  @Override
  public String serialize() {
    return ObjectUtils.toString(this.getId(), "");
  }

  @Override
  public List<Device> getPossibleValues() {
    try {
      return Context.getService(DeviceService.class).getAllDevices();
    } catch (Exception any) {
      return Collections.emptyList();
    }
  }

  @Override
  public List<Device> findPossibleValues(String searchText) {
    try {
      return Context.getService(DeviceService.class).getDevices(searchText);
    } catch (Exception any) {
      return Collections.emptyList();
    }
  }

  @Override
  public String getDisplayString() {
    return this.getName();
  }

  @Override
  public Set<DeviceAttribute> getAttributes() {
    return this.attributes;
  }

  public void setAttributes(Set<DeviceAttribute> attributes) {
    this.attributes = attributes;
  }

  @Override
  public Collection<DeviceAttribute> getActiveAttributes() {
    List<DeviceAttribute> ret = new ArrayList<DeviceAttribute>();
    if (this.getAttributes() != null) {

      for (DeviceAttribute attr : this.getAttributes()) {
        if (!attr.isVoided()) {
          ret.add(attr);
        }
      }
    }

    return ret;
  }

  @Override
  public List<DeviceAttribute> getActiveAttributes(CustomValueDescriptor ofType) {
    List<DeviceAttribute> ret = new ArrayList<DeviceAttribute>();
    if (this.getAttributes() != null) {

      for (DeviceAttribute attr : this.getAttributes()) {
        if (attr.getAttributeType().equals(ofType) && !attr.isVoided()) {
          ret.add(attr);
        }
      }
    }

    return ret;
  }

  @Override
  public void addAttribute(DeviceAttribute attribute) {
    if (this.getAttributes() == null) {
      this.setAttributes(new LinkedHashSet<DeviceAttribute>());
    }

    this.getAttributes().add(attribute);
    attribute.setOwner(this);
  }

  public void setAttribute(DeviceAttribute attribute) {
    if (getAttributes() == null) {
      addAttribute(attribute);
    } else if (getActiveAttributes(attribute.getAttributeType()).size() == 1) {
      replaceSingleExistingAttribute(attribute);
    } else {
      voidAllExistingAttributes(attribute.getAttributeType());
      addAttribute(attribute);
    }
  }

  private void replaceSingleExistingAttribute(DeviceAttribute attribute) {
    final DeviceAttribute existing = getActiveAttributes(attribute.getAttributeType()).get(0);

    if (existing.getValue().equals(attribute.getValue())) {
      return;
    }

    if (existing.getId() != null) {
      existing.setVoided(true);
    } else {
      getAttributes().remove(existing);
    }

    addAttribute(attribute);
  }

  private void voidAllExistingAttributes(DeviceAttributeType attributeType) {
    for (DeviceAttribute existing : getActiveAttributes(attributeType)) {
      if (existing.getAttributeType().equals(attributeType)) {
        if (existing.getId() != null) {
          existing.setVoided(true);
        } else {
          getAttributes().remove(existing);
        }
      }
    }
  }
}
