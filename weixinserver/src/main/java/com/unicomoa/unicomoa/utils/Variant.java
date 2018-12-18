package com.unicomoa.unicomoa.utils;


import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class Variant{
	static final Logger logger = LoggerFactory.getLogger(Variant.class);
	
	public static final Variant NULL = new VariantImpl(null);
    
    public static final Number NaN = new Double(Double.NaN);
	
    static class VariantImpl extends Variant{
        protected Object content;

        public VariantImpl(Object content){
            this.content = content;
        }

        public Object objectValue(){
            return content;
        }
        
        public Object getValue(){
            return content;
        }
    }
	
	protected Variant(){
		
	}
	
	public static Variant valueOf(Object o){
	    if(o == null)
	        return Variant.NULL;
		if(o instanceof Variant)
			return (Variant)o;
		Variant var = new VariantImpl(o);
		return var;
	}
	
	public String toString(){
		return stringValue("");
	}
	
	public abstract Object objectValue();
	
	public boolean isNull(){
	    return objectValue() == null;
	}
	
	public boolean isEmpty(){
		Object o = objectValue();
		if(o == null)
			return true;
		
		if(o instanceof String)
			return ((String)o).length() == 0;
		
		if(o instanceof Object[]){
			Object[] ary = (Object[])o;
			return ary.length == 0;
		}
			
		if(o instanceof Collection<?>){
			Collection<?> c = (Collection<?>)o;
			return c.isEmpty();
		}
		
		return false;
	}
	
	public String stringValue(){
	    return stringValue(null);
	}
	
	Object getSingleValue(){
	    Object value = objectValue();
		if(value instanceof Object[]){
			Object[] ary = (Object[])value;
			if(ary.length <= 0)
				return null;
			return ary[0];
		}else if(value instanceof Collection<?>){
			Collection<?> c = (Collection<?>)value;
			if(c.isEmpty())
				return null;
			return c.iterator().next();
		}
		return value;
	}
	
	public boolean booleanValue(boolean defaultValue){
		Object value = getSingleValue();
		if(value == null)
			return defaultValue;
		if(value instanceof Boolean)
			return (Boolean)value;
		String s = value.toString();
		if(s.equals("true") || s.equals("Y"))
			return true;
		if(s.equals("false") || s.equals("N"))
			return false;
		return defaultValue;
	}
	
	public byte byteValue(){
	    return (byte)intValue();
	}
	
	public byte byteValue(byte defaultValue){
	    return (byte)intValue(defaultValue);
	}
	
	public byte[] bytesValue(byte[] defaultValue){
	    Object o = getSingleValue();
	    if(o == null)
	        return defaultValue;
	    if(o instanceof byte[]){
	        return (byte[])o;
	    }
	    if(o instanceof String){
	        return o.toString().getBytes();
	    }
	    return defaultValue;
	}
	
	public Object[] arrayValue(){
	    Object o = objectValue();
	    if(o instanceof Object[])
	        return (Object[])o;
	    List<?> list = listValue();
	    return list == null ? null : list.toArray();
	}
	
	public Object[] arrayValue(Object[] defaultValue){
	    Object[] ret = arrayValue();
	    return ret == null ? defaultValue : ret;
	}
	
	@SuppressWarnings("unchecked")
    public <K,V> Map<K,V> mapValue(){
	    Object o = objectValue();
	    if(o instanceof Map<?,?>)
	        return (Map<K,V>)o;
	    return null;
	}
	
	public <K,V> Map<K,V> mapValue(Map<K,V> defaultValue){
	    Map<K,V> o = mapValue();
	    return o == null ? defaultValue : o;
	}
	
	public byte[] bytesValue(){
	    return bytesValue(null);
	}
	
	public int intValue(int defaultValue){
		Object value = getSingleValue();
		if(value == null)
			return defaultValue;
		if(value instanceof Number){
			return ((Number)value).intValue();
		}
		try{
			return (int)Double.parseDouble(value.toString());
		}catch(Exception e){
			if(logger.isInfoEnabled())
				logger.info("java.err_cast_to_int_fail::"+value);
			return defaultValue;
		}
	}
	
	public long longValue(long defaultValue){
		Object value = getSingleValue();
		if(value == null)
			return defaultValue;
		if(value instanceof Timestamp){
			return ((Timestamp)value).getTime();
		}
		if(value instanceof Number)
			return ((Number)value).longValue();
		try{
			return Long.parseLong(value.toString());
		}catch(Exception e){
			if(logger.isInfoEnabled())
				logger.info("java.err_cast_to_long_fail::"+value);
			return defaultValue;
		}
	}
	
	public double doubleValue(double defaultValue){
		Object value = getSingleValue();
		if(value == null)
			return defaultValue;
		if(value instanceof Number){
			return ((Number)value).doubleValue();
		}
		try{
			return Double.parseDouble(value.toString());
		}catch(Exception e){
			if(logger.isInfoEnabled())
				logger.info("java.err_cast_to_double_fail::"+value);
			return defaultValue;
		}
	}
	
	public Number numberValue(Number defaultValue){
	    Object value = getSingleValue();
	    if(value == null)
	        return defaultValue;
	    if(value instanceof Number)
	        return (Number)value;
	    try{
            return Double.parseDouble(value.toString());
        }catch(Exception e){
            if(logger.isInfoEnabled())
                logger.info("java.err_cast_to_double_fail::"+value);
            return defaultValue;
        }
	}
	
	public Number numberValue(){
	    return numberValue(null);
	}
	
	public String stringValue(String defaultValue){
		Object value = getSingleValue();
		if(value == null)
			return defaultValue;
		if(value instanceof Timestamp)
			return stampToString(value);
		return value.toString();
	}
	
	public List<String> stringListValue(){
	    List<String> list = listValue();
		return list;
	}
	
	public char charValue(char defaultValue){
		String s = stringValue(null);
		if(s == null || s.length() == 0)
			return defaultValue;
		return s.charAt(0);
	}
	
	public char charValue(){
	    return charValue((char)0);
	}
	
	public int intValue(){
	    return intValue(0);
	}
	
	public long longValue(){
	    return longValue(0L);
	}
	
	public boolean booleanValue(){
	    return booleanValue(false);
	}
	
	public double doubleValue(){
	    return doubleValue(0d);
	}
	
	public <T> List<T> listValue(List<T> defaultValue){
	    List<T> list = listValue();
	    return list == null ? defaultValue : list;
	}
	
	@SuppressWarnings("unchecked")
    public <T> List<T> listValue(){
	    Object value = objectValue();
		if(value == null)
			return null;
		if(value instanceof List<?>)
			return (List<T>)value;
		if(value instanceof Collection<?>)
			return new ArrayList<T>((Collection<? extends T>)value);
		if(value instanceof Object[])
			return (List<T>)Arrays.asList((Object[])value);
		if(value instanceof String)
			return (List<T>)StringUtils.stripedSplit((String)value, ',');
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
    public <T> Collection<T> collectionValue(){
	    Object value = objectValue();
	    if(value == null)
            return null;
        if(value instanceof List<?>)
            return (List<T>)value;
        if(value instanceof Collection<?>)
            return (Collection<T>)value;
        if(value instanceof Object[])
            return (Collection<T>)Arrays.asList((Object[])value);
        if(value instanceof String)
            return (Collection<T>)StringUtils.stripedSplit((String)value, ',');
        
        return null;
	}
	
	public <T> Collection<T> collectionValue(Collection<T> defaultValue){
	    Collection<T> c = collectionValue();
	    return c == null ? defaultValue : c;
	}
	
	public Date dateValue(){
	    return stampValue();
	}
	
	public Date dateValue(Date defaultValue){
	    return stampValue(toTimestamp(defaultValue));
	}
	
	public Timestamp stampValue(){
	    Object value = getSingleValue();
	    Timestamp stamp = toTimestamp(value);
	    return stamp;
	}
	
	public Timestamp stampValue(Timestamp defaultValue){
	    Timestamp stamp = stampValue();
	    return stamp == null ? defaultValue : stamp;
	}
	
    public String stripedStringValue(String defaultValue) {
        String s = stringValue(null);
        s = StringUtils.strip(s);
        if(s == null)
            s = defaultValue;
        return s;
    }
    
    public String stripedStringValue(){
        return stripedStringValue(null);
    }

    public Object objectValue(Object defaultValue) {
        Object value = objectValue();
        if(value == null)
            return defaultValue;
        return value;
    }	
	
    
    public double rangeValue(double defaultValue, double minValue, double maxValue){
        double d = doubleValue(defaultValue);
        if(d < minValue){
            d = minValue;
        }else if(d > maxValue){
            d = maxValue;
        }
        return d;
    }
	
    @SuppressWarnings("deprecation")
    public static Timestamp toTimestamp(Object obj){
        if(obj == null)
            return null;
        if(obj instanceof Timestamp)
            return (Timestamp)obj;

        if(obj instanceof Date)
            return new Timestamp(((Date)obj).getTime());
        
        if(obj instanceof Calendar){
            return new Timestamp(((Calendar)obj).getTimeInMillis());
        }
        
        if(obj instanceof Long){
        	return new Timestamp(((Long)obj).longValue());
        }

        if(! (obj instanceof String) )
            throw Exceptions.code("java.err_invalid_date");

        String s = StringUtils.strip(obj.toString());
        if(s == null)
            return null;
        if(StringUtils.isDigits(s)){
            try{
                long l = Long.parseLong(s);
                return new Timestamp(l);
            }catch(NumberFormatException e){
                throw Exceptions.code("java.err_invalid_date");
            }
        }
            String date_s;
            String time_s;
            String nanos_s;
            int year;
            int month = 0;
            int day = 1;
            int hour=0;
            int minute=0;
            int second=0;
            int a_nanos = 0;
            int firstDash;
            int secondDash;
            int dividingSpace;
            int firstColon = 0;
            int secondColon = 0;
            int period = 0;
            String formatError = "java.err_invalid_date";
            String zeros = "000000000";

            // Split the string into date and time components
            s = s.trim();
            if(s.length() <= 0)
                return null;
            dividingSpace = s.indexOf(' ');
            if(dividingSpace < 0)
               dividingSpace = s.indexOf('T'); // ExtJS will use this format yyyy-mm-ddThh:mm:ss
               
            if (dividingSpace > 0) {
                date_s = s.substring(0,dividingSpace);
                time_s = s.substring(dividingSpace+1);
            } else {
                date_s = s;
                time_s = null;
            }


            // Parse the date
            firstDash = date_s.indexOf('-');
            if(firstDash < 0)
                firstDash = date_s.indexOf('.');
            if(firstDash < 0)
                firstDash = date_s.indexOf('/');
            secondDash = date_s.indexOf('-', firstDash+1);
            if(secondDash < 0)
                secondDash = date_s.indexOf('.',firstDash+1);
            if(secondDash < 0)
                secondDash = date_s.indexOf('/',firstDash+1);

            // Parse the time
            if (time_s != null){
                firstColon = time_s.indexOf(':');
                secondColon = time_s.indexOf(':', firstColon+1);
                period = time_s.indexOf('.', secondColon+1);
            }

            if(firstDash <= 0)
                throw Exceptions.code(formatError);

            String syear = date_s.substring(0,firstDash);
            if(syear.length() == 2){
                syear = "20"+syear;
            }else if(syear.length() == 1){
                syear = "200"+syear;
            }
            year = Integer.parseInt(syear) - 1900;

            // Convert the date
            if(secondDash < 0){
                month = Integer.parseInt(date_s.substring(firstDash+1)) - 1;
            }else{
                month = Integer.parseInt(date_s.substring(firstDash+1,secondDash)) -1;
                String ds = date_s.substring(secondDash + 1);
                if (ds.length() > 0) {
                    day = Integer.parseInt(ds);
                }
            }


            // Convert the time; default missing nanos
            if(time_s != null){
            if ((firstColon > 0)) {
                hour = Integer.parseInt(time_s.substring(0, firstColon));
                if(secondColon < 0)
                    secondColon = time_s.length();
                String ts = time_s.substring(firstColon+1, secondColon);
                if(ts.length() > 0){
                    minute = Integer.parseInt(ts);
                }
                if ((period > 0) & (period < time_s.length()-1)) {
                second =
                    Integer.parseInt(time_s.substring(secondColon+1, period));
                nanos_s = time_s.substring(period+1);
                if (nanos_s.length() > 9)
                    throw Exceptions.code(formatError);
                if (!Character.isDigit(nanos_s.charAt(0)))
                    throw Exceptions.code(formatError);
                nanos_s = nanos_s + zeros.substring(0,9-nanos_s.length());
                a_nanos = Integer.parseInt(nanos_s);
                } else if (period > 0) {
                //throw Exceptions.code(formatError);
                } else if(secondColon < time_s.length()){

                    String ss = time_s.substring(secondColon+1);
                    if(ss.length() > 0)
                        second = Integer.parseInt(ss);
                }
            }}
            return new Timestamp(year, month, day, hour, minute, second, a_nanos);
    }
    
	public static Object cast(Class<?> clazz, Object o){		
	    if(o instanceof Variant){
            o = ((Variant)o).objectValue();
        }
		if(clazz == int.class)
			return Variant.valueOf(o).intValue(0);
		if(clazz == double.class)
			return Variant.valueOf(o).doubleValue(0.0);
		if(clazz == long.class)
			return Variant.valueOf(o).longValue(0L);
		if(clazz == char.class)
			return Variant.valueOf(o).charValue((char)0);
		if(clazz == boolean.class)
			return Variant.valueOf(o).booleanValue(false);
		
		if(o == null)
			return null;
		if(clazz.isAssignableFrom(o.getClass()))
			return o;

		if(clazz == Integer.class)
			return Variant.valueOf(o).intValue(Integer.MIN_VALUE);
		if(clazz == Double.class)
			return Variant.valueOf(o).doubleValue(Double.MIN_VALUE);
		if(clazz == Long.class)
			return Variant.valueOf(o).longValue(Long.MIN_VALUE);
		if(clazz == Boolean.class)
			return Variant.valueOf(o).booleanValue(false);
		if(clazz == Character.class)
			return Variant.valueOf(o).charValue(Character.MIN_VALUE);
		
        if(clazz == BigDecimal.class){
            return new BigDecimal(o.toString());
        }
		
		if(clazz == String.class){
		    if(o instanceof Timestamp){
		        return stampToString(o);
		    }
			return o.toString();
		}
		if(Date.class.isAssignableFrom(clazz))
		    return Variant.valueOf(o).stampValue();
		
        if(clazz == List.class){
            if(o instanceof Collection<?>){
                return new ArrayList<Object>((Collection<?>)o);
            }else if(o instanceof Object[]){
                return Arrays.asList((Object[])o);
            }else if(o instanceof String){
                return StringUtils.stripedSplit(o.toString(),',');
            }
        }else if(clazz.isArray()){
            if(o instanceof Collection<?>){
                Class<?> comType = clazz.getComponentType();               
                Collection<?> c = (Collection<?>)o;
                return c.toArray((Object[])Array.newInstance(comType,c.size()));
            }
        }else if(clazz == Collection.class){
            if(o instanceof Object[]){
                return Arrays.asList((Object[])o);
            }else if(o instanceof String){
                return StringUtils.stripedSplit(o.toString(),',');
            }           
        }
		
		if(logger.isInfoEnabled())
			logger.info("java.err_cast_fail::"+clazz+","+o.getClass()+","+o);
		return o;
	}
	
	static final String stampToString(Object stamp){
	    String str = stamp.toString().substring(0,19);
	    if(str.endsWith(" 00:00:00"))
	        return str.substring(0,10);
	    return str;
	}
}