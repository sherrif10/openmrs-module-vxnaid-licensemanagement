<%@ include file="/WEB-INF/template/include.jsp" %>

<%@ attribute name="formFieldName" required="true" %>
<%@ attribute name="licenseTypes" required="true" type="java.util.List" %>
<%@ attribute name="initialValue" required="false" %>
<%@ attribute name="optionHeader" required="false" %>

<%--
  ~ This Source Code Form is subject to the terms of the Mozilla Public License,
  ~ v. 2.0. If a copy of the MPL was not distributed with this file, You can
  ~ obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
  ~ the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
  ~ <p>
  ~ Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
  ~ graphic logo is a trademark of OpenMRS Inc.
  --%>

<c:if test="${empty licenseTypes}">
    <openmrs:message code="licensemanagement.licenseType.list.empty"/>
</c:if>
<c:if test="${not empty licenseTypes}">
    <select name="${formFieldName}">
        <c:if test="${optionHeader != ''}">
            <c:if test="${optionHeader == '[blank]'}">
                <option value=""></option>
            </c:if>
            <c:if test="${optionHeader != '[blank]'}">
                <option value="">${optionHeader}</option>
            </c:if>
        </c:if>
        <c:forEach var="licenseType" items="${licenseTypes}">
            <option value="${licenseType.licenseTypeId}"
                    <c:if test="${licenseType.licenseTypeId == initialValue}">selected</c:if>>${licenseType.name}</option>
        </c:forEach>
    </select>
</c:if>
