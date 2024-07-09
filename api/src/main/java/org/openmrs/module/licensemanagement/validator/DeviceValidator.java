/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.validator;

import org.openmrs.module.licensemanagement.Device;
import org.openmrs.validator.ValidateUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class DeviceValidator implements Validator {
    @Override
    public boolean supports(Class<?> c) {
        return c.equals(Device.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        final Device device = (Device) obj;
        if (device == null) {
            errors.rejectValue("device", "error.general");
        } else {
            ValidateUtil.validateFieldLengths(errors, obj.getClass(), "name", "description", "retireReason", "deviceMac", "deviceLinux", "deviceWindows");
        }
    }
}
