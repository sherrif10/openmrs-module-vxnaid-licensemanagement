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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.attribute.Attribute;
import org.openmrs.customdatatype.CustomDatatypeUtil;
import org.openmrs.customdatatype.InvalidCustomValueException;
import org.openmrs.customdatatype.NotYetPersistedException;
import org.openmrs.util.OpenmrsUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The DeviceAttribute Class.
 * <p>
 * This stores value defined by {@link DeviceAttributeType} for a single {@link Device}.
 * </p>
 */
@Entity(name = "licensemanagement.DeviceAttribute")
@Table(name = "licensemgmt_device_attribute")
public class DeviceAttribute extends BaseOpenmrsData
    implements Serializable, Attribute<DeviceAttributeType, Device>, Comparable<DeviceAttribute> {

  private static final long serialVersionUID = 7473851162400457298L;

  @Id
  @GeneratedValue
  @Column(name = "device_attribute_id")
  private Integer deviceAttributeId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "device_id", nullable = false)
  private Device device;

  @ManyToOne(optional = false)
  @JoinColumn(name = "attribute_type_id", nullable = false)
  private DeviceAttributeType attributeType;

  @Column(name = "value_reference", columnDefinition = "text", nullable = false, length = Integer.MAX_VALUE)
  private String valueReference;

  private transient Object value;
  private transient boolean dirty = false;

  @Override
  public Integer getId() {
    return getDeviceAttributeId();
  }

  @Override
  public void setId(Integer deviceAttributeId) {
    setDeviceAttributeId(deviceAttributeId);
  }

  /**
   * Gets DeviceAttribute ID. Take notice, that this is the same value as {@link #getId()}.
   *
   * @return deviceAttributeId, or null if this object is not persisted yet
   */
  public Integer getDeviceAttributeId() {
    return deviceAttributeId;
  }

  /**
   * Sets DeviceAttribute ID, Take notice, that this sets the same property as
   * {@link #setId(Integer)}.
   *
   * @param deviceAttributeId, the ID to set
   */
  public void setDeviceAttributeId(Integer deviceAttributeId) {
    this.deviceAttributeId = deviceAttributeId;
  }

  /**
   * Gets the Device. Take not this is the same value as {@link #getOwner()}.
   *
   * @return the device
   */
  public Device getDevice() {
    return device;
  }

  public void setDevice(Device device) {
    this.device = device;
  }

  @Override
  public Device getOwner() {
    return getDevice();
  }

  @Override
  public void setOwner(Device device) {
    setDevice(device);
  }

  @Override
  public DeviceAttributeType getAttributeType() {
    return this.attributeType;
  }

  public void setAttributeType(DeviceAttributeType attributeType) {
    this.attributeType = attributeType;
  }

  @Override
  public DeviceAttributeType getDescriptor() {
    return this.getAttributeType();
  }

  @Override
  public String getValueReference() {
    if (this.valueReference == null) {
      throw new NotYetPersistedException();
    } else {
      return this.valueReference;
    }
  }

  @Override
  public void setValueReferenceInternal(String valueReference) throws InvalidCustomValueException {
    this.valueReference = valueReference;
    this.dirty = false;
  }

  @Override
  public Object getValue() throws InvalidCustomValueException {
    if (this.value == null) {
      this.value = CustomDatatypeUtil.getDatatype(this.getAttributeType()).fromReferenceString(this.getValueReference());
    }

    return this.value;
  }

  @Override
  public <T> void setValue(T typedValue) throws InvalidCustomValueException {
    this.dirty = true;
    this.value = typedValue;
  }

  @Override
  public boolean isDirty() {
    return this.dirty;
  }

  @Override
  public int compareTo(DeviceAttribute other) {
    if (other == null) {
      return -1;
    } else {
      int retValue = this.isVoided().compareTo(other.isVoided());
      if (retValue == 0) {
        retValue = OpenmrsUtil.compareWithNullAsGreatest(this.getAttributeType().getId(), other.getAttributeType().getId());
      }

      if (retValue == 0) {
        retValue = OpenmrsUtil.compareWithNullAsGreatest(this.getValueReference(), other.getValueReference());
      }

      if (retValue == 0) {
        retValue = OpenmrsUtil.compareWithNullAsGreatest(this.getId(), other.getId());
      }

      return retValue;
    }
  }

}
