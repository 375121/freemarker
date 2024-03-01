package com.pappucoder.in;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.ResourceUtils;

import com.pappucoder.in.freemarker.bean.FreemarkerRequestBean;
import com.pappucoder.in.freemarker.serviceimpl.FreemarkerTemplateWriterWithURL;

@SpringBootApplication
public class FreeMarkerAndItextPdfGenerationApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(FreeMarkerAndItextPdfGenerationApplication.class,
				args);
		FreemarkerTemplateWriterWithURL bean = run.getBean(FreemarkerTemplateWriterWithURL.class);
		Map<String, String> mapObject = new HashMap<>();
		mapObject.put("name", "ajay");
		mapObject.put("std", "12th");
		mapObject.put("college", "SVM");
		mapObject.put("currentDate", "SVM");
		mapObject.put("insuranceNo", "SVM");
		mapObject.put("loanAccountNumber", "SVM");
		mapObject.put("premium", "SVM");
		mapObject.put("sumAssured", "SVM");
		mapObject.put("principalName", "SVM");
		mapObject.put("productName", "SVM");
		mapObject.put("totalLoanAmount", "SVM");
		mapObject.put("advanceEMI", "SVM");
		mapObject.put("totalNumberOfEMI", "SVM");
		mapObject.put("loanType", "SVM");
		mapObject.put("customerEmailAddress", "SVM");
		mapObject.put("customerDeviceIpAddress", "SVM");
		mapObject.put("loanSourcingDate", "SVM");
		mapObject.put("customerContactDate", "SVM");
		mapObject.put("bflServerIp", "SVM");
		mapObject.put("locationName", "SVM");
		mapObject.put("paymentMadeDate", "SVM");
		mapObject.put("customerGeoTaggingDetails", "SVM");
		mapObject.put("quoteCreatedDate", "SVM");
		mapObject.put("loanApplicationReferenceNumber", "SVM");
		mapObject.put("teleCallerName", "SVM");
		mapObject.put("bflIp", "SVM");
		mapObject.put("customerName", "Ajay");
		mapObject.put("loanOtpDate", "SVM");
		mapObject.put("customerMobileNumber", "SVM");
		mapObject.put("storeDate", "SVM");
		mapObject.put("finnoneCustomerId", "SVM");

		
		
		
		
		
		
//		String templateURl= "template.html";
		String templateURl = "https://stage-aem.bajajfinserv.in/content/insurance-marketplace/audit-trail/full-funded.html";
		String storeURL = "/Users/ajaysharma21/Desktop/testing_Temp/";
		String fileName = "template2.img";
		

		try {
			File file = ResourceUtils.getFile("classpath:template.html");
			String templateURl1 = "file://" + file.getAbsolutePath();
			
			FreemarkerRequestBean freemarkerBean = new FreemarkerRequestBean(mapObject,templateURl1, fileName, storeURL);

			String generatingPresignURL = bean.generatingPresignURL(freemarkerBean);
			
//			String generatingPresignURL = bean.generatingPresignURL(mapObject, templateURl1, fileName, storeURL);
			
			System.out.println(generatingPresignURL);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
