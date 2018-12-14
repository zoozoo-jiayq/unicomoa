<task name="${name}" assignee="${candidate}" >
		<on event="start">
	   	  	<event-listener class="cn.com.qytx.cbb.customJpdl.service.impl.template.RollbackListenerForOtherTask">
	   	  	</event-listener>
   	 	</on>
		<#list paths as path>
      		<transition name="TO ${path.to}" to="${path.to}" />
      	</#list>
 </task>