package com.pappucoder.in.itextrenderer.service;

import com.pappucoder.in.freemarker.helper.BASE64DecodedMultipartFile;

public interface ItextRendererPDFGeneratorService {
	public BASE64DecodedMultipartFile getPdfWithMultiPart(Object byteArrayObject, String fileName);
}
