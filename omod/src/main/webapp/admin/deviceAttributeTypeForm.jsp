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
                 redirect="/admin/licensemanagement/deviceAttributeType.list"/>

<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<script type="text/javascript">
    function confirmPurge() {
        if
        (confirm("Are you sure you want to purge this device attribute type? It will be permanently removed from the system.")) {
            return true;
        } else {
            return false;
        }
    }

    function forceMaxLength(object, maxLength) {
        if (object.value.length >= maxLength) {
            object.value = object.value.substring(0, maxLength);
        }
    }

    $j(function () {
        $j('select[name="datatypeClassname"]').change(function () {
            $j('#datatypeDescription').load(openmrsContextPath + '/q/message.form', {key: $j(this).val() + '.description'});
        });
        $j('select[name="preferredHandlerClassname"]').change(function () {
            $j('#handlerDescription').load(openmrsContextPath + '/q/message.form', {key: $j(this).val() + '.description'});
        });
        <c:if test="${ not empty deviceAttributeType.datatypeClassname }">
        $j('#datatypeDescription').load(openmrsContextPath + '/q/message.form', {key: '${ deviceAttributeType.datatypeClassname }.description'});
        </c:if>
        <c:if test="${ not empty deviceAttributeType.preferredHandlerClassname }">
        $j('#handlerDescription').load(openmrsContextPath + '/q/message.form', {key: '${ deviceAttributeType.preferredHandlerClassname }.description'});
        </c:if>
    });
</script>


<h2><openmrs:message code="licensemanagement.deviceAttributeType.title"/></h2>

<spring:hasBindErrors name="deviceAttributeType">
    <openmrs:message htmlEscape="false" code="fix.error"/>
    <br/>
</spring:hasBindErrors>
<form method="post">
    <fieldset>
        <table class="left-aligned-th">
            <tr>
                <th><openmrs:message code="general.name"/><span class="required">*</span></th>
                <td>
                    <spring:bind path="deviceAttributeType.name">
                        <input type="text" name="name" value="${status.value}" size="35"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th valign="top"><openmrs:message code="general.description"/></th>
                <td valign="top">
                    <spring:bind path="deviceAttributeType.description">
                        <textarea name="description" rows="3" cols="40"
                                  onkeypress="return forceMaxLength(this, 255);">${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th><openmrs:message code="licensemanagement.deviceAttributeType.minOccurs"/></th>
                <td>
                    <spring:bind path="deviceAttributeType.minOccurs">
                        <input type="text" name="minOccurs" value="${status.value}" size="10"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th><openmrs:message code="licensemanagement.deviceAttributeType.maxOccurs"/></th>
                <td>
                    <spring:bind path="deviceAttributeType.maxOccurs">
                        <input type="text" name="maxOccurs" value="${status.value}" size="10"/>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th><openmrs:message code="licensemanagement.deviceAttributeType.datatypeClassname"/><span
                        class="required">*</span></th>
                <td>
                    <spring:bind path="deviceAttributeType.datatypeClassname">
                        <select name="datatypeClassname">
                            <option value=""></option>
                            <c:forEach items="${datatypes}" var="datatype">
                                <option value="${datatype}" <c:if test="${datatype == status.value}">selected</c:if>>
                                    <openmrs:message code="${datatype}.name"/></option>
                            </c:forEach>
                        </select>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                        <br/>
                        <span id="datatypeDescription"></span>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th><openmrs:message code="licensemanagement.deviceAttributeType.datatypeConfig"/></th>
                <td>
                    <spring:bind path="deviceAttributeType.datatypeConfig">
                        <textarea name="datatypeConfig" rows="3" cols="40">${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th><openmrs:message code="licensemanagement.deviceAttributeType.preferredHandlerClassname"/></th>
                <td>
                    <spring:bind path="deviceAttributeType.preferredHandlerClassname">
                        <select name="preferredHandlerClassname">
                            <option value=""><openmrs:message code="general.default"/></option>
                            <c:forEach items="${handlers}" var="handler">
                                <option value="${handler}" <c:if test="${handler == status.value}">selected</c:if>>
                                    <openmrs:message code="${handler}.name"/></option>
                            </c:forEach>
                        </select>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                        <br/>
                        <span id="handlerDescription"></span>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <th><openmrs:message code="licensemanagement.deviceAttributeType.handlerConfig"/></th>
                <td>
                    <spring:bind path="deviceAttributeType.handlerConfig">
                        <textarea name="handlerConfig" rows="3" cols="40">${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
                    </spring:bind>
                </td>
            </tr>
            <c:if test="${ not empty deviceAttributeType.creator }">
                <tr>
                    <th><openmrs:message code="general.createdBy"/></th>
                    <td>
                        <c:out value="${deviceAttributeType.creator.personName}"/> -
                        <openmrs:formatDate date="${deviceAttributeType.dateCreated}" type="long"/>
                    </td>
                </tr>
            </c:if>
            <c:if test="${deviceAttributeType.deviceAttributeTypeId != null}">
                <tr>
                    <th><font color="#D0D0D0"><sub><openmrs:message code="general.uuid"/></sub></font></th>
                    <td colspan="${fn:length(locales)}">
                        <font color="#D0D0D0"><sub>
                            <spring:bind path="deviceAttributeType.uuid">
                                ${status.value}
                            </spring:bind>
                        </sub></font>
                    </td>
                </tr>
            </c:if>
        </table>
        <br/>

        <input type="submit" value="<openmrs:message code="licensemanagement.deviceAttributeType.save"/>" name="save">

    </fieldset>
</form>

<br/>

<c:if test="${ not deviceAttributeType.retired && not empty deviceAttributeType.id }">
    <form method="post">
        <fieldset>
            <h4><openmrs:message code="licensemanagement.deviceAttributeType.retire"/></h4>

            <b><openmrs:message code="general.reason"/></b>
            <input type="text" value="" size="40" name="retireReason"/>
            <spring:hasBindErrors name="deviceAttributeType">
                <c:forEach items="${ errors.allErrors }" var="error">
                    <c:if test="${ error.code == 'retireReason' }"><span class="error"><openmrs:message
                            code="${ error.defaultMessage }" text="${ error.defaultMessage }"/></span></c:if>
                </c:forEach>
            </spring:hasBindErrors>
            <br/>
            <input type="submit" value='<openmrs:message code="licensemanagement.deviceAttributeType.retire"/>'
                   name="retire"/>
        </fieldset>
    </form>
</c:if>

<c:if test="${deviceAttributeType.retired && not empty deviceAttributeType.deviceAttributeTypeId}">
    <form method="post">
        <fieldset>
            <h4><openmrs:message code="licensemanagement.deviceAttributeType.unretire"/></h4>
            <input type="submit" value='<openmrs:message code="licensemanagement.deviceAttributeType.unretire"/>'
                   name="unretire"/>
        </fieldset>
    </form>
</c:if>

<br/>

<c:if test="${ not empty deviceAttributeType.id }">
    <openmrs:hasPrivilege privilege="Purge Device Attribute Types">
        <form id="purge" method="post" onsubmit="return confirmPurge()">
            <fieldset>
                <h4><openmrs:message code="licensemanagement.deviceAttributeType.purge"/></h4>
                <input type="submit" value='<openmrs:message code="licensemanagement.deviceAttributeType.purge"/>'
                       name="purge"/>
            </fieldset>
        </form>
    </openmrs:hasPrivilege>
</c:if>

<%@ include file="/WEB-INF/template/footer.jsp" %>
