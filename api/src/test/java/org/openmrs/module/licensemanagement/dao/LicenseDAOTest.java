/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.licensemanagement.DeviceAttribute;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.dao.LicenseDAO;
import org.openmrs.module.licensemanagement.api.dao.LicenseTypeDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LicenseDAOTest extends BaseModuleContextSensitiveTest {
    private static final String INITIAL_DATA_XML = "org/openmrs/module/licensemanagement/dao/LicenseDAOTest.xml";

    @Autowired
    LicenseTypeDAO typeDAO;

    @Autowired
    LicenseDAO dao;

    @Before
    public void runBeforeEachTest() throws Exception {
        executeDataSet(INITIAL_DATA_XML);
        updateSearchIndex();
    }

    @After
    public void runAfterEachTest() throws Exception {
        deleteAllData();
    }

    @Test
    public void getLicense_shouldReturnCorrectLicense() {
        // Given
        // Data loaded from XML

        // When
        final License second = dao.getLicense(2);

        // Then
        assertThat(second, notNullValue());
        assertThat(second, hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000002")));
    }

    @Test
    public void getLicense_name_shouldReturnCorrectLicense() {
        // Given
        // Data loaded from XML

        // When
        final License first = dao.getLicense("IRIS1");

        // Then
        assertThat(first, notNullValue());
        assertThat(first, hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000001")));
    }

    @Test
    public void getLicenseByUuid_shouldReturnCorrectLicense() {
        // Given
        // Data loaded from XML

        // When
        final License third = dao.getLicenseByUuid("f07ba61b-1000-0000-0000-000000000003");

        // Then
        assertThat(third, notNullValue());
        assertThat(third, hasProperty("id", is(3)));
    }

    @Test
    public void getAllLicenses_true_shouldReturnLicensesIncludingRetired() {
        // Given
        // Data loaded from XML

        // When
        final List<License> licenses = dao.getAllLicenses(true);

        // Then
        assertThat(licenses, notNullValue());
        assertThat(licenses.size(), is(7));
        assertThat(licenses, contains(hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000001")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000002")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000003")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000004")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000005")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000006")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000007"))));
    }

    @Test
    public void getAllLicenses_false_shouldReturnNonRetiredLicenses() {
        // Given
        // Data loaded from XML

        // When
        final List<License> licenses = dao.getAllLicenses(false);

        // Then
        assertThat(licenses, notNullValue());
        assertThat(licenses.size(), is(6));
        assertThat(licenses, contains(hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000001")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000002")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000004")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000005")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000006")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000007"))));
    }

    @Test
    public void getLicensesByType_shouldReturnNonRetiredLicensesWithType() {
        // Given
        // Data loaded from XML

        // When
        final List<License> licenses = dao.getLicensesByType(typeDAO.getLicenseType("Iris Scan"));

        // Then
        assertThat(licenses, notNullValue());
        assertThat(licenses.size(), is(3));
        assertThat(licenses, contains(hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000001")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000004")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000006"))));
    }

    @Test
    public void saveLicense_shouldSaveAllPropertiesInDb() {
        //Given
        final LicenseType licenseType = new LicenseType();
        licenseType.setName("LicenseType1");

        final License license = new License();
        license.setName("License1");
        license.setDescription("First License");
        license.setSerialNo("b10a8db164e0754105b7a99be72e3fe5");
        license.setOnline("online");
        license.setLicenseType(licenseType);

        //When
        typeDAO.saveLicenseType(licenseType);
        dao.saveLicense(license);

        //Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.clearSession();

        //Then
        final License savedLicense = dao.getLicenseByUuid(license.getUuid());

        assertThat(savedLicense, hasProperty("licenseId", notNullValue()));
        assertThat(savedLicense, hasProperty("uuid", is(savedLicense.getUuid())));
        assertThat(savedLicense, hasProperty("name", is(savedLicense.getName())));
        assertThat(savedLicense, hasProperty("description", is(savedLicense.getDescription())));
        assertThat(savedLicense, hasProperty("serialNo", is(savedLicense.getSerialNo())));
        assertThat(savedLicense, hasProperty("online", is(savedLicense.getOnline())));
        assertThat(savedLicense, hasProperty("licenseType", is(savedLicense.getLicenseType())));
    }

    @Test
    public void deleteLicense_shouldDeleteLicense() {
        // Given
        // Data loaded from XML

        // When
        dao.deleteLicense(dao.getLicense(1));

        // Then
        final List<License> afterDelete = dao.getAllLicenses(true);

        assertThat(afterDelete, notNullValue());
        assertThat(afterDelete.size(), is(6));
        assertThat(afterDelete, contains(hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000002")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000003")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000004")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000005")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000006")),
                hasProperty("uuid", is("f07ba61b-1000-0000-0000-000000000007"))));
    }

    @Test
    public void getDeviceAttributeWithLicense_shouldReturnDeviceAttributesWithCorrectLicenseType() {
        // Given
        // Data loaded from XML

        // When
        final List<DeviceAttribute> deviceAttributes =
                dao.getDeviceAttributeWithLicense(typeDAO.getLicenseType("Iris Scan"));

        // Then
        assertThat(deviceAttributes, notNullValue());
        assertThat(deviceAttributes.size(), is(2));

        // Load the value
        for (final DeviceAttribute deviceAttribute : deviceAttributes) {
            deviceAttribute.getValue();
        }

        assertThat(deviceAttributes, contains(hasProperty("value", hasProperty("name", is("IRIS1"))),
                hasProperty("value", hasProperty("name", is("IRIS3")))));
    }

    @Test
    public void getLicenseNotLinkedByDeviceAttribute_shouldReturnLicensesNotUsedInAttributes() {
        // Given
        // Data loaded from XML

        // When
        final List<License> licenses = dao.getLicenseNotLinkedByDeviceAttribute(typeDAO.getLicenseType("Finger Scan"));

        // Then
        assertThat(licenses, notNullValue());
        assertThat(licenses.size(), is(2));
        assertThat(licenses, containsInAnyOrder(
                hasProperty("name", is("FINGER3")),
                 hasProperty("name", is("FINGER2"))
        )
        );
    }
}
