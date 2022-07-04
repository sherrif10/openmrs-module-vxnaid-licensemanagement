/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.api.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.licensemanagement.Device;
import org.openmrs.module.licensemanagement.DeviceError;
import java.util.List;

public class DeviceErrorDAO extends BaseLicenseManagementOpenmrsObjectDAO<DeviceError> {

    DeviceErrorDAO() {
        super(DeviceError.class);
    }

    public DeviceError getDeviceError(final Integer id) {
        return internalRead(id);
    }

    public DeviceError getDeviceErrorByUuid(final String uuid) {
        return internalReadByUuid(uuid);
    }

    public List<DeviceError> getDeviceErrorsByKey(Device device, final String key, boolean includeVoided) {
        final Criteria criteria = getSession().createCriteria(DeviceError.class).add(Restrictions.eq("device", device))
                                              .add(Restrictions.eq("key", key));
        if (!includeVoided) {
            criteria.add(Restrictions.eq("voided", false));
        }
        return criteria.list();
    }

    public List<DeviceError> getDeviceErrorsByType(String type, boolean includeVoided) {
        final Criteria criteria = getSession().createCriteria(DeviceError.class).add(Restrictions.eq("type", type));
        if (!includeVoided) {
            criteria.add(Restrictions.eq("voided", false));
        }
        return criteria.list();
    }

    public DeviceError saveDeviceError(final DeviceError deviceError) {
        return internalSave(deviceError);
    }

    public void deleteDeviceError(final DeviceError deviceError) {
        internalDelete(deviceError);
    }
}
