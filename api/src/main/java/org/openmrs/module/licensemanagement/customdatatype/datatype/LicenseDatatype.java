/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.customdatatype.datatype;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.CustomDatatype;
import org.openmrs.customdatatype.SerializingCustomDatatype;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.api.LicenseService;
import org.springframework.stereotype.Component;

/**
 * The LicenseDatatype Class.
 * <p>
 * This is custom Datatype for Attributes which values are instances of License entity. E.g.:
 * DeviceAttributeType of LicenseDatatype named 'Iris license' can be used to assign a License used
 * for iris scans to any Device.
 * </p>
 */
@Component
public class LicenseDatatype extends SerializingCustomDatatype<License> {

    @Override
    public Summary doGetTextSummary(License license) {
        return new CustomDatatype.Summary(license.getName(), true);
    }

    @Override
    public String serialize(License license) {
        if (license == null) {
            return null;
        }
        return license.getUuid();
    }

    @Override
    public License deserialize(String licenseUuid) {
        if (StringUtils.isBlank(licenseUuid)) {
            return null;
        }
        return Context.getService(LicenseService.class).getLicenseByUuid(licenseUuid);
    }
}
