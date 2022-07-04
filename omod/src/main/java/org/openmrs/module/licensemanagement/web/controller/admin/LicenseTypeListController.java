/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.web.controller.admin;

import org.openmrs.api.context.Context;
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.LicenseTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller("licensemanagement.LicenseTypeListController")
@RequestMapping(value = "module/licensemanagement/admin/licenseType.list")
public class LicenseTypeListController {
    private static final String VIEW = "/module/vxnaidlicensemanagement/admin/licenseTypeList";

    @RequestMapping(method = RequestMethod.GET)
    public String onGet() {
        return VIEW;
    }

    @ModelAttribute("licenseTypes")
    protected List<LicenseType> getLicenseTypes() {
        return Context.getService(LicenseTypeService.class).getAllLicenseTypes(true);
    }
}
