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
import org.openmrs.customdatatype.SerializingCustomDatatype;
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.LicenseTypeService;
import org.springframework.stereotype.Component;

/**
 * The LicenseTypeDatatype Class.
 */
@Component
public class LicenseTypeDatatype extends SerializingCustomDatatype<LicenseType> {

    @Override
    public Summary doGetTextSummary(LicenseType licenseType) {
        return new Summary(licenseType.getName(), true);
    }

    @Override
    public String serialize(LicenseType licenseType) {
        if (licenseType == null) {
            return null;
        }
        return licenseType.getUuid();
    }

    @Override
    public LicenseType deserialize(String licenseTypeUuid) {
        if (StringUtils.isBlank(licenseTypeUuid)) {
            return null;
        }

        return Context.getService(LicenseTypeService.class).getLicenseTypeByUuid(licenseTypeUuid);
    }
}
