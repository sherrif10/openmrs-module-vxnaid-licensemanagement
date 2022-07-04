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
import org.openmrs.customdatatype.datatype.BooleanDatatype;
import org.openmrs.module.licensemanagement.Device;
import org.openmrs.module.licensemanagement.DeviceAttribute;
import org.openmrs.module.licensemanagement.DeviceAttributeType;
import org.openmrs.module.licensemanagement.License;
import org.openmrs.module.licensemanagement.api.dao.DeviceDAO;
import org.openmrs.module.licensemanagement.api.dao.LicenseDAO;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class DeviceDAOTest extends BaseModuleContextSensitiveTest {

    private static final String INITIAL_DATA_XML = "org/openmrs/module/licensemanagement/dao/DeviceDAOTest.xml";

    @Autowired
    DeviceDAO dao;

    @Autowired
    LicenseDAO licenseDAO;

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
    public void getDevice_shouldReturnCorrectDeviceWithAttributes() {
        // Given
        // Data loaded from XML

        // When
        final Device second = dao.getDevice(2);

        // Then
        assertThat(second, notNullValue());
        assertThat(second, hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000002")));

        assertThat(second.getAttributes(), notNullValue());
        assertThat(second.getAttributes().size(), is(2));

        // Load values
        for (final DeviceAttribute attribute : second.getAttributes()) {
            attribute.getValue();
        }

        assertThat(second.getAttributes(), contains(hasProperty("value", is(licenseDAO.getLicense("IRIS2"))),
                hasProperty("value", is("Mobile System XYZ"))));
    }

    @Test
    public void getDevice_name_shouldReturnCorrectDevice() {
        // Given
        // Data loaded from XML

        // When
        final Device first = dao.getDevice("Device 1");

        // Then
        assertThat(first, notNullValue());
        assertThat(first, hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000001")));
    }

    @Test
    public void getDeviceByUuid_shouldReturnCorrectDevice() {
        // Given
        // Data loaded from XML

        // When
        final Device third = dao.getDeviceByUuid("f08ba64b-2000-0000-0000-000000000003");

        // Then
        assertThat(third, notNullValue());
        assertThat(third, hasProperty("id", is(3)));
    }

    @Test
    public void getDeviceByMac_shouldReturnCorrectDevice() {
        // Given
        // Data loaded from XML

        // When
        final Device first = dao.getDeviceByMac("AA:00:00:00:00:01");

        // Then
        assertThat(first, notNullValue());
        assertThat(first, hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000001")));
    }


    @Test
    public void getDeviceByMac_shouldReturnNonRetiredCorrectDevice() {
        // Given
        // Data loaded from XML

        // When
        final Device first = dao.getDeviceByMac("AA:00:00:00:00:01", false);

        // Then
        assertThat(first, notNullValue());
        assertThat(first, hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000001")));
    }

    @Test
    public void getAllDevices_true_shouldReturnDevicesIncludingRetired() {
        // Given
        // Data loaded from XML

        // When
        final List<Device> devices = dao.getAllDevices(true);

        // Then
        assertThat(devices, notNullValue());
        assertThat(devices.size(), is(3));
        assertThat(devices, contains(hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000002")),
                hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000003"))));
    }

    @Test
    public void getAllDevices_false_shouldReturnNonRetiredDevices() {
        // Given
        // Data loaded from XML

        // When
        final List<Device> devices = dao.getAllDevices(false);

        // Then
        assertThat(devices, notNullValue());
        assertThat(devices.size(), is(2));
        assertThat(devices, contains(hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000002"))));
    }

    @Test
    public void getDeviceByNameFragment_shouldReturnCorrectDevices() {
        // Given
        // Data loaded from XML

        // When
        final List<Device> xDevices = dao.getDeviceByNameFragment("XDevice");

        // Then
        assertThat(xDevices, notNullValue());
        assertThat(xDevices.size(), is(1));
        assertThat(xDevices, contains(hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000002"))));
    }

    @Test
    public void saveDevice_shouldSaveAllPropertiesInDb() {
        //Given
        final Device device = new Device();
        device.setName("NewDevice1");
        device.setDescription("New First Device");
        device.setDeviceMac("00:1B:44:11:3A:B7");

        //When
        dao.saveDevice(device);

        //Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.clearSession();

        //Then
        final Device savedDevice = dao.getDeviceByUuid(device.getUuid());

        assertThat(savedDevice, hasProperty("deviceId", notNullValue()));
        assertThat(savedDevice, hasProperty("uuid", is(device.getUuid())));
        assertThat(savedDevice, hasProperty("name", is(device.getName())));
        assertThat(savedDevice, hasProperty("description", is(device.getDescription())));
        assertThat(savedDevice, hasProperty("deviceMac", is(device.getDeviceMac())));
    }

    @Test
    public void deleteDevice_shouldDeleteDevice() {
        // Given
        // Data loaded from XML

        // When
        dao.deleteDevice(dao.getDevice(1));

        // Then
        final List<Device> afterDelete = dao.getAllDevices(true);

        assertThat(afterDelete, notNullValue());
        assertThat(afterDelete.size(), is(2));
        assertThat(afterDelete, contains(hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000002")),
                hasProperty("uuid", is("f08ba64b-2000-0000-0000-000000000003"))));
    }

    @Test
    public void getDeviceAttributeType_shouldReturnCorrectDeviceAttributeType() {
        // Given
        // Data loaded from XML

        // When
        final DeviceAttributeType second = dao.getDeviceAttributeType(2);

        // Then
        assertThat(second, notNullValue());
        assertThat(second, hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000002")));
    }

    @Test
    public void getDeviceAttributeType_name_shouldReturnCorrectDeviceAttributeType() {
        // Given
        // Data loaded from XML

        // When
        final DeviceAttributeType first = dao.getDeviceAttributeType("Iris Scan License");

        // Then
        assertThat(first, notNullValue());
        assertThat(first, hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000001")));
    }

    @Test
    public void getDeviceAttributeTypeByUuid_shouldReturnCorrectDeviceAttributeType() {
        // Given
        // Data loaded from XML

        // When
        final DeviceAttributeType third = dao.getDeviceAttributeTypeByUuid("f08ba64b-3000-0000-0000-000000000003");

        // Then
        assertThat(third, notNullValue());
        assertThat(third, hasProperty("id", is(3)));
    }

    @Test
    public void getAllDeviceAttributeTypes_true_shouldReturnDeviceAttributeTypesIncludingRetired() {
        // Given
        // Data loaded from XML

        // When
        final List<DeviceAttributeType> deviceAttributeTypes = dao.getAllDeviceAttributeTypes(true);

        // Then
        assertThat(deviceAttributeTypes, notNullValue());
        assertThat(deviceAttributeTypes.size(), is(4));
        assertThat(deviceAttributeTypes, contains(hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000002")),
                hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000003")),
                hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000004"))));
    }

    @Test
    public void getAllDeviceAttributeTypes_false_shouldReturnNonRetiredDeviceAttributeTypes() {
        // Given
        // Data loaded from XML

        // When
        final List<DeviceAttributeType> deviceAttributeTypes = dao.getAllDeviceAttributeTypes(false);

        // Then
        assertThat(deviceAttributeTypes, notNullValue());
        assertThat(deviceAttributeTypes.size(), is(3));
        assertThat(deviceAttributeTypes, contains(hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000002")),
                hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000004"))));
    }

    @Test
    public void saveDeviceAttributeType_shouldSaveAllPropertiesInDb() {
        //Given
        final DeviceAttributeType deviceAttributeType = new DeviceAttributeType();
        deviceAttributeType.setName("NewDeviceAttributeType1");
        deviceAttributeType.setDescription("New First DeviceAttributeType");
        deviceAttributeType.setDatatypeClassname(BooleanDatatype.class.getName());
        deviceAttributeType.setUuid("0d773b11-e0a4-4f49-a21e-232d719bbec4");

        //When
        dao.saveDeviceAttributeType(deviceAttributeType);

        //Let's clean up the cache to be sure getItemByUuid fetches from DB and not from cache
        Context.flushSession();
        Context.clearSession();

        //Then
        final DeviceAttributeType savedType = dao.getDeviceAttributeTypeByUuid(deviceAttributeType.getUuid());

        assertThat(savedType, hasProperty("deviceAttributeTypeId", notNullValue()));
        assertThat(savedType, hasProperty("uuid", is(deviceAttributeType.getUuid())));
        assertThat(savedType, hasProperty("name", is(deviceAttributeType.getName())));
        assertThat(savedType, hasProperty("description", is(deviceAttributeType.getDescription())));
        assertThat(savedType, hasProperty("datatypeClassname", is(deviceAttributeType.getDatatypeClassname())));
    }

    @Test
    public void deleteDeviceAttributeType_shouldDeleteDeviceAttributeType() {
        // Given
        // Data loaded from XML

        // When
        dao.deleteDeviceAttributeType(dao.getDeviceAttributeType(4));

        // Then
        final List<DeviceAttributeType> afterDelete = dao.getAllDeviceAttributeTypes(true);

        assertThat(afterDelete, notNullValue());
        assertThat(afterDelete.size(), is(3));
        assertThat(afterDelete, contains(hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000002")),
                hasProperty("uuid", is("f08ba64b-3000-0000-0000-000000000003"))));
    }

    @Test
    public void getDeviceAttributeByUuid_shouldReturnCorrectDeviceAttribute() {
        // Given
        // Data loaded from XML

        // When
        final DeviceAttribute fourth = dao.getDeviceAttributeByUuid("f08ba64b-4000-0000-0000-000000000004");

        // Then
        assertThat(fourth, notNullValue());
        assertThat(fourth, hasProperty("valueReference", is("Mobile System XYZ")));
    }

    @Test
    public void getDeviceAttributeByDeviceAndType_shouldReturnCorrectDeviceAttribute() {
        // Given
        // Data loaded from XML

        // When
        final Device firstDevice = dao.getDevice(1);
        final DeviceAttributeType deviceAttributeType = dao.getDeviceAttributeType("Iris Scan License");
        final DeviceAttribute irisScanLicenseOfFirstDevice =
                dao.getDeviceAttributeByDeviceAndType(firstDevice, deviceAttributeType);

        // Then
        assertThat(irisScanLicenseOfFirstDevice, notNullValue());
        assertThat(irisScanLicenseOfFirstDevice, hasProperty("uuid", is("f08ba64b-4000-0000-0000-000000000001")));

        final License iris1License = licenseDAO.getLicense("IRIS1");
        assertThat(irisScanLicenseOfFirstDevice.getValue(), is(instanceOf(License.class)));
        assertThat(irisScanLicenseOfFirstDevice.getValue(), hasProperty("serialNo", is(iris1License.getSerialNo())));
    }

    @Test
    public void getDeviceAttributes_shouldReturnAllAttributesOfSingleType() {
        // Given
        // Data loaded from XML

        // When
        final List<DeviceAttribute> assignedIrisLicenses =
                dao.getDeviceAttributes(dao.getDeviceAttributeType("Iris Scan License"));

        // Then
        assertThat(assignedIrisLicenses, notNullValue());
        assertThat(assignedIrisLicenses.size(), is(3));
        assertThat(assignedIrisLicenses, contains(hasProperty("uuid", is("f08ba64b-4000-0000-0000-000000000001")),
                hasProperty("uuid", is("f08ba64b-4000-0000-0000-000000000003")),
                hasProperty("uuid", is("f08ba64b-4000-0000-0000-000000000005"))));

        // Load value
        for (DeviceAttribute attribute : assignedIrisLicenses) {
            attribute.getValue();
        }

        assertThat(assignedIrisLicenses, contains(hasProperty("value", is(licenseDAO.getLicense("IRIS1"))),
                hasProperty("value", is(licenseDAO.getLicense("IRIS2"))),
                hasProperty("value", is(licenseDAO.getLicense("IRIS3")))));
    }

    @Test
    public void getDeviceCount_shouldGetDeviceCountIncludingRetiredDevices() {
        // When
        final long count = dao.getDeviceCount(true);
        assertThat(count,equalTo(3L));
    }

    @Test
    public void getDeviceCount_shouldGetDeviceCountExcludingRetiredDevices() {
        // When
        final long count = dao.getDeviceCount(false);
        assertThat(count,equalTo(2L));
    }
}
