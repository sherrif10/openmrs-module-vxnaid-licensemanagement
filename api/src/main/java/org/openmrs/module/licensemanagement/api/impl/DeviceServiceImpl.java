/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.customdatatype.CustomDatatypeUtil;
import org.openmrs.module.licensemanagement.Device;
import org.openmrs.module.licensemanagement.DeviceAttribute;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.module.licensemanagement.api.DeviceService;
import org.openmrs.module.licensemanagement.api.dao.DeviceDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Transactional
public class DeviceServiceImpl extends BaseOpenmrsService implements DeviceService {
    private DeviceDAO deviceDAO;

    @Override
    public void setDeviceDAO(final DeviceDAO deviceDAO) {
        this.deviceDAO = deviceDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Device getDevice(final Integer locationId) throws APIException {
        return deviceDAO.getDevice(locationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Device getDevice(final String name) throws APIException {
        return deviceDAO.getDevice(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Device getDeviceByUuid(final String uuid) throws APIException {
        return deviceDAO.getDeviceByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public Device getDeviceByMAC(final String deviceMac) throws APIException {
        return deviceDAO.getDeviceByMac(deviceMac);
    }

    @Override
    @Transactional(readOnly = true)
    public Device getDeviceByMAC(final String deviceMac, final Boolean retired) throws APIException {
        return deviceDAO.getDeviceByMac(deviceMac, retired);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Device> getAllDevices() throws APIException {
        return deviceDAO.getAllDevices(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Device> getAllDevices(boolean includeRetired) throws APIException {
        return deviceDAO.getAllDevices(includeRetired);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Device> getDevices(String nameFragment) throws APIException {
        return deviceDAO.getDeviceByNameFragment(nameFragment);
    }

    @Override
    public Device saveDevice(final Device device) throws APIException {
        if (device.getName() == null) {
            throw new APIException("Device.name.required");
        }

        CustomDatatypeUtil.saveAttributesIfNecessary(device);

        return deviceDAO.saveDevice(device);
    }

    @Override
    public Device retireDevice(final Device device, final String reason) throws APIException {
        // Fields are set by org.openmrs.aop.RequiredDataAdvice
        return deviceDAO.saveDevice(device);
    }

    @Override
    public Device unretireDevice(final Device device) throws APIException {
        // Fields are set by org.openmrs.aop.RequiredDataAdvice
        return deviceDAO.saveDevice(device);
    }

    @Override
    public void purgeDevice(final Device device) throws APIException {
        deviceDAO.deleteDevice(device);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceAttributeType getDeviceAttributeType(final Integer deviceAttributeTypeId) throws APIException {
        return deviceDAO.getDeviceAttributeType(deviceAttributeTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceAttributeType getDeviceAttributeType(final String name) throws APIException {
        return deviceDAO.getDeviceAttributeType(name);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceAttributeType getDeviceAttributeTypeByUuid(final String uuid) throws APIException {
        return deviceDAO.getDeviceAttributeTypeByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceAttributeType> getAllDeviceAttributeTypes() throws APIException {
        return deviceDAO.getAllDeviceAttributeTypes(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceAttributeType> getAllDeviceAttributeTypes(boolean includeRetired) throws APIException {
        return deviceDAO.getAllDeviceAttributeTypes(includeRetired);
    }

    @Override
    public DeviceAttributeType saveDeviceAttributeType(final DeviceAttributeType deviceAttributeType) throws APIException {
        return deviceDAO.saveDeviceAttributeType(deviceAttributeType);
    }

    @Override
    public DeviceAttributeType retireDeviceAttributeType(DeviceAttributeType deviceAttributeType, String retireReason) throws APIException {
        // Fields are set by org.openmrs.aop.RequiredDataAdvice
        return deviceDAO.saveDeviceAttributeType(deviceAttributeType);
    }

    @Override
    public DeviceAttributeType unretireDeviceAttributeType(DeviceAttributeType deviceAttributeType) throws APIException {
        // Fields are set by org.openmrs.aop.RequiredDataAdvice
        return deviceDAO.saveDeviceAttributeType(deviceAttributeType);
    }

    @Override
    public void purgeDeviceAttributeType(DeviceAttributeType deviceAttributeType) throws APIException {
        deviceDAO.deleteDeviceAttributeType(deviceAttributeType);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceAttribute getDeviceAttributeByUuid(String deviceAttributeUuid) throws APIException {
        return deviceDAO.getDeviceAttributeByUuid(deviceAttributeUuid);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceAttribute getDeviceAttributeByDeviceAndTypeUuid(Device device, String deviceAttributeTypeUuid)
            throws APIException {
        final DeviceAttributeType deviceAttributeType = deviceDAO.getDeviceAttributeTypeByUuid(deviceAttributeTypeUuid);

        if (deviceAttributeType == null) {
            return null;
        }

        return deviceDAO.getDeviceAttributeByDeviceAndType(device, deviceAttributeType);
    }

    @Override
    public List<DeviceAttribute> getDeviceAttributes(final String deviceAttributeTypeUuid) throws APIException {
        final DeviceAttributeType deviceAttributeType = deviceDAO.getDeviceAttributeTypeByUuid(deviceAttributeTypeUuid);

        if (deviceAttributeType == null) {
            return Collections.emptyList();
        }
        return deviceDAO.getDeviceAttributes(deviceAttributeType);
    }

    @Override
    public long getDeviceCount(boolean includeRetired) throws APIException {
        return deviceDAO.getDeviceCount(includeRetired);
    }
}
