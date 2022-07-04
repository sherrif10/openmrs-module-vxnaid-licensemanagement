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

import org.apache.commons.lang3.StringUtils;
import org.openmrs.BaseOpenmrsMetadata;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The License Class.
 * <p>
 * This entity represents a single license.
 * </p>
 */
@Entity(name = "licensemanagement.License")
@Table(name = "licensemgmt_license")
@AttributeOverride(name = "name", column = @Column(name = "name", nullable = true, length = 255))
public class License extends BaseOpenmrsMetadata {

    @Id
    @GeneratedValue
    @Column(name = "license_id")
    private Integer licenseId;

    @Column(name = "serial_no", columnDefinition = "text", length = Integer.MAX_VALUE)
    private String serialNo;

    @Column(name = "online", columnDefinition = "text", length = Integer.MAX_VALUE)
    private String online;

    @ManyToOne(optional = false)
    @JoinColumn(name = "license_type_id", nullable = false)
    private LicenseType licenseType;

    @Override
    public Integer getId() {
        return getLicenseId();
    }

    @Override
    public void setId(Integer licenseId) {
        setLicenseId(licenseId);
    }

    /**
     * Gets License ID. Take notice, that this is the same value as {@link #getId()}.
     *
     * @return licenseId, or null if this object is not persisted yet
     */
    public Integer getLicenseId() {
        return licenseId;
    }

    /**
     * Sets License ID, Take notice, that this sets the same property as {@link #setId(Integer)}.
     *
     * @param licenseId, the ID to set
     */
    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public String getDisplayString() {
        if (!StringUtils.isEmpty(getName())) {
            return getName();
        } else if (!StringUtils.isEmpty(getSerialNo())) {
            final String serialNo = getSerialNo();
            return serialNo.substring(0, Math.min(serialNo.length(), 16));
        } else {
            return getUuid();
        }
    }
}
