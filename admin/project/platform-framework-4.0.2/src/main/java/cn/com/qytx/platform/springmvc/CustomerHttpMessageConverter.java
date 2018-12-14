package cn.com.qytx.platform.springmvc;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.StringHttpMessageConverter;


public class CustomerHttpMessageConverter extends StringHttpMessageConverter {

	@Override
	protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
		Charset charset = Charset.forName("UTF-8");
		Writer writer = new OutputStreamWriter(outputMessage.getBody(), charset);
		outputMessage.getBody().write(s.getBytes(charset));
		outputMessage.getBody().flush();
	}
}
