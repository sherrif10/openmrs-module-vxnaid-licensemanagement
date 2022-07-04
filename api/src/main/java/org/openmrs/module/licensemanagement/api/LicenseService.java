/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.UsedLicense;
import org.openmrs.module.licensemanagement.api.dao.LicenseDAO;
import org.openmrs.module.licensemanagement.util.PrivilegeConstants;

import java.util.List;

public interface LicenseService {

    void setLicenseDAO(LicenseDAO licenseDAO);

    @Authorized(PrivilegeConstants.GET_LICENSES)
    License getLicense(Integer licenseId) throws APIException;

    @Authorized(PrivilegeConstants.GET_LICENSES)
    License getLicense(String name) throws APIException;

    @Authorized(PrivilegeConstants.GET_LICENSES)
    License getLicenseByUuid(String uuid) throws APIException;

    @Authorized(PrivilegeConstants.GET_LICENSES)
    List<License> getAllLicenses() throws APIException;

    @Authorized(PrivilegeConstants.GET_LICENSES)
    List<License> getAllLicenses(boolean includeRetired) throws APIException;

    @Authorized(PrivilegeConstants.GET_LICENSES)
    List<License> getLicenses(LicenseType licenseType) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_LICENSES)
    License saveLicense(License license) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_LICENSES)
    License retireLicense(License license, String retireReason) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_LICENSES)
    License unretireLicense(License license) throws APIException;

    @Authorized(PrivilegeConstants.PURGE_LICENSES)
    void purgeLicense(License license) throws APIException;

    /**
     * Gets the list of Licenses of {@code licenseType} type which are used.
     * <p>
     * The used License is a License which is assigned to Device via
     * {@link org.openmrs.module.licensemanagement.DeviceAttribute} with `voided=false`.
     * </p>
     *
     * @param licenseType the License Type, not null
     * @return the list of used Licenses, never null
     * @throws APIException if there was error obtaining the result
     */
    List<UsedLicense> getUsedLicenses(LicenseType licenseType) throws APIException;

    /**
     * Get a License free License of {@code licenseType} type.
     * <p>
     * A free License is a License which is not assigned to any Device via
     * {@link org.openmrs.module.licensemanagement.DeviceAttribute} relation or the relation is voided.
     * </p>
     *
     * @param licenseType the License Type, not null
     * @return the free License or null if there is no free license
     * @throws APIException if there was error obtaining the result
     */
    License getAnyFreeLicense(LicenseType licenseType) throws APIException;
}
