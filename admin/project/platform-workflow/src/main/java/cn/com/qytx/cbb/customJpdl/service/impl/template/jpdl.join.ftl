 <join  name="${name}">
      <#list paths as path>
      		<transition name="${path.text.text}" to="${path.to}" g="-50,-20"/>
      </#list>
 </join>