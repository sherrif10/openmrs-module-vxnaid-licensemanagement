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
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.module.licensemanagement.api.DeviceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "module/licensemanagement/admin/deviceAttributeType.list")
public class DeviceAttributeTypeListController {
    private static final String VIEW = "/module/vxnaidlicensemanagement/admin/deviceAttributeTypeList";

    @RequestMapping(method = RequestMethod.GET)
    public String onGet() {
        return VIEW;
    }

    @ModelAttribute("deviceAttributeTypes")
    protected List<DeviceAttributeType> getLicenseTypes() {
        return Context.getService(DeviceService.class).getAllDeviceAttributeTypes(true);
    }
}
