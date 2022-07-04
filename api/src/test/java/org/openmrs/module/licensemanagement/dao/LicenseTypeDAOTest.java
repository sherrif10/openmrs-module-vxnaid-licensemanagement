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
import org.openmrs.module.licensemanagement.LicenseType;
import org.openmrs.module.licensemanagement.api.dao.LicenseTypeDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class LicenseTypeDAOTest extends BaseModuleContextSensitiveTest {
    private static final String INITIAL_DATA_XML = "org/openmrs/module/licensemanagement/dao/LicenseTypeDAOTest.xml";

    @Autowired
    LicenseTypeDAO dao;

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
    public void getLicenseType_id_shouldReturnCorrectLicenseTypes() {
        // Given
        // Data loaded from XML

        // When
        final LicenseType secondType = dao.getLicenseType(2);

        // Then
        assertThat(secondType, notNullValue());
        assertThat(secondType, hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000001")));
    }

    @Test
    public void getLicenseType_name_shouldReturnCorrectLicenseTypes() {
        // Given
        // Data loaded from XML

        // When
        final LicenseType firstType = dao.getLicenseType("Iris Scan");

        // Then
        assertThat(firstType, notNullValue());
        assertThat(firstType, hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000000")));
    }

    @Test
    public void getLicenseTypeByUuid_shouldReturnCorrectLicenseTypes() {
        // Given
        // Data loaded from XML

        // When
        final LicenseType thirdType = dao.getLicenseTypeByUuid("f08ba61b-0000-0000-0000-000000000002");

        // Then
        assertThat(thirdType, notNullValue());
        assertThat(thirdType, hasProperty("id", is(3)));
    }

    @Test
    public void getAllLicenseTypes_true_shouldReturnLicenseTypesIncludingRetired() {
        // Given
        // Data loaded from XML

        // When
        final List<LicenseType> types = dao.getAllLicenseTypes(true);

        // Then
        assertThat(types, notNullValue());
        assertThat(types.size(), is(3));
        assertThat(types, contains(hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000000")),
                hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000002"))));
    }

    @Test
    public void getAllLicenseTypes_false_shouldReturnNonRetiredLicenseTypes() {
        // Given
        // Data loaded from XML

        // When
        final List<LicenseType> types = dao.getAllLicenseTypes(false);

        // Then
        assertThat(types, notNullValue());
        assertThat(types.size(), is(2));
        assertThat(types, contains(hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000000")),
                hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000001"))));
    }

    @Test
    public void saveLicenseType_shouldSaveAllPropertiesInDb() {
        //Given
        final LicenseType licenseType = new LicenseType();
        licenseType.setName("LicenseType1");
        licenseType.setDescription("First LicenseType");

        //When
        dao.saveLicenseType(licenseType);

        //Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.clearSession();

        //Then
        final LicenseType savedLicenseType = dao.getLicenseTypeByUuid(licenseType.getUuid());

        assertThat(savedLicenseType, hasProperty("licenseTypeId", notNullValue()));
        assertThat(savedLicenseType, hasProperty("uuid", is(licenseType.getUuid())));
        assertThat(savedLicenseType, hasProperty("name", is(licenseType.getName())));
        assertThat(savedLicenseType, hasProperty("description", is(licenseType.getDescription())));
    }

    @Test
    public void deleteLicenseType_shouldDeleteLicenseType() {
        // Given
        // Data loaded from XML

        // When
        dao.deleteLicenseType(dao.getLicenseType(1));

        // Then
        final List<LicenseType> afterDeleteTypes = dao.getAllLicenseTypes(true);

        assertThat(afterDeleteTypes, notNullValue());
        assertThat(afterDeleteTypes.size(), is(2));
        assertThat(afterDeleteTypes, contains(hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba61b-0000-0000-0000-000000000002"))));
    }
}
