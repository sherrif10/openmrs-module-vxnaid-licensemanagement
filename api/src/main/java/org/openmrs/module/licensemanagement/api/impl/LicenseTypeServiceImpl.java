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
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.LicenseTypeService;
import org.openmrs.module.licensemanagement.api.dao.LicenseTypeDAO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class LicenseTypeServiceImpl extends BaseOpenmrsService implements LicenseTypeService {
    private LicenseTypeDAO licenseTypeDAO;

    @Override
    public void setLicenseTypeDAO(final LicenseTypeDAO licenseTypeDAO) {
        this.licenseTypeDAO = licenseTypeDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public LicenseType getLicenseType(final Integer licenseTypeId) throws APIException {
        return licenseTypeDAO.getLicenseType(licenseTypeId);
    }

    @Override
    @Transactional(readOnly = true)
    public LicenseType getLicenseType(final String name) throws APIException {
        return licenseTypeDAO.getLicenseType(name);
    }

    @Override
    @Transactional(readOnly = true)
    public LicenseType getLicenseTypeByUuid(final String uuid) throws APIException {
        return licenseTypeDAO.getLicenseTypeByUuid(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseType> getAllLicenseTypes() throws APIException {
        return licenseTypeDAO.getAllLicenseTypes(false);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LicenseType> getAllLicenseTypes(boolean includeRetired) throws APIException {
        return licenseTypeDAO.getAllLicenseTypes(includeRetired);
    }

    @Override
    public LicenseType saveLicenseType(final LicenseType licenseType) throws APIException {
        return licenseTypeDAO.saveLicenseType(licenseType);
    }

    @Override
    public LicenseType retireLicenseType(final LicenseType licenseType, final String reason) throws APIException {
        // Fields are set by org.openmrs.aop.RequiredDataAdvice
        return licenseTypeDAO.saveLicenseType(licenseType);
    }

    @Override
    public LicenseType unretireLicenseType(LicenseType licenseType) throws APIException {
        // Fields are set by org.openmrs.aop.RequiredDataAdvice
        return licenseTypeDAO.saveLicenseType(licenseType);
    }

    @Override
    public void purgeLicenseType(LicenseType licenseType) throws APIException {
        licenseTypeDAO.deleteLicenseType(licenseType);
    }
}
