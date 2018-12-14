/**
 * Copyright (C) 2014 serv (liuyuhua69@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.com.qytx.platform.security;


/**
 * 系统变量工具类
 * 
 * @author serv
 *
 */
public class SystemContextHolder {
	private static ThreadLocal<SessionVariable> sessionVariable = new ThreadLocal<SessionVariable>();
    /**
     * 获取当前安全模型
     */
    public static SessionVariable getSessionContext(){
        try{
            return sessionVariable.get();
        }catch (Exception e){
            return LocalSystem.getSessionVariable();
        }

    }

    public static void setSessionContext(SessionVariable variable){
    	sessionVariable.set(variable);
    }

}
