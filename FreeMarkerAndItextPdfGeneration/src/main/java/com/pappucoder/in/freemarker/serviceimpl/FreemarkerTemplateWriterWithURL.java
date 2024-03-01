package com.pappucoder.in.freemarker.serviceimpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pappucoder.in.freemarker.bean.FreemarkerRequestBean;
import com.pappucoder.in.freemarker.config.FreeMarkerConfiguration;
import com.pappucoder.in.freemarker.exception.ErrorCodes;
import com.pappucoder.in.freemarker.exception.FreeMarkerServiceException;
import com.pappucoder.in.freemarker.helper.BASE64DecodedMultipartFile;
import com.pappucoder.in.freemarker.service.FreeMarkerService;
import com.pappucoder.in.itextrenderer.service.ItextRendererPDFGeneratorService;

import freemarker.core.InvalidReferenceException;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class FreemarkerTemplateWriterWithURL implements FreeMarkerService{

	private ItextRendererPDFGeneratorService itextRendererPDFGenerator;

	public FreemarkerTemplateWriterWithURL(ItextRendererPDFGeneratorService itextRendererPDFGenerator) {
		this.itextRendererPDFGenerator = itextRendererPDFGenerator;
	}

	public String generatingPresignURL(Map<?, ?> stringObjectMap, String templateUrl, String fileName,
			String storeLocationPath) {
		if (null == stringObjectMap || null == templateUrl || null == fileName || null == storeLocationPath) {
			throw new FreeMarkerServiceException(HttpStatus.BAD_REQUEST, ErrorCodes.FREEMARKER_REQUEST_BEAN_IS_EMPTY);
		}

		String pdfPreSignedURl = null;
		try {
			MultipartFile multipartFile = this.generateTemplateWithMultiPartFile(stringObjectMap, templateUrl,
					fileName);

			File f1 = new File(storeLocationPath + multipartFile.getOriginalFilename());
			multipartFile.transferTo(f1);
			pdfPreSignedURl = "file://" + storeLocationPath + multipartFile.getOriginalFilename();

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdfPreSignedURl;
	}
	
	
	public String generatingPresignURL(FreemarkerRequestBean freemarkerRequestBean) {
		if (null == freemarkerRequestBean) {
			throw new FreeMarkerServiceException(HttpStatus.BAD_REQUEST, ErrorCodes.FREEMARKER_REQUEST_BEAN_IS_EMPTY);
		}

		String pdfPreSignedURl = null;
		try {
			MultipartFile multipartFile = this.generateTemplateWithMultiPartFile(freemarkerRequestBean.getMap(),
					freemarkerRequestBean.getTemplateUrl(), freemarkerRequestBean.getFileName());

			File f1 = new File(freemarkerRequestBean.getStoreLocationPath() + multipartFile.getOriginalFilename());
			multipartFile.transferTo(f1);
			pdfPreSignedURl = "file://" + freemarkerRequestBean.getStoreLocationPath()
					+ multipartFile.getOriginalFilename();

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pdfPreSignedURl;
	}

	/**
	 * Return BASE64DecodedMultipartFile object from your template URL object. input
	 * parameter are required class name tempLet path ,FTL file name ,Map which will
	 * have data from object.
	 * 
	 * @throws IOException
	 *
	 */
	public MultipartFile generateTemplateWithMultiPartFile(Map<?, ?> stringObjectMap, String templateUrl,
			String fileName)  {
		if (null == templateUrl || templateUrl.isBlank()) {
			throw new FreeMarkerServiceException(HttpStatus.BAD_REQUEST, ErrorCodes.TEMPLATE_URL_NOT_FOUND, fileName);
		}

		BASE64DecodedMultipartFile base64DecodedMultipartFile;
		ByteArrayOutputStream byteArrayOutputStream = null;
		Writer pageWriter;
		try {

			byteArrayOutputStream = new ByteArrayOutputStream();
			pageWriter = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8);
			writeIntoTemplate(stringObjectMap, pageWriter, templateUrl);
			
			base64DecodedMultipartFile = itextRendererPDFGenerator.getPdfWithMultiPart(byteArrayOutputStream, fileName);
			byteArrayOutputStream.flush();
			byteArrayOutputStream.close();
		} catch (InvalidReferenceException e) {
			throw new FreeMarkerServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodes.PLEASE_CHECK_MAP_PARAMETER_AGAINST_THE_HTML, e.toString());
		} catch (TemplateException e) {
			throw new FreeMarkerServiceException(HttpStatus.BAD_REQUEST, ErrorCodes.TEMPLATE_URL_NOT_FOUND,
					e.toString());
		} catch (ConnectException e) {
			throw new FreeMarkerServiceException(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCodes.PLEASE_CHECK_YOUR_URL,
					e.toString());
		} catch (Exception e) {
			throw new FreeMarkerServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodes.EXCEPTION_OCCURED_WHILE_FETCHING_TEMPLATE, e.toString());
		}
		
		return base64DecodedMultipartFile;

	}

	/**
	 * create Template object/instance from provided Template_url method which fill
	 * the template with provided map parameters against it complete the html with
	 * filled value in template
	 */
	private void writeIntoTemplate(Map<?, ?> stringObjectMap, Writer pageWriter, String templateUrl)
			throws IOException, TemplateException {
		Template template = FreeMarkerConfiguration.getInstance(templateUrl).getTemplate(templateUrl.toString());
		template.process(stringObjectMap, pageWriter);
	}

	

	

}
