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

<openmrs:require privilege="Get Devices" otherwise="/login.htm" redirect="/admin/licensemanagement/device.list"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<script type="text/javascript">

    function confirmPurge() {
        if (confirm("<openmrs:message code='licensemanagement.device.purgeConfirmMessage' />")) {
            return true;
        } else {
            return false;
        }
    }

</script>

<h2><openmrs:message code="licensemanagement.device.title"/></h2>

<spring:hasBindErrors name="device">
    <openmrs:message htmlEscape="false" code="fix.error"/>
    <br/>
</spring:hasBindErrors>

<form method="post">
    <fieldset>
        <table class="left-aligned-th">
            <tr>
                <th><openmrs:message code="general.name"/><span class="required">*</span></th>
                <td>
                    <spring:bind path="device.name">
                        <input type="text" name="name" value="${status.value}" size="35"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="general.description"/></th>
                <td valign="top">
                    <spring:bind path="device.description">
                    <textarea name="description" rows="3" cols="40"
                              onkeypress="return forceMaxLength(this, 255);">${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="licensemanagement.device.deviceMac"/></th>
                <td valign="top">
                    <spring:bind path="device.deviceMac">
                        <input type="text" name="deviceMac" value="${status.value}" size="35"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="licensemanagement.device.deviceLinux"/></th>
                <td valign="top">
                    <spring:bind path="device.deviceLinux">
                        <input type="text" name="deviceLinux" value="${status.value}" size="35"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="licensemanagement.device.deviceWindows"/></th>
                <td valign="top">
                    <spring:bind path="device.deviceWindows">
                        <input type="text" name="deviceWindows" value="${status.value}" size="35"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <spring:bind path="device.activeAttributes">
                <c:if test="${status.error}">
                    <tr>
                        <th></th>
                        <td colspan="5">
                        <span class="error">
                            <c:forEach var="err" items="${status.errorMessages}">
                                ${ err }<br/>
                            </c:forEach>
                        </span>
                        </td>
                    </tr>
                </c:if>
            </spring:bind>
            <c:forEach var="attrType" items="${ attributeTypes }">
                <c:if test="${ !attrType.retired }">
                    <openmrs_tag:attributesForType attributeType="${ attrType }" customizable="${ device }"
                                                   formFieldNamePrefix="attribute.${ attrType.id }"/>
                </c:if>
            </c:forEach>
            <c:if test="${!(device.creator == null)}">
                <tr>
                    <th><openmrs:message code="general.createdBy"/></th>
                    <td><openmrs:format user="${ device.creator }"/></td>
                </tr>
            </c:if>
            <tr>
                <c:if test="${device.deviceId != null}">
                    <th><font color="#D0D0D0"><sub><openmrs:message code="general.uuid"/></sub></font></th>
                    <td colspan="${fn:length(locales)}"><font color="#D0D0D0"><sub>
                        <spring:bind path="device.uuid">
                            ${status.value}
                        </spring:bind></sub></font>
                    </td>
                </c:if>
            </tr>
        </table>

        <input type="submit" value="<openmrs:message code="licensemanagement.device.save"/>" name="save">

        <br/>
    </fieldset>
</form>

<br/>

<c:if test="${not device.retired && not empty device.deviceId}">
    <form method="post">
        <fieldset>
            <h4><openmrs:message code="licensemanagement.device.retire"/></h4>

            <b><openmrs:message code="general.reason"/></b>
            <input type="text" value="" size="40" name="retireReason"/>
            <spring:hasBindErrors name="device">
                <c:forEach items="${errors.allErrors}" var="error">
                    <c:if test="${error.code == 'retireReason'}"><span class="error"><openmrs:message
                            code="${error.defaultMessage}" text="${error.defaultMessage}"/></span></c:if>
                </c:forEach>
            </spring:hasBindErrors>
            <br/>
            <input type="submit" value='<openmrs:message code="licensemanagement.device.retire"/>' name="retire"/>
        </fieldset>
    </form>
</c:if>

<c:if test="${device.retired && not empty device.deviceId}">
    <form method="post">
        <fieldset>
            <h4><openmrs:message code="licensemanagement.device.unretire"/></h4>
            <input type="submit" value='<openmrs:message code="licensemanagement.device.unretire"/>' name="unretire"/>
        </fieldset>
    </form>
</c:if>

<br/>

<c:if test="${not empty device.deviceId}">
    <openmrs:hasPrivilege privilege="Manage Devices">
        <form id="purge" method="post" onsubmit="return confirmPurge()">
            <fieldset>
                <h4><openmrs:message code="licensemanagement.device.purge"/></h4>
                <input type="submit" value='<openmrs:message code="licensemanagement.device.purge"/>' name="purge"/>
            </fieldset>
        </form>
    </openmrs:hasPrivilege>
</c:if>

<%@ include file="/WEB-INF/template/footer.jsp" %>
