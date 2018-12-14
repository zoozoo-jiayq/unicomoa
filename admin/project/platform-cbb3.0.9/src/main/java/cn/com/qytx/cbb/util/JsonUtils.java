package cn.com.qytx.cbb.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * 序列化字符json
 * @author liyanlei
 *对外提供两个方法。一个包含日期、一个不包含日期。
 *不包含日期返回日期格式为"yyyy-MM-dd HH:mm:ss"
 */
public class JsonUtils {
	
	public static GsonBuilder gsonBuilder = null;
	
	static{
		gsonBuilder = new GsonBuilder().disableHtmlEscaping().serializeNulls();
	}
	/**
	 * 序列化包含时间的任何格式
	 * @param dateFormat 时间格式
	 * @param obj 转换json字符创的对象
	 * @param excludeFileds  不需要转换的字段
	 * @return
	 */
	public static String generWithDateJsonString(String dateFormat,Object obj,String...excludeFileds){
		gsonBuilder.setDateFormat(formatDate(dateFormat));
		gsonBuilder.addSerializationExclusionStrategy(new ExcludeFiledsExclusionStrategy(excludeFileds));
		gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}
	
	/**
	 * 格式化没有包含日期的字符串
	 * @param obj
	 * @param excludeFileds
	 * @return
	 */
	public static String generJsonString(Object obj,String...excludeFileds){
		return generWithDateJsonString(null,obj, excludeFileds);
	}
	/**
	 * 格式化时间类型
	 * @param dateFormat
	 * @return
	 */
	private static String formatDate(String dateFormat){
		if(null==dateFormat || "".equals(dateFormat)){
			return "yyyy-MM-dd HH:mm:ss";
		}else{
			return dateFormat;
		}
	}
	
	/**
	 * 注入hibernate对象
	 * @author liyanlei
	 *
	 */
	private static class HibernateProxyTypeAdapter extends TypeAdapter<HibernateProxy>{
		
		public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
				 return (HibernateProxy.class.isAssignableFrom(type.getRawType()) ? (TypeAdapter<T>) new HibernateProxyTypeAdapter(gson) : null);  
			}
		};
		private final Gson context;  
		
		private HibernateProxyTypeAdapter(Gson context) {  
	        this.context = context;  
	    }  
		public HibernateProxy read(JsonReader in) throws IOException {
			 throw new UnsupportedOperationException("Not supported");  
		}
		public void write(JsonWriter out, HibernateProxy value) throws IOException {
			 if (value == null) {  
		            out.nullValue();  
		            return;  
		     }
	        Class<?> baseType = Hibernate.getClass(value);  
	        TypeAdapter delegate = context.getAdapter(TypeToken.get(baseType));  
	        Object unproxiedValue = ((HibernateProxy) value).getHibernateLazyInitializer()  
	                .getImplementation();  
	        delegate.write(out, unproxiedValue);  
		}
	}
	/**
	 * 过滤不需要转换json字符创的字段
	 * @author liyanlei
	 *
	 */
	private static class ExcludeFiledsExclusionStrategy implements ExclusionStrategy{
		
		private String[] excludeFileds = null;
		
		private ExcludeFiledsExclusionStrategy(){
			
		}
		
		public ExcludeFiledsExclusionStrategy(String[] excludeFileds){
			this.excludeFileds = excludeFileds;
		}
		
		public boolean shouldSkipField(FieldAttributes attributes) {
			if(contains(excludeFileds,attributes.getName())){
				return true;
			}
			return false;  
		}

		@Override
		public boolean shouldSkipClass(Class<?> clazz) {
			return false;
		}
		
	}
	/**
	 * 判断当前数组是否包含当前source数据
	 * @param stringArray
	 * @param source
	 * @return
	 */
	private static boolean contains(String[] stringArray,String source){
		if(stringArray !=null){
			List<String> tempList = Arrays.asList(stringArray);
			if(tempList.contains(source)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
