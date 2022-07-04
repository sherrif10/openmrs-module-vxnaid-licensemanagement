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
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.LicenseTypeService;

/**
 * The LicenseEditor class.
 * <p>
 * Used at Form controllers to convert LicenseType to text.
 * </p>
 */
public class LicenseTypeEditor extends BasePropertyEditorSupport<LicenseType> {

    public LicenseTypeEditor() {
        super(LicenseType.class.getSimpleName());
    }

    @Override
    Integer getEntityId(LicenseType licenseType) {
        return licenseType.getLicenseTypeId();
    }

    @Override
    LicenseType getEntityById(Integer id) {
        return Context.getService(LicenseTypeService.class).getLicenseType(id);
    }

    @Override
    LicenseType getEntityByName(String text) {
        return Context.getService(LicenseTypeService.class).getLicenseType(text);
    }
}
