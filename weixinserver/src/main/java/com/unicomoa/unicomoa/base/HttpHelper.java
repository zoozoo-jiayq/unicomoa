package com.unicomoa.unicomoa.base;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class HttpHelper {

	static private Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	static {
    	// Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        } };
        
        try{
	        // Install the all-trusting trust manager
	        final SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new java.security.SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        // Create all-trusting host name verifier
	        HostnameVerifier allHostsValid = new HostnameVerifier() {
	            public boolean verify(String hostname, SSLSession session) {
	                return true;
	            }
	        };
	
	        // Install the all-trusting host verifier
	        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        }catch(Exception e){
        	e.printStackTrace();
        	logger.error("https certifications install failure",e);
        }
	}
	
	
	public static String textGet(String url) {
		BufferedReader reader = null;
		try {
			URLConnection con = new URL(url).openConnection();
			reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));

			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			
			logger.debug(String.format("Get content from url[%s] : %s", url, sb.toString()));
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("Communication Error : %s ", e.getMessage()));
		} finally {
			IoUtils.safeClose(reader);
		}
		return "";
	}

	public static String textPost(String url, String text){
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			URLConnection con = new URL(url).openConnection();
	        con.setDoOutput(true);
	        con.setDoInput(true);
	        
	        writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "utf-8"));	        
	        writer.write( text );
	        writer.flush();
	        
	        reader = new BufferedReader( new InputStreamReader( con.getInputStream(), "utf-8"));
	        StringBuffer sb = new StringBuffer();
	        String line = "";
	        while ((line = reader.readLine()) != null) {
	            sb.append(line);
	        }        
	        
	        logger.debug(String.format("Post return from url[%s] : %s", url, sb.toString()));
	        return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("Communication Error : %s ", e.getMessage()));
			throw new RuntimeException(e);
		} finally {
			IoUtils.safeClose(writer);
			IoUtils.safeClose(reader);
		}
	}
	
	public static InputStream streamPost(String url, String text){
		BufferedWriter writer = null;
		BufferedReader reader = null;
		try {
			URLConnection con = new URL(url).openConnection();
	        con.setDoOutput(true);
	        con.setDoInput(true);
	        if(text!=null){
		        writer = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "utf-8"));	        
		        writer.write( text );
		        writer.flush();
	        }
	        System.out.println(new Gson().toJson(con.getHeaderFields())+"========================");
	       return con.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("Communication Error : %s ", e.getMessage()));
			throw new RuntimeException(e);
		} finally {
			IoUtils.safeClose(writer);
			IoUtils.safeClose(reader);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T xml2Bean(String xml,Class<T> cls) {
		ByteArrayInputStream bais;
		Unmarshaller um;
		try {
			bais = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			um = JAXBContext.newInstance(cls).createUnmarshaller();
			return (T) um.unmarshal(bais);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static <T> T textPost(String url, String text, Class<T> clasz) {

		try {

			URLConnection con = new URL(url).openConnection();
			con.setDoOutput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					con.getOutputStream()));
			writer.write(text);
			BufferedReader r = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			while (true) {
				String line = r.readLine();
				if (line == null)
					break;
				sb.append(line);
			}
			logger.debug(sb.toString());

			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(new StringReader(sb.toString()), clasz);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public static void objectPost(String url, Object input) {
		try {

			URLConnection con = new URL(url).openConnection();
			con.setDoOutput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					con.getOutputStream()));
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.writeValue(writer, input);
			writer.close();

			if (logger.isDebugEnabled()) {
				ObjectMapper m = new ObjectMapper();
				m.setSerializationInclusion(Include.NON_NULL);
				StringWriter swriter = new StringWriter();
				m.writeValue(swriter, input);
				logger.debug(swriter.toString());

				BufferedReader r = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				StringBuffer sb = new StringBuffer();
				while (true) {
					String line = r.readLine();
					if (line == null)
						break;
					sb.append(line);
				}
				logger.debug(String.format(
						"Post content to url[%s] and result is: %s", url,
						sb.toString()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static <T> T objectPost(String url, Object input, Class<T> clasz) {
		T output = null;
		try {

			URLConnection con = new URL(url).openConnection();
			con.setDoOutput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					con.getOutputStream()));
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper.writeValue(writer, input);
			writer.close();
			if (logger.isDebugEnabled()) {
				ObjectMapper m = new ObjectMapper();
				m.setSerializationInclusion(Include.NON_NULL);
				StringWriter swriter = new StringWriter();
				m.writeValue(swriter, input);
				logger.debug(swriter.toString());
			}
			BufferedReader r = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			StringBuffer sb = new StringBuffer();
			while (true) {
				String line = r.readLine();
				if (line == null)
					break;
				sb.append(line);
			}
			logger.debug(String.format(
					"Post content to url[%s] and result is: %s", url,
					sb.toString()));
			output = mapper.readValue(new StringReader(sb.toString()), clasz);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return output;
	}

	public static <T> T jsonTextToBean(String text, Class<T> clasz) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(new StringReader(text), clasz);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 以GET方式访问指定地址，获取JSON
	 * 
	 * @param url
	 * @return
	 * @throws WeixinApiException
	 */
	public static <T> T objectGet(String url, Class<T> clasz) {

		T output = null;

		// HTTP
		try {

			URLConnection con = new URL(url).openConnection();

			final Reader reader = new InputStreamReader(con.getInputStream(),
					"utf-8");
			final BufferedReader br = new BufferedReader(reader);

			StringBuffer sb = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();

			ObjectMapper mapper = new ObjectMapper();
			output = mapper.readValue(new StringReader(sb.toString()), clasz);

			logger.debug(String.format("Get content from url[%s] : %s", url,
					sb.toString()));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return output;
	}

	

	public static <T> T xmlPost(String url, Object input, Class<T> clasz) {
		try {
			URLConnection con = new URL(url).openConnection();
			con.setDoOutput(true);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					con.getOutputStream(), "utf-8"));
			Marshaller m = JAXBContext.newInstance(input.getClass())
					.createMarshaller();
			m.marshal(input, writer);
			writer.close();
			if (logger.isDebugEnabled()) {
				StringWriter w = new StringWriter();
				m.marshal(input, w);
				logger.debug(w.toString());
			}
//			ObjectMapper mapper = new XmlMapper();
//			logger.debug(clasz.toGenericString());
//			return mapper.readValue(con.getInputStream(), clasz);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	public static String map2Xml(Map<String, String> map) {

		StringBuffer buffer = new StringBuffer("<xml>");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			buffer.append("<").append(entry.getKey()).append(">");
			buffer.append(entry.getValue());
			buffer.append("</").append(entry.getKey()).append(">");
		}
		buffer.append("</xml>");
		return buffer.toString();
	}

	@SuppressWarnings("unchecked")
	public static <T> T xml2Obj(String xml, Class<T> clazz) {
		try {
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmar = context.createUnmarshaller();
			return (T) unmar.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}

	static final String end = "\r\n";
	static final String twoHyphens = "--";
	static final String boundary = "ylnetwork";

	public static String upload(String url, InputStream in, String filename) {

		try {

			HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
					url).openConnection();

			// 输入输出流
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);

			// get，post必须大写
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");

			// 必须在Content-Type请求头中指定分界符中的任意字符串
			httpURLConnection.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			// 获取输出流对象，预备上传文件
			DataOutputStream dos = new DataOutputStream(
					httpURLConnection.getOutputStream());

			// 设置分界符，加end表示单独一行
			dos.writeBytes(twoHyphens + boundary + end);

			// 设置与上传文件相关的信息
			dos.writeBytes(String
					.format("Content-Disposition: form-data; name=\"file\"; filename=\"%s\" \r\n",
							filename));

			// 在上传文件信息与文件的内容之间必须有一个空行
			dos.writeBytes(end);

			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			while ((count = in.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
			}
			in.close();

			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();

			dos.close();
			is.close();
			return result;

		} catch (Exception e) {
			logger.error("Upload failed.", e);
		}
		return "";
	}

	
}
