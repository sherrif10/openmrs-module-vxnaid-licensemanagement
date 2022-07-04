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

<openmrs:require privilege="Get License Types" otherwise="/login.htm"
                 redirect="/module/licensemanagement/admin/licenseType.list"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<h2><spring:message code="licensemanagement.licenseTypes.manage.title"/></h2>

<a href="licenseType.form"><openmrs:message code="licensemanagement.licenseType.add"/></a>

<br/><br/>

<b class="boxHeader"><openmrs:message code="licensemanagement.licenseType.list.title"/></b>
<form method="post" class="box">
    <table>
        <tr>
            <th><openmrs:message code="general.name"/></th>
            <th><openmrs:message code="general.description"/></th>
        </tr>
        <c:forEach var="licenseType" items="${licenseTypes}">
            <tr>
                <td valign="top">
                    <a href="licenseType.form?licenseTypeId=${licenseType.licenseTypeId}">
                        <c:choose>
                            <c:when test="${licenseType.retired == true}">
                                <del><c:out value="${licenseType.name}"/></del>
                            </c:when>
                            <c:otherwise>
                                <c:out value="${licenseType.name}"/>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </td>
                <td valign="top"><c:out value="${licenseType.description}"/></td>
            </tr>
        </c:forEach>
    </table>
</form>

<%@ include file="/WEB-INF/template/footer.jsp" %>
