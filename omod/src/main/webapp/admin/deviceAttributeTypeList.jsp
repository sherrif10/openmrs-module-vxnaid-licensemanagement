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

<openmrs:require privilege="Get Device Attribute Types" otherwise="/login.htm"
                 redirect="/module/licensemanagement/admin/deviceAttributeType.list"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><spring:message code="licensemanagement.deviceAttributeTypes.manage.title"/></h2>

<a href="deviceAttributeType.form"><openmrs:message code="licensemanagement.deviceAttributeType.add"/></a>

<br/><br/>

<b class="boxHeader"><openmrs:message code="licensemanagement.deviceAttributeType.list.title"/></b>
<form method="post" class="box">
    <table>
        <tr>
            <th><openmrs:message code="general.name"/></th>
            <th><openmrs:message code="general.description"/></th>
            <th><openmrs:message code="licensemanagement.deviceAttributeType.datatypeClassname"/></th>
        </tr>
        <c:forEach var="deviceAttributeType" items="${deviceAttributeTypes}">
            <tr>
                <td valign="top">
                    <a href="deviceAttributeType.form?deviceAttributeTypeId=${deviceAttributeType.deviceAttributeTypeId}">
                        <c:choose>
                            <c:when test="${deviceAttributeType.retired == true}">
                                <del><c:out value="${deviceAttributeType.name}"/></del>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${deviceAttributeType.name}"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </td>
                <td valign="top"><c:out value="${deviceAttributeType.description}"/></td>
                <td valign="top">
                    <openmrs:message code="${deviceAttributeType.datatypeClassname}.name"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>
