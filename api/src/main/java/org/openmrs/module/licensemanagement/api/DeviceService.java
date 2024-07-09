/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.licensemanagement.Device;
import org.openmrs.module.licensemanagement.DeviceAttribute;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.module.licensemanagement.api.dao.DeviceDAO;
import org.openmrs.module.licensemanagement.util.PrivilegeConstants;

import java.util.List;

public interface DeviceService extends OpenmrsService {

    void setDeviceDAO(DeviceDAO dao);

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDevice(Integer locationId) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDevice(String name) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDeviceByUuid(String uuid) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDeviceByMAC(String deviceMac) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDeviceByMAC(String deviceMac, Boolean retired) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    List<Device> getAllDevices() throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    List<Device> getAllDevices(boolean includeRetired) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    List<Device> getDevices(String nameFragment) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_DEVICES)
    Device saveDevice(Device device) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_DEVICES)
    Device retireDevice(Device device, String reason) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_DEVICES)
    Device unretireDevice(Device device) throws APIException;

    @Authorized(PrivilegeConstants.PURGE_DEVICES)
    void purgeDevice(Device device) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICE_ATTRIBUTE_TYPES)
    DeviceAttributeType getDeviceAttributeType(Integer deviceAttributeTypeId) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICE_ATTRIBUTE_TYPES)
    DeviceAttributeType getDeviceAttributeType(String name) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICE_ATTRIBUTE_TYPES)
    DeviceAttributeType getDeviceAttributeTypeByUuid(String uuid) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDeviceByWindows(String deviceWindows) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDeviceByWindows(String deviceWindows, Boolean retired) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDeviceByLinux(String deviceLinux) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    Device getDeviceByLinux(String deviceLinux, Boolean retired) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICE_ATTRIBUTE_TYPES)
    List<DeviceAttributeType> getAllDeviceAttributeTypes() throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICE_ATTRIBUTE_TYPES)
    List<DeviceAttributeType> getAllDeviceAttributeTypes(boolean includeRetired) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_DEVICE_ATTRIBUTE_TYPES)
    DeviceAttributeType saveDeviceAttributeType(DeviceAttributeType deviceAttributeType) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_DEVICE_ATTRIBUTE_TYPES)
    DeviceAttributeType retireDeviceAttributeType(DeviceAttributeType deviceAttributeType, String retireReason) throws APIException;

    @Authorized(PrivilegeConstants.MANAGE_DEVICE_ATTRIBUTE_TYPES)
    DeviceAttributeType unretireDeviceAttributeType(DeviceAttributeType deviceAttributeType) throws APIException;

    @Authorized(PrivilegeConstants.PURGE_DEVICE_ATTRIBUTE_TYPES)
    void purgeDeviceAttributeType(DeviceAttributeType deviceAttributeType) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    DeviceAttribute getDeviceAttributeByUuid(String deviceAttributeUuid) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    DeviceAttribute getDeviceAttributeByDeviceAndTypeUuid(Device device, String deviceAttributeTypeUuid) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    List<DeviceAttribute> getDeviceAttributes(String deviceAttributeTypeUuid) throws APIException;

    @Authorized(PrivilegeConstants.GET_DEVICES)
    long getDeviceCount(boolean includeRetired) throws APIException;
}
