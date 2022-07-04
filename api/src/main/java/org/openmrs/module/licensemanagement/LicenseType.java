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

import org.openmrs.BaseOpenmrsMetadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The LicenseType Class.
 * <p>
 * This entity represents a single kind of Licenses, eg.: Iris Scan license
 * </p>
 */
@Entity(name = "licensemanagement.LicenseType")
@Table(name = "licensemgmt_license_type")
public class LicenseType extends BaseOpenmrsMetadata {

    @Id
    @GeneratedValue
    @Column(name = "license_type_id")
    private Integer licenseTypeId;

    @Override
    public Integer getId() {
        return getLicenseTypeId();
    }

    @Override
    public void setId(Integer licenseTypeId) {
        setLicenseTypeId(licenseTypeId);
    }

    /**
     * Gets LicenseType ID. Take notice, that this is the same value as {@link #getId()}.
     *
     * @return licenseTypeId, or null if this object is not persisted yet
     */
    public Integer getLicenseTypeId() {
        return licenseTypeId;
    }

    /**
     * Sets LicenseType ID, Take notice, that this sets the same property as {@link #setId(Integer)}
     * .
     *
     * @param licenseTypeId, the ID to set
     */
    public void setLicenseTypeId(Integer licenseTypeId) {
        this.licenseTypeId = licenseTypeId;
    }
}
