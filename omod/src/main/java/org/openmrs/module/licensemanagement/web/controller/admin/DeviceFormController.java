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
import org.openmrs.module.licensemanagement.Device;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.api.DeviceService;
import org.openmrs.module.licensemanagement.propertyeditor.LicenseEditor;
import org.openmrs.module.licensemanagement.validator.DeviceValidator;
import org.openmrs.module.licensemanagement.web.attribute.LicenseManagementWebAttributeUtil;
import org.openmrs.web.WebConstants;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DeviceFormController {
    private static final String VIEW = "/module/vxnaidlicensemanagement/admin/deviceForm";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(License.class, new LicenseEditor());
    }

    @ModelAttribute("device")
    public Device formBackingObject(@RequestParam(value = "deviceId", required = false) String deviceId) {
        final Device device;

        if (StringUtils.isEmpty(deviceId)) {
            device = new Device();
        } else {
            final DeviceService service = Context.getService(DeviceService.class);
            device = service.getDevice(Integer.valueOf(deviceId));
        }

        return device;
    }

    @ModelAttribute("attributeTypes")
    public List<DeviceAttributeType> getAttributeTypes() {
        final DeviceService service = Context.getService(DeviceService.class);
        return service.getAllDeviceAttributeTypes();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/module/licensemanagement/admin/device.form")
    public String showForm() {
        return VIEW;
    }

    @RequestMapping(value = "/module/licensemanagement/admin/device.form", method = RequestMethod.POST)
    public String handleSubmit(WebRequest webRequest, HttpServletRequest httpServletRequest,
                               @ModelAttribute("device") Device device, BindingResult errors) {

        final DeviceService service = Context.getService(DeviceService.class);

        if (webRequest.getParameter("purge") != null) {
            try {
                service.purgeDevice(device);
                webRequest.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "licensemanagement.device.purgedSuccessfully",
                        WebRequest.SCOPE_SESSION);
            } catch (Exception e) {
                webRequest.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, "error.object.inuse.cannot.purge",
                        WebRequest.SCOPE_SESSION);
            }
            return "redirect:device.list";
        }

        new DeviceValidator().validate(device, errors);

        LicenseManagementWebAttributeUtil.handleSubmittedDeviceAttributes(device, errors, httpServletRequest,
                Context.getService(DeviceService.class).getAllDeviceAttributeTypes());

        if (errors.hasErrors()) {
            return VIEW;
        }

        if (webRequest.getParameter("save") != null) {
            service.saveDevice(device);

            webRequest.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.device.saved"),
                    WebRequest.SCOPE_SESSION);
        } else if (webRequest.getParameter("retire") != null) {
            final String retireReason = webRequest.getParameter("retireReason");

            if (device.getId() != null && !(StringUtils.hasText(retireReason))) {
                errors.reject("retireReason", "general.retiredReason.empty");
                return VIEW;
            }

            service.retireDevice(device, retireReason);

            webRequest.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.device.retired"),
                    WebRequest.SCOPE_SESSION);
        } else if (webRequest.getParameter("unretire") != null) {
            service.unretireDevice(device);

            webRequest.setAttribute(WebConstants.OPENMRS_MSG_ATTR,
                    Context.getMessageSourceService().getMessage("licensemanagement.device.unretired"),
                    WebRequest.SCOPE_SESSION);
        }

        return "redirect:device.list";
    }
}
