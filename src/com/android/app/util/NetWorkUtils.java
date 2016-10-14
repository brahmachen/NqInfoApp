package com.android.app.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.android.ais.app.AppApplication;

/**
 * @author �����жϹ���
 * 
 */
public class NetWorkUtils {
	public static final String NET_TYPE_WIFI = "WIFI";
	public static final String NET_TYPE_MOBILE = "MOBILE";
	public static final String NET_TYPE_NO_NETWORK = "no_network";

	private Context mContext = null;

	public NetWorkUtils(Context pContext) {
		this.mContext = pContext;
	}

	public NetWorkUtils() {
	}

	public static final AppApplication getApplication() {
		return AppApplication.getInstance();
	}

	public static final String IP_DEFAULT = "0.0.0.0";

	public static boolean isConnectInternet(final Context pContext) {
		final ConnectivityManager conManager = (ConnectivityManager) pContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

		if (networkInfo != null) {
			return networkInfo.isAvailable();
		}

		return false;
	}

	public static boolean isConnectWifi() {
		ConnectivityManager mConnectivity = (ConnectivityManager) getApplication()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = mConnectivity.getActiveNetworkInfo();
		// �ж������������ͣ�ֻ����3G��wifi�����һЩ���ݸ��¡�
		int netType = -1;
		if (info != null) {
			netType = info.getType();
		}
		if (netType == ConnectivityManager.TYPE_WIFI) {
			return info.isConnected();
		} else {
			return false;
		}
	}

	public static String getNetTypeName(final int pNetType) {
		switch (pNetType) {
		case 0:
			return "unknown";
		case 1:
			return "GPRS";
		case 2:
			return "EDGE";
		case 3:
			return "UMTS";
		case 4:
			return "CDMA: Either IS95A or IS95B";
		case 5:
			return "EVDO revision 0";
		case 6:
			return "EVDO revision A";
		case 7:
			return "1xRTT";
		case 8:
			return "HSDPA";
		case 9:
			return "HSUPA";
		case 10:
			return "HSPA";
		case 11:
			return "iDen";
		case 12:
			return "EVDO revision B";
		case 13:
			return "LTE";
		case 14:
			return "eHRPD";
		case 15:
			return "HSPA+";
		default:
			return "unknown";
		}
	}

	// ��ȡ�ֻ�MAc��ַ
	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}

	public static String getIPAddress() {
		try {
			final Enumeration<NetworkInterface> networkInterfaceEnumeration = NetworkInterface
					.getNetworkInterfaces();

			while (networkInterfaceEnumeration.hasMoreElements()) {
				final NetworkInterface networkInterface = networkInterfaceEnumeration
						.nextElement();

				final Enumeration<InetAddress> inetAddressEnumeration = networkInterface
						.getInetAddresses();

				while (inetAddressEnumeration.hasMoreElements()) {
					final InetAddress inetAddress = inetAddressEnumeration
							.nextElement();

					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress();
					}
				}
			}

			return NetWorkUtils.IP_DEFAULT;
		} catch (final SocketException e) {
			return NetWorkUtils.IP_DEFAULT;
		}
	}

	public static String longToIP() {
		WifiManager wifiManager = (WifiManager) getApplication()
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		int ipAddress = wifiInfo.getIpAddress();
		// linux long�ǵ�λ��ǰ����λ�ں�
		StringBuffer sb = new StringBuffer("");
		// ����24λ��0
		sb.append(String.valueOf((ipAddress & 0x000000FF)));
		sb.append(".");
		// ����16λ��0��Ȼ������8λ
		sb.append(String.valueOf((ipAddress & 0x0000FFFF) >>> 8));
		sb.append(".");
		// ����8λ��0��Ȼ������16λ
		sb.append(String.valueOf((ipAddress & 0x00FFFFFF) >>> 16));
		sb.append(".");
		// ֱ������24λ
		sb.append(String.valueOf((ipAddress >>> 24)));
		return sb.toString();
	}

	public static String getConnTypeName() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getApplication()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			return NET_TYPE_NO_NETWORK;
		} else {
			return networkInfo.getTypeName();
		}
	}

}
