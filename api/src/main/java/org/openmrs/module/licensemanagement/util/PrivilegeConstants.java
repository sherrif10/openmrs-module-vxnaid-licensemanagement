/*
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

package org.openmrs.module.licensemanagement.util;

import org.openmrs.annotation.AddOnStartup;
import org.openmrs.annotation.HasAddOnStartupPrivileges;

/**
 * Contains all License Management privilege names and their descriptions.
 */
@HasAddOnStartupPrivileges
public class PrivilegeConstants {

    @AddOnStartup(description = "Able to get devices")
    public static final String GET_DEVICES = "Get Devices";

    @AddOnStartup(description = "Able to add/edit/delete devices")
    public static final String MANAGE_DEVICES = "Manage Devices";

    @AddOnStartup(description = "Able to permanently delete devices")
    public static final String PURGE_DEVICES = "Purge Devices";

    @AddOnStartup(description = "Able to get device attribute types")
    public static final String GET_DEVICE_ATTRIBUTE_TYPES = "Get Device Attribute Types";

    @AddOnStartup(description = "Able to add/edit/delete device attribute types")
    public static final String MANAGE_DEVICE_ATTRIBUTE_TYPES = "Manage Device Attribute Types";

    @AddOnStartup(description = "Able to permanently delete device attribute types")
    public static final String PURGE_DEVICE_ATTRIBUTE_TYPES = "Purge Device Attribute Types";

    @AddOnStartup(description = "Able to get licenses")
    public static final String GET_LICENSES = "Get Licenses";

    @AddOnStartup(description = "Able to add/edit/delete licenses")
    public static final String MANAGE_LICENSES = "Manage Licenses";

    @AddOnStartup(description = "Able to permanently delete licenses")
    public static final String PURGE_LICENSES = "Purge Licenses";

    @AddOnStartup(description = "Able to get license types")
    public static final String GET_LICENSE_TYPES = "Get License Types";

    @AddOnStartup(description = "Able to add/edit/delete license types")
    public static final String MANAGE_LICENSE_TYPES = "Manage License Types";

    @AddOnStartup(description = "Able to permanently delete license types")
    public static final String PURGE_LICENSE_TYPES = "Purge License Types";

    private PrivilegeConstants() {
    }
}
