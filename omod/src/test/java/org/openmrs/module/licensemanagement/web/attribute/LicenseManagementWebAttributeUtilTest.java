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

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.logging.Log;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openmrs.api.context.Context;
import org.openmrs.customdatatype.CustomDatatype;
import org.openmrs.customdatatype.CustomDatatypeHandler;
import org.openmrs.customdatatype.CustomDatatypeUtil;
import org.openmrs.customdatatype.Customizable;
import org.openmrs.module.licensemanagement.DeviceAttribute;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

@RunWith(PowerMockRunner.class)
@PrepareForTest({CustomDatatypeUtil.class, Context.class})
public class LicenseManagementWebAttributeUtilTest {
    @Test
    public void handleSubmittedDeviceAttributesShouldHandle500Params() throws Exception {
        // given
        final HandleSubmittedDeviceAttributesTestParams testParams = prepareHandleSubmittedDeviceAttributesTestParams(500);

        // when
        LicenseManagementWebAttributeUtil.handleSubmittedDeviceAttributes(testParams.customizable, testParams.bindingResult,
                testParams.request, testParams.deviceAttributeTypes);

        // then
        Mockito.verify(testParams.customizable, Mockito.times(500)).addAttribute(Mockito.any(DeviceAttribute.class));
    }

    @Test
    public void handleSubmittedDeviceAttributesShouldHandleFirst500ParamsAndPrintErrorLog() throws Exception {
        // given
        final Log mockLog = Mockito.mock(Log.class);
        final HandleSubmittedDeviceAttributesTestParams testParams = prepareHandleSubmittedDeviceAttributesTestParams(1000);

        mockLicenseManagementWebAttributeUtilLOG(mockLog);

        // when
        LicenseManagementWebAttributeUtil.handleSubmittedDeviceAttributes(testParams.customizable, testParams.bindingResult,
                testParams.request, testParams.deviceAttributeTypes);

        // then
        Mockito.verify(testParams.customizable, Mockito.times(500)).addAttribute(Mockito.any(DeviceAttribute.class));
        Mockito
                .verify(mockLog)
                .error("The number of request parameters to scan for attribute values exceeds the maximum value " +
                        "of 500. Any exceeding parameter is ignored! This error may indicate a DoS attack.");
    }

    private void mockLicenseManagementWebAttributeUtilLOG(Log mockLog) throws Exception {
        final Field logStaticField = LicenseManagementWebAttributeUtil.class.getDeclaredField("LOG");
        logStaticField.setAccessible(true);

        // override 'final' modifier on the LOG field so it can be changed
        final Field commonModifiersField = Field.class.getDeclaredField("modifiers");
        commonModifiersField.setAccessible(true);
        commonModifiersField.setInt(logStaticField, logStaticField.getModifiers() & ~Modifier.FINAL);

        logStaticField.set(null, mockLog);
    }

    private HandleSubmittedDeviceAttributesTestParams prepareHandleSubmittedDeviceAttributesTestParams(
            int requestParametersSize) throws Exception {
        final int testAttributeTypeId = 123;

        final DeviceAttributeType testAttributeType = Mockito.mock(DeviceAttributeType.class);
        Mockito.when(testAttributeType.getId()).thenReturn(testAttributeTypeId);

        final Customizable<DeviceAttribute> customizable = Mockito.mock(Customizable.class);
        final BindingResult bindingResult = Mockito.mock(BindingResult.class);
        final HttpServletRequest request = prepareRequestWithAttributeParameters(requestParametersSize, testAttributeTypeId);

        final CustomDatatype testDatatype = Mockito.mock(CustomDatatype.class);
        final CustomDatatypeHandler testDatatypeHandler = Mockito.mock(CustomDatatypeHandler.class);

        Mockito.when(testDatatype.fromReferenceString(Mockito.anyString())).then(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) {
                return ObjectUtils.toString(invocationOnMock.getArguments()[0], null);
            }
        });

        PowerMockito.mockStatic(CustomDatatypeUtil.class);
        PowerMockito.when(CustomDatatypeUtil.class, "getDatatype", testAttributeType).thenReturn(testDatatype);
        PowerMockito.when(CustomDatatypeUtil.class, "getHandler", testAttributeType).thenReturn((testDatatypeHandler));

        return new HandleSubmittedDeviceAttributesTestParams(customizable, bindingResult, request,
                Collections.singletonList(testAttributeType));
    }

    private HttpServletRequest prepareRequestWithAttributeParameters(int count, int attributeTypeId) {
        final Vector<String> parameterNames = new Vector<String>();

        for (int attrIdx = 0; attrIdx < count; ++attrIdx) {
            parameterNames.add("attribute." + attributeTypeId + ".new[");
        }

        final HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getParameterNames()).thenReturn(parameterNames.elements());
        Mockito.when(request.getParameter(Mockito.anyString())).thenReturn("TestValue");
        return request;
    }

    private static class HandleSubmittedDeviceAttributesTestParams {
        final Customizable<DeviceAttribute> customizable;
        final BindingResult bindingResult;
        final HttpServletRequest request;
        final List<DeviceAttributeType> deviceAttributeTypes;

        private HandleSubmittedDeviceAttributesTestParams(Customizable<DeviceAttribute> customizable,
                                                          BindingResult bindingResult, HttpServletRequest request,
                                                          List<DeviceAttributeType> deviceAttributeTypes) {
            this.customizable = customizable;
            this.bindingResult = bindingResult;
            this.request = request;
            this.deviceAttributeTypes = deviceAttributeTypes;
        }
    }
}
