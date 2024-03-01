package com.pappucoder.in.freemarker.config;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import freemarker.cache.URLTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * Singleton class for Freemarker Configuration.
 */
public class FreeMarkerConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Variable to hold value for Configuration.
	 */
	public static final String UTF_8 = "UTF-8";
	private static Configuration configuration;
	private static final Object lockObject = new Object();

	private FreeMarkerConfiguration() {

	}

	/**
	 * creating configuration of freemarker Template return single instance of
	 * Template with configuration
	 * 
	 * @throws IOException
	 */
	public static Configuration getInstance(String templateUrl) throws IOException {
		if (configuration == null) {
			synchronized (lockObject) {
				if (null == configuration) {

					configuration = new Configuration(Configuration.VERSION_2_3_0);
					configuration.setLocalizedLookup(false);
					try {
						URL url = new URL(templateUrl);
						configuration.setTemplateLoader(new URLTemplateLoader() {
							@Override
							protected URL getURL(String url) {
								try {
									return new URL(url);
								} catch (MalformedURLException malformedUrlException) {
									malformedUrlException.printStackTrace();
									throw new RuntimeException(malformedUrlException);
								}
							}
						});
					} catch (MalformedURLException e) {
						configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates/"));

					}
					configuration.setDefaultEncoding(UTF_8);
					configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
					configuration.setLogTemplateExceptions(false);
					configuration.setWrapUncheckedExceptions(true);
					configuration.setFallbackOnNullLoopVariable(false);

				}
			}

		}

		return configuration;
	}
	/**
	 * USEFULL CODE
	 * 
	 * File file = ResourceUtils.getFile("classpath:template.html"); String
	 * templateURl1 = "file://" + file.getAbsolutePath();
	 */

	/**
	 * isProxy=false, proxyHostName=null, proxyPort=0 public static Configuration
	 * getInstance(boolean isProxy, String proxyHostName, int proxyPort) throws
	 * IOException { if (null == configuration) { synchronized (lockObject) { if
	 * (null == configuration) {
	 * 
	 * configuration = new Configuration(Configuration.VERSION_2_3_0);
	 * configuration.setLocalizedLookup(false); //
	 * configuration.setTemplateLoader(new URLTemplateLoader() { // @Override //
	 * protected URL getURL(String url) { // try { // return new URL(null, url, //
	 * new FreeMarkerURLStreamHandler(url, isProxy, proxyHostName, proxyPort)); // }
	 * catch (MalformedURLException malformedURLException) { // throw new
	 * RuntimeException(malformedURLException); // } // } // });
	 * configuration.setDirectoryForTemplateLoading(new
	 * File("src/main/resources/")); configuration.setDefaultEncoding(UTF_8);
	 * configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	 * configuration.setLogTemplateExceptions(false);
	 * configuration.setWrapUncheckedExceptions(true);
	 * configuration.setFallbackOnNullLoopVariable(false);
	 * 
	 * } } } return configuration; }
	 * 
	 * @Override protected Object clone() throws CloneNotSupportedException { throw
	 *           new CloneNotSupportedException(); }
	 * 
	 *           // implement readResolve method protected Object readResolve() {
	 *           return configuration; }
	 * 
	 */

}
