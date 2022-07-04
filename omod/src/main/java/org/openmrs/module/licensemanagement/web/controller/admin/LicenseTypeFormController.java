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
import org.openmrs.module.licensemanagement.validator.LicenseTypeValidator;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LicenseTypeFormController {
    private static final String VIEW = "/module/vxnaidlicensemanagement/admin/licenseTypeForm";

    @ModelAttribute("licenseType")
    public LicenseType formBackingObject(@RequestParam(value = "licenseTypeId", required = false) String licenseTypeId) {
        final LicenseType licenseType;

        if (StringUtils.isEmpty(licenseTypeId)) {
            licenseType = new LicenseType();
        } else {
            final LicenseTypeService service = Context.getService(LicenseTypeService.class);
            licenseType = service.getLicenseType(Integer.valueOf(licenseTypeId));
        }

        return licenseType;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/module/licensemanagement/admin/licenseType.form")
    public String showForm() {
        return VIEW;
    }

    @RequestMapping(value = "/module/licensemanagement/admin/licenseType.form", method = RequestMethod.POST)
    public String handleSubmit(WebRequest request, @ModelAttribute("licenseType") LicenseType licenseType,
                               BindingResult errors) {

        final LicenseTypeService service = Context.getService(LicenseTypeService.class);

        if (request.getParameter("purge") != null) {
            try {
                service.purgeLicenseType(licenseType);
                request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "licensemanagement.licenseType.purgedSuccessfully",
                        WebRequest.SCOPE_SESSION);
            } catch (Exception e) {
                request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "error.object.inuse.cannot.purge",
                        WebRequest.SCOPE_SESSION);
            }
            return "redirect:licenseType.list";
        }

        new LicenseTypeValidator().validate(licenseType, errors);

        if (errors.hasErrors()) {
            return VIEW;
        }

        if (request.getParameter("save") != null) {
            service.saveLicenseType(licenseType);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.licenseType.saved"),
                    WebRequest.SCOPE_SESSION);
        } else if (request.getParameter("retire") != null) {
            final String retireReason = request.getParameter("retireReason");

            if (licenseType.getId() != null && !(StringUtils.hasText(retireReason))) {
                errors.reject("retireReason", "general.retiredReason.empty");
                return VIEW;
            }

            service.retireLicenseType(licenseType, retireReason);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.licenseType.retired"),
                    WebRequest.SCOPE_SESSION);
        } else if (request.getParameter("unretire") != null) {
            service.unretireLicenseType(licenseType);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.licenseType.unretired"),
                    WebRequest.SCOPE_SESSION);
        }

        return "redirect:licenseType.list";
    }
}
