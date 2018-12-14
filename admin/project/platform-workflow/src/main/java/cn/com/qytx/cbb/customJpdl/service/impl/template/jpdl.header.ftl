<?xml version="1.0" encoding="GBK"?>

<process name="${name}" xmlns="http://jbpm.org/4.4/jpdl">
   <start  name="start">
      <transition name="TO ${beginTask}" to="${beginTask}" />
   </start>