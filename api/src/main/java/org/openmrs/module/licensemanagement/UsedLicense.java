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

/**
 * The UsedLicense Class.
 * <p>
 * The immutable data class which stores the License and the Device the License is assigned to.
 * </p>
 */
public final class UsedLicense {
    private final License license;
    private final Device device;

    public UsedLicense(License license, Device device) {
        this.license = license;
        this.device = device;
    }

    public License getLicense() {
        return license;
    }

    public Device getDevice() {
        return device;
    }
}
