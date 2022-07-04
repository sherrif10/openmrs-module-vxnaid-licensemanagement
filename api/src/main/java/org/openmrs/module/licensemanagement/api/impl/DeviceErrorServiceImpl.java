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

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.licensemanagement.Device;
import org.openmrs.module.licensemanagement.DeviceError;
import org.openmrs.module.licensemanagement.api.DeviceErrorService;
import org.openmrs.module.licensemanagement.api.dao.DeviceErrorDAO;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Transactional
public class DeviceErrorServiceImpl extends BaseOpenmrsService implements DeviceErrorService {

    private DeviceErrorDAO deviceErrorDAO;

    public void setDeviceErrorDAO(DeviceErrorDAO deviceErrorDAO) {
        this.deviceErrorDAO = deviceErrorDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceError getDeviceError(final Integer deviceErrorId) {
        return deviceErrorDAO.getDeviceError(deviceErrorId);
    }

    @Override
    @Transactional(readOnly = true)
    public DeviceError getDeviceErrorByUuid(final String uuid) {
        return deviceErrorDAO.getDeviceErrorByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeviceError> getDeviceErrorsByKey(Device device, final String key, boolean includeVoided) {
        return deviceErrorDAO.getDeviceErrorsByKey(device, key, includeVoided);
    }

    @Override
    public DeviceError saveDeviceError(final DeviceError deviceError) {
        return deviceErrorDAO.saveDeviceError(deviceError);
    }
}
