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

import org.openmrs.attribute.BaseAttributeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The DeviceAttributeType Class.
 * <p>
 * This entity represents a kind of attribute a Device can have.
 * </p>
 */
@Entity(name = "licensemanagement.DeviceAttributeType")
@Table(name = "licensemgmt_device_attribute_type")
public class DeviceAttributeType extends BaseAttributeType<Device> implements Serializable {

  private static final long serialVersionUID = 5981492992738802133L;

  @Id
  @GeneratedValue
  @Column(name = "device_attribute_type_id")
  private Integer deviceAttributeTypeId;

  @Override
  public Integer getId() {
    return getDeviceAttributeTypeId();
  }

  @Override
  public void setId(Integer deviceAttributeTypeId) {
    setDeviceAttributeTypeId(deviceAttributeTypeId);
  }

  /**
   * Gets DeviceAttributeType ID. Take notice, that this is the same value as {@link #getId()}.
   *
   * @return deviceAttributeTypeId, or null if this object is not persisted yet
   */
  public Integer getDeviceAttributeTypeId() {
    return deviceAttributeTypeId;
  }

  /**
   * Sets DeviceAttributeType ID, Take notice, that this sets the same property as
   * {@link #setId(Integer)}.
   *
   * @param deviceAttributeTypeId, the ID to set
   */
  public void setDeviceAttributeTypeId(Integer deviceAttributeTypeId) {
    this.deviceAttributeTypeId = deviceAttributeTypeId;
  }
}
