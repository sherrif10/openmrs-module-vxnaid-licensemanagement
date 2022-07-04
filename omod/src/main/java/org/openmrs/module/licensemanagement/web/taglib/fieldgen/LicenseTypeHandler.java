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
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.LicenseTypeService;

import java.util.List;

/**
 * The LicenseTypeHandler Class.
 * <p>
 * This is handler used to initialize model for 'OpenMRS field gen' fields. These inputs are dynamically created
 * depending on the type of model data, e.g.: for inputs for attribute values.
 * </p>
 *
 * @see fieldGen/licenseType.jsp
 */
public class LicenseTypeHandler extends BaseFieldGenHandler {
    /**
     * This path is a workaround!
     * <p>
     * OpenMRS doesn't copy the license.field in the correct place in WEB-INF. Instead in
     * ./fieldGen/module/licensemanagement/*, it's in ./module/licensemanagement/fieldGen/*.
     * </p>
     */
    private final static String DEFAULT_URL = "../module/vxnaidlicensemanagement/fieldGen/licenseType.field";

    public LicenseTypeHandler() {
        super(DEFAULT_URL);
    }

    @Override
    void loadAdditionalData() {
        final LicenseTypeService licenseTypeService = Context.getService(LicenseTypeService.class);
        final List<LicenseType> licenseTypes = licenseTypeService.getAllLicenseTypes();
        setParameter("licenseTypes", licenseTypes);
    }
}
