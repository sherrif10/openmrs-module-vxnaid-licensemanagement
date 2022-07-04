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
import org.openmrs.customdatatype.CustomDatatypeUtil;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.module.licensemanagement.api.DeviceService;
import org.openmrs.module.licensemanagement.validator.DeviceAttributeTypeValidator;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import java.util.Collection;

@Controller
public class DeviceAttributeTypeFormController {
    private static final String VIEW = "/module/vxnaidlicensemanagement/admin/deviceAttributeTypeForm";

    @ModelAttribute("datatypes")
    public Collection<String> getDatatypes() {
        return CustomDatatypeUtil.getDatatypeClassnames();
    }

    @ModelAttribute("handlers")
    public Collection<String> getHandlers() {
        return CustomDatatypeUtil.getHandlerClassnames();
    }

    @ModelAttribute("deviceAttributeType")
    public DeviceAttributeType formBackingObject(
            @RequestParam(value = "deviceAttributeTypeId", required = false) String deviceAttributeTypeId) {
        final DeviceAttributeType deviceAttributeType;

        if (StringUtils.isEmpty(deviceAttributeTypeId)) {
            deviceAttributeType = new DeviceAttributeType();
        } else {
            final DeviceService service = Context.getService(DeviceService.class);
            deviceAttributeType = service.getDeviceAttributeType(Integer.valueOf(deviceAttributeTypeId));
        }

        return deviceAttributeType;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/module/licensemanagement/admin/deviceAttributeType.form")
    public String showForm() {
        return VIEW;
    }

    @RequestMapping(value = "/module/licensemanagement/admin/deviceAttributeType.form", method = RequestMethod.POST)
    public String handleSubmit(WebRequest request,
                               @ModelAttribute("deviceAttributeType") DeviceAttributeType deviceAttributeType,
                               BindingResult errors) {

        final DeviceService service = Context.getService(DeviceService.class);

        if (request.getParameter("purge") != null) {
            try {
                service.purgeDeviceAttributeType(deviceAttributeType);
                request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                        "licensemanagement.deviceAttributeType.purgedSuccessfully", WebRequest.SCOPE_SESSION);
            } catch (Exception e) {
                request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "error.object.inuse.cannot.purge",
                        WebRequest.SCOPE_SESSION);
            }
            return "redirect:deviceAttributeType.list";
        }

        new DeviceAttributeTypeValidator().validate(deviceAttributeType, errors);

        if (errors.hasErrors()) {
            return VIEW;
        }

        if (request.getParameter("save") != null) {
            service.saveDeviceAttributeType(deviceAttributeType);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.deviceAttributeType.saved"),
                    WebRequest.SCOPE_SESSION);
        } else if (request.getParameter("retire") != null) {
            final String retireReason = request.getParameter("retireReason");

            if (deviceAttributeType.getId() != null && !(StringUtils.hasText(retireReason))) {
                errors.reject("retireReason", "general.retiredReason.empty");
                return VIEW;
            }

            service.retireDeviceAttributeType(deviceAttributeType, retireReason);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.deviceAttributeType.retired"),
                    WebRequest.SCOPE_SESSION);
        } else if (request.getParameter("unretire") != null) {
            service.unretireDeviceAttributeType(deviceAttributeType);

            request.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.deviceAttributeType.unretired"),
                    WebRequest.SCOPE_SESSION);
        }

        return "redirect:deviceAttributeType.list";
    }
}
