/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.propertyeditor;

import org.openmrs.api.context.Context;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.api.LicenseService;

/**
 * The LicenseEditor class.
 * <p>
 * Used at Form controllers to convert License to text.
 * </p>
 */
public class LicenseEditor extends BasePropertyEditorSupport<License> {

    public LicenseEditor() {
        super(License.class.getSimpleName());
    }

    @Override
    Integer getEntityId(License license) {
        return license.getLicenseId();
    }

    @Override
    License getEntityById(Integer id) {
        return Context.getService(LicenseService.class).getLicense(id);
    }

    @Override
    License getEntityByName(String text) {
        return Context.getService(LicenseService.class).getLicense(text);
    }
}
