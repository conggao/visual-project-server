<#assign n=0/>
<#list comList?if_exists as root>
<#assign n=n+1/>
<#if root.childrens?exists && (root.childrens?size>0)>
<#--  当前节点是容器  -->
<#assign comList=root.childrens/>
<#assign n=n/>
<#switch root.comName>
    <#case "form">
        <#include "form_start.ftl">
        <#break/>
    <#case "checkbox">
        <#include "checkbox.ftl">
        <#break/>
    <#default>
        <div>
</#switch>
<#include "com_container.ftl">
<#switch root.comName>
    <#case "form">
        <#include "form_end.ftl">
        <#break/>
    <#case "checkbox">
        <#include "checkbox.ftl">
        <#break/>
    <#default>
        </div>
</#switch>
<#else>
<#--  当前节点是组件  -->
<#include "com.ftl">
</#if>
</#list>