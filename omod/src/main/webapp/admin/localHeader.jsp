<%--
  ~ This Source Code Form is subject to the terms of the Mozilla Public License,
  ~ v. 2.0. If a copy of the MPL was not distributed with this file, You can
  ~ obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
  ~ the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
  ~ <p>
  ~ Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
  ~ graphic logo is a trademark of OpenMRS Inc.
  --%>

<ul id="menu">
    <li class="first">
        <a href="${pageContext.request.contextPath}/admin/index.htm"><openmrs:message code="admin.title.short"/></a>
    </li>

    <openmrs:hasPrivilege privilege="Manage License Types,Get License Types">
        <li id="licensemanagement-manageLicenseTypes"
            <c:if test='<%= request.getRequestURI().contains("licenseTypeList") %>'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/module/licensemanagement/admin/licenseType.list">
                <openmrs:message code="licensemanagement.licenseTypes.manage.title"/>
            </a>
        </li>
    </openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="Manage Licenses,Get Licenses">
        <li id="licensemanagement-manageLicenses"
            <c:if test='<%= request.getRequestURI().contains("licenseList") %>'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/module/licensemanagement/admin/license.list">
                <openmrs:message code="licensemanagement.licenses.manage.title"/>
            </a>
        </li>
    </openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="Manage Device Attribute Types,Get Device Attribute Types">
        <li id="licensemanagement-manageDeviceAttributeTypes"
            <c:if test='<%= request.getRequestURI().contains("deviceAttributeTypeList") %>'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/module/licensemanagement/admin/deviceAttributeType.list">
                <openmrs:message code="licensemanagement.deviceAttributeTypes.manage.title"/>
            </a>
        </li>
    </openmrs:hasPrivilege>

    <openmrs:hasPrivilege privilege="Manage Devices,Get Devices">
        <li id="licensemanagement-manageDevices"
            <c:if test='<%= request.getRequestURI().contains("deviceList") %>'>class="active"</c:if>>
            <a href="${pageContext.request.contextPath}/module/licensemanagement/admin/device.list">
                <openmrs:message code="licensemanagement.devices.manage.title"/>
            </a>
        </li>
    </openmrs:hasPrivilege>
</ul>
