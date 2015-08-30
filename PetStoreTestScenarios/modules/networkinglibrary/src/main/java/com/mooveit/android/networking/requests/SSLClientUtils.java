package com.mooveit.android.networking.requests;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

public class SSLClientUtils {

    private static TrustManager[] trustManagers;

    public static class FakeX509TrustManager implements X509TrustManager {
        @SuppressWarnings("unused")
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public boolean isClientTrusted(X509Certificate[] chain) {
            return (true);
        }

        public boolean isServerTrusted(X509Certificate[] chain) {
            return (true);
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] x509Certificates, String s)
                throws java.security.cert.CertificateException {
        }

        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[0];
        }
    }

    /**
     * Allow all SSL certificates by setting up a host name verifier that passes
     * everything and as well setting up a SocketFactory with the
     * #FakeX509TrustManager.
     */
    public static void createSSLClient() {
        SSLContext context = null;

        if (trustManagers == null) {
            trustManagers = new TrustManager[] { new FakeX509TrustManager() };
        }

        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
        } catch (KeyManagementException e) {
        }
        HttpsURLConnection.setDefaultSSLSocketFactory(context
                .getSocketFactory());

        SSLContext.setDefault(context);
    }

}