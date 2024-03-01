package com.pappucoder.in.freemarker.bean;

import java.util.Map;

public class FreemarkerRequestBean {

	private Map<String, String> map;

	private String templateUrl;

	private String fileName;

	private String storeLocationPath;

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public String getTemplateUrl() {
		return templateUrl;
	}

	public void setTemplateUrl(String templateUrl) {
		this.templateUrl = templateUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getStoreLocationPath() {
		return storeLocationPath;
	}

	public void setStoreLocationPath(String storeLocationPath) {
		this.storeLocationPath = storeLocationPath;
	}

	public FreemarkerRequestBean(Map<String, String> map, String templateUrl, String fileName,
			String storeLocationPath) {
		super();
		this.map = map;
		this.templateUrl = templateUrl;
		this.fileName = fileName;
		this.storeLocationPath = storeLocationPath;
	}

	

	@Override
	public String toString() {
		return "FreemarkerBean [map=" + map + ", templateUrl=" + templateUrl + ", fileName=" + fileName
				+ ", storeLocationPath=" + storeLocationPath + "]";
	}

}
