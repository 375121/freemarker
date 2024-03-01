package com.pappucoder.in.itextrenderer.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.FSEntityResolver;

import com.pappucoder.in.freemarker.exception.ErrorCodes;
import com.pappucoder.in.freemarker.exception.FreeMarkerServiceException;
import com.pappucoder.in.freemarker.helper.BASE64DecodedMultipartFile;
import com.pappucoder.in.intextrenderer.config.B64ImgReplacedElementFactory;
import com.pappucoder.in.itextrenderer.service.ItextRendererPDFGeneratorService;

@Service
public class ItextRendererPDFGenerator implements ItextRendererPDFGeneratorService {

	public BASE64DecodedMultipartFile getPdfWithMultiPart(Object byteArrayObject, String fileName) {
		byte[] byteArray;
		BASE64DecodedMultipartFile base64DecodedMultipartFile = null;
		try {
			OutputStream outputStream = this.generatePDF(byteArrayObject);
//			Optional<OutputStream> objectOptional = Optional.ofNullable(outputStream);
			if (/* objectOptional.isPresent() && */outputStream instanceof OutputStream) {
				ByteArrayOutputStream returnByteArrayOutputStream = (ByteArrayOutputStream) outputStream;
				byteArray = returnByteArrayOutputStream.toByteArray();
				returnByteArrayOutputStream.flush();
				returnByteArrayOutputStream.close();
				outputStream.close();
				System.out.println("completed");
				base64DecodedMultipartFile = new BASE64DecodedMultipartFile(byteArray, fileName);
			} else {
				throw new FreeMarkerServiceException(HttpStatus.NOT_ACCEPTABLE,
						ErrorCodes.EXPECTED_IS_OUTPUT_STREAM_AS_OBJECT);
			}
		} catch (Exception e) {
			throw new FreeMarkerServiceException(HttpStatus.INTERNAL_SERVER_ERROR,
					ErrorCodes.EXCEPTION_OCCURED_WHILE_GENERATING_PDF, e.toString());

		}
		return base64DecodedMultipartFile;
	}

	private OutputStream generatePDF(Object byteArrayObject) throws Exception {
		Optional<Object> objectOptional = Optional.ofNullable(byteArrayObject);
		OutputStream returnOutPutStream = null;
		/**
		 * the conversion of Object to ByteArrayOutputStream and ByteArrayInputStream
		 * and byteArrayObject instanceOf ByteArrayOutputStream extended for learning
		 * purpose can be removed
		 */
		if (objectOptional.isPresent() && byteArrayObject instanceof ByteArrayOutputStream) {
			ByteArrayOutputStream byteArrayOutputStream = (ByteArrayOutputStream) byteArrayObject;
			ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

			/**
			 * Here creating Document instance which provide DOM XML object of our PDF
			 * filled by FreeMarker earlier
			 */
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setEntityResolver(FSEntityResolver.instance());
			Document document = builder.parse(inputStream);

			/**
			 * method which returns OutputStream of pdf, Providing Document instance and
			 * OutputStream;
			 */
			try (OutputStream out = new ByteArrayOutputStream()) {
				returnOutPutStream = this.createPDF(document, out);
				byteArrayOutputStream.flush();
				byteArrayOutputStream.close();
				inputStream.close();
			} catch (IOException e) {
				throw new FreeMarkerServiceException(HttpStatus.NOT_FOUND, ErrorCodes.FILE_NOT_FOUND, e.toString());

			}

		} else {
			throw new FreeMarkerServiceException(HttpStatus.BAD_REQUEST, ErrorCodes.BYTEARRAY_OBJECT_IS_NOT_VALID);
		}

		return returnOutPutStream;

	}

	/**
	 * returns outputStream of generated PDF.
	 * 
	 * @param document
	 * @param out
	 * @return
	 */
	private OutputStream createPDF(Document document, OutputStream out) {
		ITextRenderer renderer = new ITextRenderer();
		SharedContext sharedContext = renderer.getSharedContext();
		sharedContext.setPrint(true);
		sharedContext.setInteractive(false);
		sharedContext.setReplacedElementFactory(new B64ImgReplacedElementFactory());
		renderer.setDocument(document, null);
		renderer.layout();
		renderer.createPDF(out);
		renderer.finishPDF();

		return out;
	}

}
