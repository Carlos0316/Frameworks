package com.carlos.libnetwork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class NetworkManager {

    // ===========================================================
    // Constants
    // ===========================================================

    // ===========================================================
    // Fields
    // ===========================================================

    private static NetworkManager mInstance;

    private Context mContext;

    private ConnectivityManager mConnectivityManager;

    private WifiManager mWifiManager;

    // ===========================================================
    // Constructors
    // ===========================================================

    private NetworkManager(Context context) {
        mContext = context.getApplicationContext();
        mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    // ===========================================================
    // Getter & Setter
    // ===========================================================

    // ===========================================================
    // Methods for/from SuperClass/Interfaces
    // ===========================================================

    // ===========================================================
    // Methods
    // ===========================================================

    public static NetworkManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkManager(context);
        }

        return mInstance;
    }

    public String getIPAddress() throws SocketException {
        // TODO: 2016/8/16 测试WIFI状态下IP是否正常，4G网络下IP是否正常
        String ipAddress = "0.0.0.0";
        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        while (netInterfaces.hasMoreElements()) {
            NetworkInterface intf = netInterfaces.nextElement();
            if (intf.getName().toLowerCase(Locale.getDefault()).equals("eth0")) {
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipAddress = inetAddress.getHostAddress().toString();
                        if (!ipAddress.contains("::")) {// ipV6的地址
                            break;
                        }
                    }
                }
            }
        }
        return ipAddress;
    }

    public boolean isNetworkConnected() {
        if (mConnectivityManager != null) {
            NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
            if (info == null) {
                return false;
            } else {
                return info.isAvailable();
            }
        }

        return false;
    }

    public void disconnectNetwork(int networkType, String ssid) {

    }

    // ===========================================================
    // Inner and Anonymous Classes
    // ===========================================================

}
