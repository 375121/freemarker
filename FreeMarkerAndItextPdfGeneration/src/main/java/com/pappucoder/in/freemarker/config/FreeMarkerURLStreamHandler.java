package com.pappucoder.in.freemarker.config;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * FreeMarkerURLStreamHandler is override URLStreamHandler to apply proxy for
 * opening connection of URLS.
 *
 */
public class FreeMarkerURLStreamHandler extends URLStreamHandler {
	public static final String IS_PROXY_REQUIRED = "FreeMarkerURLStreamHandler isProxy required ";
	public static final String PROXY_TYPE = "FreeMarkerURLStreamHandler PROXY TYPE=";
	private String url = "";
	private boolean isProxy;
	private String proxyHostName = "";
	private int proxyPort = 0;

	public FreeMarkerURLStreamHandler(String url, boolean isProxy, String proxyHostName, int proxyPort) {
		this.url = url;
		this.isProxy = isProxy;
		this.proxyHostName = proxyHostName;
		this.proxyPort = proxyPort;
	}

	@Override
	protected URLConnection openConnection(URL u) throws IOException {

		URLConnection urlConnection = null;
		if (this.isProxy) {
			InetSocketAddress inetSocketAddress = new InetSocketAddress(this.proxyHostName, this.proxyPort);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, inetSocketAddress);
			urlConnection = new URL(url).openConnection(proxy);
		} else
			urlConnection = new URL(url).openConnection(Proxy.NO_PROXY);

		return urlConnection;
	}

}
