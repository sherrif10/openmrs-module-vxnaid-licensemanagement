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
import org.openmrs.module.licensemanagement.DeviceAttribute;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.customdatatype.datatype.LicenseDatatype;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository("licensemanagement.LicenseDAO")
public class LicenseDAO extends BaseLicenseManagementOpenmrsMetadataDAO<License> {

    private static final String GET_DEVICE_ATTRIBUTE_WITH_LICENSE_SQL =  //
            "SELECT da.device_attribute_id " + //
                    "FROM licensemgmt_device_attribute AS da " + //
                    "INNER JOIN licensemgmt_device_attribute_type AS dat ON dat.device_attribute_type_id = da" +
                    ".attribute_type_id " +
                    "INNER JOIN licensemgmt_license AS l ON l.uuid = da.value_reference and l.license_type_id = " +
                    ":licenseTypeId and l.retired = false " + //
                    "where da.voided = false and dat.datatype = '" + LicenseDatatype.class.getName() + "' ";

    private static final String GET_LICENSE_NOT_LINKED_BY_DEVICE_ATTRIBUTE_SQL = //
            "SELECT l.license_id " +//
                    "FROM licensemgmt_license AS l " + //
                    "LEFT JOIN licensemgmt_device_attribute AS da ON da.value_reference = l.uuid and da.voided = false " +
                    "LEFT JOIN licensemgmt_device_attribute_type AS dat ON dat.device_attribute_type_id = da" +
                    ".attribute_type_id and dat.datatype = '" + LicenseDatatype.class.getName() + "' " + //
                    "WHERE l.license_type_id = :licenseTypeId and l.retired = false " +
                    "and da.device_attribute_id is null";

    public LicenseDAO() {
        super(License.class);
    }

    public License getLicense(final Integer id) {
        return internalRead(id);
    }

    public License getLicense(final String name) {
        return internalReadByName(name);
    }

    public License getLicenseByUuid(final String uuid) {
        return internalReadByUuid(uuid);
    }

    public List<License> getAllLicenses(boolean includeRetired) {
        return internalReadAll(includeRetired);
    }

    public List<License> getLicensesByType(final LicenseType licenseType) {
        final Criteria criteria = getSession().createCriteria(License.class).add(Restrictions.and(Restrictions
                                                                                                          .eq("retired",
                                                                                                              false),
                                                                                                  Restrictions
                                                                                                          .eq("licenseType",
                                                                                                              licenseType)));
        return criteria.list();
    }

    public License saveLicense(final License license) {
        return internalSave(license);
    }

    public void deleteLicense(final License license) {
        internalDelete(license);
    }

    public List<DeviceAttribute> getDeviceAttributeWithLicense(LicenseType licenseType) {
        final List<?> attributeIds = getSession().createSQLQuery(GET_DEVICE_ATTRIBUTE_WITH_LICENSE_SQL)
                                              .setParameter("licenseTypeId", licenseType.getId()).list();

        final List<DeviceAttribute> result = new ArrayList<DeviceAttribute>();

        for (final Object deviceAttributeId : attributeIds) {
            final DeviceAttribute attribute = (DeviceAttribute) getSession()
                    .get(DeviceAttribute.class, ((Number) deviceAttributeId).intValue());

            result.add(attribute);
        }

        return result;
    }

    public List<License> getLicenseNotLinkedByDeviceAttribute(LicenseType licenseType) {
        final List<?> licenseIds = getSession().createSQLQuery(GET_LICENSE_NOT_LINKED_BY_DEVICE_ATTRIBUTE_SQL)
                                            .setParameter("licenseTypeId", licenseType.getId()).list();

        final List<License> result = new ArrayList<License>();

        for (final Object licenseId : licenseIds) {
            final License license = (License) getSession().get(License.class, ((Number) licenseId).intValue());

            result.add(license);
        }

        return result;
    }
}
