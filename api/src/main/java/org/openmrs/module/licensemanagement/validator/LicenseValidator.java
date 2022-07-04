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

import org.openmrs.module.licensemanagement.License;
import org.openmrs.validator.ValidateUtil;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LicenseValidator implements Validator {
    @Override
    public boolean supports(Class<?> c) {
        return c.equals(License.class);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        final License license = (License) obj;
        if (license == null) {
            errors.rejectValue("license", "error.general");
        } else {
            ValidateUtil.validateFieldLengths(errors, obj.getClass(), "name", "description", "retireReason", "serialNo",
                    "online");
        }
    }
}
