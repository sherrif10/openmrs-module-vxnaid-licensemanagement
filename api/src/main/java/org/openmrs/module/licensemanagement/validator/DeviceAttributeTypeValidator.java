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

import org.openmrs.api.context.Context;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.module.licensemanagement.api.DeviceService;
import org.openmrs.validator.BaseAttributeTypeValidator;
import org.openmrs.validator.ValidateUtil;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

public class DeviceAttributeTypeValidator extends BaseAttributeTypeValidator<DeviceAttributeType> {
    @Override
    public boolean supports(Class<?> clazz) {
        return DeviceAttributeType.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        super.validate(target, errors);
        final DeviceAttributeType deviceAttributeType = (DeviceAttributeType) target;
        final DeviceService deviceService = Context.getService(DeviceService.class);

        if (!StringUtils.isEmpty(deviceAttributeType.getName())) {
            final DeviceAttributeType otherAttributeType =
                    deviceService.getDeviceAttributeType(deviceAttributeType.getName());

            if (otherAttributeType != null && !otherAttributeType.getUuid().equals(deviceAttributeType.getUuid())) {
                errors.rejectValue("name", "licensemanagement.deviceAttributeType.error.nameAlreadyInUse");
            }
        } else {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
                    "licensemanagement.deviceAttributeType.error.nameEmpty");
        }

        ValidateUtil.validateFieldLengths(errors, target.getClass(), "name", "description", "datatypeClassname",
                "preferredHandlerClassname", "retireReason");
    }
}
