/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.web.taglib.fieldgen;

import org.openmrs.api.context.Context;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.api.LicenseService;

import java.util.List;

/**
 * The LicenseHandler Class.
 * <p>
 * This is handler used to initialize model for 'OpenMRS field gen' fields. These inputs are dynamically created
 * depending on the type of model data, e.g.: for inputs for attribute values.
 * </p>
 *
 * @see fieldGen/license.jsp
 */
public class LicenseHandler extends BaseFieldGenHandler {
    /**
     * This path is a workaround!
     * <p>
     * OpenMRS doesn't copy the license.field in the correct place in WEB-INF. Instead in
     * ./fieldGen/module/licensemanagement/*, it's in ./module/licensemanagement/fieldGen/*.
     * </p>
     */
    private final static String DEFAULT_URL = "../module/vxnaidlicensemanagement/fieldGen/license.field";

    public LicenseHandler() {
        super(DEFAULT_URL);
    }

    @Override
    public void loadAdditionalData() {
        final LicenseService licenseService = Context.getService(LicenseService.class);
        final List<License> licenses = licenseService.getAllLicenses(true);
        setParameter("licenses", licenses);
    }
}
