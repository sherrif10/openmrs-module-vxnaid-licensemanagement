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
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.LicenseService;
import org.openmrs.module.licensemanagement.api.LicenseTypeService;
import org.openmrs.module.licensemanagement.propertyeditor.LicenseTypeEditor;
import org.openmrs.module.licensemanagement.validator.LicenseValidator;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

@Controller
public class LicenseFormController {
    private static final String VIEW = "/module/vxnaidlicensemanagement/admin/licenseForm";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(LicenseType.class, new LicenseTypeEditor());
    }

    @ModelAttribute("license")
    public License formBackingObject(@RequestParam(value = "licenseId", required = false) String licenseId) {
        final License license;

        if (StringUtils.isEmpty(licenseId)) {
            license = new License();
        } else {
            final LicenseService service = Context.getService(LicenseService.class);
            license = service.getLicense(Integer.valueOf(licenseId));
        }

        return license;
    }

    @ModelAttribute("licenseTypes")
    public List<LicenseType> getLicenseTypes() {
        return Context.getService(LicenseTypeService.class).getAllLicenseTypes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/module/licensemanagement/admin/license.form")
    public String showForm() {
        return VIEW;
    }

    @RequestMapping(value = "/module/licensemanagement/admin/license.form", method = RequestMethod.POST)
    public String handleSubmit(WebRequest request, @ModelAttribute("license") License license, BindingResult errors) {

        final LicenseService service = Context.getService(LicenseService.class);

        if (request.getParameter("purge") != null) {
            try {
                service.purgeLicense(license);
                request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "licensemanagement.license.purgedSuccessfully",
                        WebRequest.SCOPE_SESSION);
            } catch (Exception e) {
                request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "error.object.inuse.cannot.purge",
                        WebRequest.SCOPE_SESSION);
            }
            return "redirect:license.list";
        }

        new LicenseValidator().validate(license, errors);

        if (errors.hasErrors()) {
            return VIEW;
        }

        if (request.getParameter("save") != null) {
            service.saveLicense(license);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.license.saved"),
                    WebRequest.SCOPE_SESSION);
        } else if (request.getParameter("retire") != null) {
            final String retireReason = request.getParameter("retireReason");

            if (license.getId() != null && !(StringUtils.hasText(retireReason))) {
                errors.reject("retireReason", "general.retiredReason.empty");
                return VIEW;
            }

            service.retireLicense(license, retireReason);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.license.retired"),
                    WebRequest.SCOPE_SESSION);
        } else if (request.getParameter("unretire") != null) {
            service.unretireLicense(license);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.license.unretired"),
                    WebRequest.SCOPE_SESSION);
        }

        return "redirect:license.list";
    }
}
