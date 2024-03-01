package com.pappucoder.in.freemarker.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.pappucoder.in.freemarker.bean.FreemarkerRequestBean;

public interface FreeMarkerService {

	public String generatingPresignURL(FreemarkerRequestBean freemarkerRequestBean);

	public String generatingPresignURL(Map<?, ?> stringObjectMap, String templateUrl, String fileName,
			String storeLocationPath);

	public MultipartFile generateTemplateWithMultiPartFile(Map<?, ?> stringObjectMap, String templateUrl,
			String fileName);
	
}
