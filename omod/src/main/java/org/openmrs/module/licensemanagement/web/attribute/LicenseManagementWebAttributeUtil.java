/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.web.attribute;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.CustomDatatype;
import org.openmrs.customdatatype.CustomDatatypeHandler;
import org.openmrs.customdatatype.CustomDatatypeUtil;
import org.openmrs.customdatatype.Customizable;
import org.openmrs.module.licensemanagement.DeviceAttribute;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.web.attribute.WebAttributeUtil;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The LicenseManagementWebAttributeUtil Class.
 * <p>
 * This is reimplemented helper from WebAttributeUtil, framework did not work with {@link DeviceAttribute}. The
 * {@link DeviceAttribute} doesn't derive from {@link org.openmrs.attribute.BaseAttribute} because that class has no JPA
 * annotations, once OpenMRS framework is updated this class can be deleted.
 * </p>
 */
public class LicenseManagementWebAttributeUtil extends WebAttributeUtil {
    private static final Pattern betweenSquareBrackets = Pattern.compile("\\[(\\d*)]");
    private static final int REQUEST_PARAM_MAX_NUM = 500;
    private static final Log LOG = LogFactory.getLog(LicenseManagementWebAttributeUtil.class);

    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDeeplyNestedIfStmts", "PMD.AvoidThrowingRawExceptionTypes", "java:S3776"})
    public static <CustomizableClass extends Customizable<DeviceAttribute>> void handleSubmittedDeviceAttributes(
            CustomizableClass owner, BindingResult errors, HttpServletRequest request,
            List<DeviceAttributeType> attributeTypes) {

        final List<DeviceAttribute> toVoid = new ArrayList<DeviceAttribute>();

        for (final DeviceAttributeType attrType : attributeTypes) {
            final CustomDatatype<?> dt = CustomDatatypeUtil.getDatatype(attrType);
            final CustomDatatypeHandler handler = CustomDatatypeUtil.getHandler(attrType);

            int elementCount = 0;
            for (@SuppressWarnings("unchecked") Enumeration<String> iter = request.getParameterNames();
                 iter.hasMoreElements(); ) {

                if (++elementCount > REQUEST_PARAM_MAX_NUM) {
                    LOG.error("The number of request parameters to scan for attribute values exceeds the maximum value " +
                            "of " + REQUEST_PARAM_MAX_NUM + ". Any exceeding parameter is ignored! This error may indicate" +
                            " a DoS attack.");
                    break;
                }

                final String paramName = iter.nextElement();

                if (paramName.startsWith("attribute." + attrType.getId())) {
                    String afterPrefix = paramName.substring(("attribute." + attrType.getId()).length());
                    Object valueAsObject;

                    try {
                        valueAsObject = getValue(request, dt, handler, paramName);
                    } catch (Exception ex) {
                        errors.rejectValue("activeAttributes", "attribute.error.invalid", new Object[] {attrType.getName()},
                                "Illegal value for " + attrType.getName());
                        continue;
                    }

                    if (afterPrefix.startsWith(".new[")) {
                        // if not empty, we create a new one
                        if (valueAsObject != null && !"".equals(valueAsObject)) {
                            final DeviceAttribute attr = new DeviceAttribute();
                            attr.setAttributeType(attrType);
                            attr.setValue(valueAsObject);
                            owner.addAttribute(attr);
                        }

                    } else if (afterPrefix.startsWith(".existing[")) {
                        // if it has changed, we edit the existing one
                        final Integer existingAttributeId = getFromSquareBrackets(afterPrefix);
                        final DeviceAttribute existing = findAttributeById(owner, existingAttributeId);

                        if (existing == null) {
                            throw new RuntimeException("DeviceAttribute was modified between page load and submit. Try " +
                                    "again.");
                        }

                        if (valueAsObject == null) {
                            // they changed an existing value to "", so we void that attribute
                            toVoid.add(existing);
                        } else if (!existing.getValue().equals(valueAsObject)) {
                            // they changed an existing value to a new value
                            toVoid.add(existing);
                            final DeviceAttribute newVal = new DeviceAttribute();
                            newVal.setAttributeType(attrType);
                            newVal.setValue(valueAsObject);
                            owner.addAttribute(newVal);
                        }
                    }
                }
            }
        }

        for (final DeviceAttribute attr : toVoid) {
            voidAttribute(attr);
        }
    }

    private static Integer getFromSquareBrackets(String input) {
        Matcher matcher = betweenSquareBrackets.matcher(input);
        matcher.find();
        return Integer.valueOf(matcher.group(1));
    }

    private static DeviceAttribute findAttributeById(Customizable<DeviceAttribute> owner, Integer existingAttributeId) {
        for (final DeviceAttribute candidate : owner.getActiveAttributes()) {
            if (candidate.getId().equals(existingAttributeId)) {
                return candidate;
            }
        }
        return null;
    }

    private static void voidAttribute(DeviceAttribute existing) {
        existing.setVoided(true);
        existing.setVoidedBy(Context.getAuthenticatedUser());
        existing.setDateVoided(new Date());
    }
}
