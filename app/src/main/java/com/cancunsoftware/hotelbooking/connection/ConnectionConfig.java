package com.cancunsoftware.hotelbooking.connection;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class ConnectionConfig {

    public static String apiHost = "http://dev.cancunsoftware.com/ApiTest";

    public static String PmLangs = "/api/PmLangs";
    public static String PmHotel = "/api/Hotel";
    public static String PmRooms = "/api/PmRooms";
    public static String PmRoomById = "/api/PmRooms";
    public static String PmActivity = "/api/PmActivity";
    public static String PmBookings = "/api/PmBookings";


    public static OkHttpClient getClient() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }
        X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[] { trustManager }, null);
        SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.hostnameVerifier((hostname, session) -> true);
        builder.sslSocketFactory(sslSocketFactory, trustManager).build();
        OkHttpClient client = builder.build();
        return client;
    }
}
