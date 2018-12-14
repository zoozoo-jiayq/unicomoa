 <decision name="${name}" >
   <#list paths as path>
      		<transition name="TO ${path.to}" to="${path.to}" >
      			<condition expr="${path.expr}"/>
      		</transition>
      </#list>
 </decision>