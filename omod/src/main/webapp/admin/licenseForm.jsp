<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ taglib prefix="vxnaidlicensemanagement" tagdir="/WEB-INF/tags/module/vxnaidlicensemanagement" %>

<%--
  ~ This Source Code Form is subject to the terms of the Mozilla Public License,
  ~ v. 2.0. If a copy of the MPL was not distributed with this file, You can
  ~ obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
  ~ the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
  ~ <p>
  ~ Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
  ~ graphic logo is a trademark of OpenMRS Inc.
  --%>

<openmrs:require privilege="Get Licenses" otherwise="/login.htm" redirect="/admin/licensemanagement/license.list"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<script type="text/javascript">

    function confirmPurge() {
        if (confirm("<openmrs:message code='licensemanagement.license.purgeConfirmMessage' />")) {
            return true;
        } else {
            return false;
        }
    }

</script>

<h2><openmrs:message code="licensemanagement.license.title"/></h2>

<spring:hasBindErrors name="license">
    <openmrs:message htmlEscape="false" code="fix.error"/>
    <br/>
</spring:hasBindErrors>

<form method="post">
    <fieldset>
        <table class="left-aligned-th">
            <tr>
                <th><openmrs:message code="general.name"/></th>
                <td>
                    <spring:bind path="license.name">
                        <input type="text" name="name" value="${status.value}" size="35"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="general.description"/></th>
                <td valign="top">
                    <spring:bind path="license.description">
                    <textarea name="description" rows="3" cols="40"
                              onkeypress="return forceMaxLength(this, 255);">${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="licensemanagement.license.serialNo"/></th>
                <td valign="top">
                    <spring:bind path="license.serialNo">
                    <textarea name="serialNo" rows="3" cols="40">${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="licensemanagement.license.online"/></th>
                <td valign="top">
                    <spring:bind path="license.online">
                        <textarea name="online" rows="3" cols="40">${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="licensemanagement.license.licenseType"/><span
                        class="required">*</span></th>
                <td>
                    <spring:bind path="license.licenseType">
                        <vxnaidlicensemanagement:licenseTypeField licenseTypes="${licenseTypes}" formFieldName="licenseType"
                                                            initialValue="${status.value}"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <c:if test="${!(license.creator == null)}">
                <tr>
                    <th><openmrs:message code="general.createdBy"/></th>
                    <td><openmrs:format user="${ license.creator }"/></td>
                </tr>
            </c:if>
            <tr>
                <c:if test="${license.licenseId != null}">
                    <th><font color="#D0D0D0"><sub><openmrs:message code="general.uuid"/></sub></font></th>
                    <td colspan="${fn:length(locales)}"><font color="#D0D0D0"><sub>
                        <spring:bind path="license.uuid">
                            ${status.value}
                        </spring:bind></sub></font>
                    </td>
                </c:if>
            </tr>
        </table>

        <input type="submit" value="<openmrs:message code="licensemanagement.license.save"/>" name="save">

        <br/>
    </fieldset>
</form>

<br/>

<c:if test="${not license.retired && not empty license.licenseId}">
    <form method="post">
        <fieldset>
            <h4><openmrs:message code="licensemanagement.license.retire"/></h4>

            <b><openmrs:message code="general.reason"/></b>
            <input type="text" value="" size="40" name="retireReason"/>
            <spring:hasBindErrors name="license">
                <c:forEach items="${errors.allErrors}" var="error">
                    <c:if test="${error.code == 'retireReason'}"><span class="error"><openmrs:message
                            code="${error.defaultMessage}" text="${error.defaultMessage}"/></span></c:if>
                </c:forEach>
            </spring:hasBindErrors>
            <br/>
            <input type="submit" value='<openmrs:message code="licensemanagement.license.retire"/>' name="retire"/>
        </fieldset>
    </form>
</c:if>

<c:if test="${license.retired && not empty license.licenseId}">
    <form method="post">
        <fieldset>
            <h4><openmrs:message code="licensemanagement.license.unretire"/></h4>
            <input type="submit" value='<openmrs:message code="licensemanagement.license.unretire"/>' name="unretire"/>
        </fieldset>
    </form>
</c:if>

<br/>

<c:if test="${not empty license.licenseId}">
    <openmrs:hasPrivilege privilege="Manage Licenses">
        <form id="purge" method="post" onsubmit="return confirmPurge()">
            <fieldset>
                <h4><openmrs:message code="licensemanagement.license.purge"/></h4>
                <input type="submit" value='<openmrs:message code="licensemanagement.license.purge"/>' name="purge"/>
            </fieldset>
        </form>
    </openmrs:hasPrivilege>
</c:if>

<%@ include file="/WEB-INF/template/footer.jsp" %>
