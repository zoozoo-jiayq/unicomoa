<task name="${name}"  >
		 <assignment-handler class="cn.com.qytx.cbb.customJpdl.service.impl.template.MultiAssignTask"> 
        </assignment-handler>
		<#list paths as path>
      		<transition name="TO ${path.to}" to="${path.to}" />
      	</#list>
 </task>