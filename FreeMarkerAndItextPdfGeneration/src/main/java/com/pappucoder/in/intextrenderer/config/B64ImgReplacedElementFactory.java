package com.pappucoder.in.intextrenderer.config;

import java.io.IOException;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import com.itextpdf.text.pdf.codec.Base64;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;

public class B64ImgReplacedElementFactory implements ReplacedElementFactory {

	/**
	 * this method is used to handle extra properties of pdf 
	 * such set img size and soucre 
	 * can be used for other purpose 
	 */
	public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth,
			int cssHeight) {
		Element e = box.getElement();
		if (e == null) {
			return null;
		}
		/**
		 * searches for src tag in our HTML can get the value for that such as URL of
		 * img or Byte[] of that img
		 */
		String nodeName = e.getNodeName();
		if (nodeName.equals("img")) {
			String attribute = e.getAttribute("src");
			FSImage fsImage;
			try {
				fsImage = buildImage(attribute, uac);
			} catch (BadElementException e1) {
				fsImage = null;
			} catch (IOException e1) {
				fsImage = null;
			}
			if (fsImage != null) {
				if (cssWidth != -1 || cssHeight != -1) {
					fsImage.scale(cssWidth, cssHeight);
				}
				return new ITextImageElement(fsImage);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param srcAttr
	 * @param uac
	 * @return Itextimg
	 * @throws IOException
	 * @throws BadElementException
	 * 
	 * Here we can set our image src type: in byte[], URL
	 * we are using byte[] 
	 * byte[] is extracted for the Document and converted to the itext-img this CCS provide to it.
	 * 
	 */
	protected FSImage buildImage(String srcAttr, UserAgentCallback uac) throws IOException, BadElementException {
		FSImage fsImage;
		if (srcAttr.startsWith("data:image/")) {
			String b64encoded = srcAttr.substring(srcAttr.indexOf("base64,") + "base64,".length(), srcAttr.length());
			byte[] decodedBytes = Base64.decode(b64encoded);

			fsImage = new ITextFSImage(Image.getInstance(decodedBytes));
		} else {
			fsImage = uac.getImageResource(srcAttr).getImage();
		}
		return fsImage;
	}

	@Override
	public void reset() {
//		
	}

	@Override
	public void remove(Element e) {
//		
	}

	@Override
	public void setFormSubmissionListener(FormSubmissionListener listener) {
//    		
	}

}
