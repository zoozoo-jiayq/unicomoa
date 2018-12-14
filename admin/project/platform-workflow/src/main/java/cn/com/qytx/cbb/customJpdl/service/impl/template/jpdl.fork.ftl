 <fork  name="${name}">
      <#list paths as path>
      		<transition name="TO ${path.to}" to="${path.to}" />
      </#list>
 </fork>