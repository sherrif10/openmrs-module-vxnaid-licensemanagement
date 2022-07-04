/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.licensemanagement;

import org.openmrs.BaseOpenmrsData;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * The Device Error Class.
 * <p>
 * This entity represents an error reported by a device.
 * </p>
 */

@Entity(name = "licensemanagement.DeviceError")
@Table(name = "licensemgmt_device_error")
public class DeviceError extends BaseOpenmrsData implements Serializable {

    private static final long serialVersionUID = 349906371904556770L;

    @Id
    @GeneratedValue
    @Column(name = "device_error_id")
    private Integer deviceErrorId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;

    @Column(name = "meta_type", nullable = false)
    private String metaType;

    @Column(name = "meta_sub_type")
    private String metaSubType;

    @Column(name = "meta_value")
    private String metaValue;

    @Column(name = "error_key", nullable = false)
    private String key;

    @Column(name = "meta", columnDefinition = "text", length = 2000, nullable = false)
    private String meta;

    @Column(name = "stack_trace", columnDefinition = "text", length = 2000)
    private String stackTrace;

    @Column(name = "reported_date", nullable = false)
    private Date reportedDate;

    /**
     * @see org.openmrs.OpenmrsObject#getId()
     */
    @Override
    public Integer getId() {
        return getDeviceErrorId();
    }

    @Override
    public void setId(Integer deviceErrorId) {
        setDeviceErrorId(deviceErrorId);
    }

    public Integer getDeviceErrorId() {
        return deviceErrorId;
    }

    public void setDeviceErrorId(Integer deviceErrorId) {
        this.deviceErrorId = deviceErrorId;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getMetaType() {
        return metaType;
    }

    public void setMetaType(String metaType) {
        this.metaType = metaType;
    }

    public String getMetaSubType() {
        return metaSubType;
    }

    public void setMetaSubType(String metaSubType) {
        this.metaSubType = metaSubType;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Date getReportedDate() {
        return reportedDate;
    }

    public void setReportedDate(Date reportedDate) {
        this.reportedDate = reportedDate;
    }
}
