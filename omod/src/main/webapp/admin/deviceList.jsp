<%@ include file="/WEB-INF/template/include.jsp" %>

<%--
  ~ This Source Code Form is subject to the terms of the Mozilla Public License,
  ~ v. 2.0. If a copy of the MPL was not distributed with this file, You can
  ~ obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
  ~ the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
  ~ <p>
  ~ Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
  ~ graphic logo is a trademark of OpenMRS Inc.
  --%>

<openmrs:require privilege="Get Devices" otherwise="/login.htm"
                 redirect="/module/licensemanagement/admin/device.list"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><spring:message code="licensemanagement.devices.manage.title"/></h2>

<a href="device.form"><openmrs:message code="licensemanagement.device.add"/></a>

<br/><br/>

<b class="boxHeader"><openmrs:message code="licensemanagement.device.list.title"/></b>
<form method="post" class="box">
    <table>
        <tr>
            <th><openmrs:message code="general.name"/></th>
            <th><openmrs:message code="general.description"/></th>
            <th><openmrs:message code="licensemanagement.device.deviceMac"/></th>
        </tr>
        <c:forEach var="device" items="${devices}">
            <tr>
                <td valign="top">
                    <a href="device.form?deviceId=${device.deviceId}">
                        <c:choose>
                            <c:when test="${device.retired == true}">
                                <del><c:out value="${device.name}"/></del>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${device.name}"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </td>
                <td valign="top"><c:out value="${device.description}"/></td>
                <td valign="top"><c:out value="${device.deviceMac}"/></td>
            </tr>
        </c:forEach>
    </table>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>
