package com.pappucoder.in.freemarker.helper;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 *<p>
 * Trivial implementation of the {@link MultipartFile} interface to wrap a byte[] decoded
 * from a BASE64 encoded String
 *</p>
 */
public class BASE64DecodedMultipartFile implements MultipartFile {
	private final byte[] pdfContent;
	private String fileName = "";

	public BASE64DecodedMultipartFile(byte[] pdfContent, String fileName) {
		super();
		this.pdfContent = pdfContent;
		this.fileName = fileName;
	}

	@Override
	public String getName() {
		return this.fileName;
	}

	@Override
	public String getOriginalFilename() {
		return this.fileName;
	}

	@Override
	public String getContentType() {
		return "application/pdf";
	}

	@Override
	public boolean isEmpty() {
		return pdfContent == null || pdfContent.length == 00;
	}

	@Override
	public long getSize() {
		return pdfContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return pdfContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(pdfContent);
	}

	/**
	 * used to store the file transfer to another by using transferTo method
	 */
	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		try (OutputStream outputStream = java.nio.file.Files.newOutputStream(dest.toPath())) {
			outputStream.write(pdfContent);
			outputStream.flush();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
