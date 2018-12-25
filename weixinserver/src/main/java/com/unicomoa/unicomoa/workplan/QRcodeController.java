package com.unicomoa.unicomoa.workplan;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.WriterException;
import com.unicomoa.unicomoa.base.BaseController;
import com.unicomoa.unicomoa.utils.QRCodeUtil;

@RestController
public class QRcodeController extends BaseController {
	
	@RequestMapping("/qrcode")
	public void qrcode(String content) throws WriterException, IOException {
		QRCodeUtil.createQrCode(response.getOutputStream(), content, 800, "PNG");
	}
}
