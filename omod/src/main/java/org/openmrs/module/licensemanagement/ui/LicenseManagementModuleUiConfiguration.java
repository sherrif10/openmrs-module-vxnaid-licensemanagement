/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.ui;

import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.web.taglib.fieldgen.LicenseHandler;
import org.openmrs.module.licensemanagement.web.taglib.fieldgen.LicenseTypeHandler;
import org.openmrs.ui.framework.StandardModuleUiConfiguration;
import org.openmrs.ui.framework.fragment.FragmentFactory;
import org.openmrs.ui.framework.page.PageFactory;
import org.openmrs.ui.framework.resource.ResourceFactory;
import org.openmrs.web.taglib.fieldgen.FieldGenHandlerFactory;

/**
 * The LicenseManagementModuleUiConfiguration Class.
 * <p>
 * Responsible for adding {@link LicenseTypeHandler} and {@link LicenseHandler} to OpenMRS framework, to make the
 * dynamic editors (e.g.: attribute value) support these types.
 * </p>
 */
public class LicenseManagementModuleUiConfiguration extends StandardModuleUiConfiguration {

    @Override
    public void afterContextRefreshed(PageFactory pageFactory, FragmentFactory fragmentFactory,
                                      ResourceFactory resourceFactory) {
        super.afterContextRefreshed(pageFactory, fragmentFactory, resourceFactory);

        FieldGenHandlerFactory
                .getSingletonInstance()
                .getHandlers()
                .put(LicenseType.class.getName(), LicenseTypeHandler.class.getName());

        FieldGenHandlerFactory
                .getSingletonInstance()
                .getHandlers()
                .put(License.class.getName(), LicenseHandler.class.getName());
    }
}
