package com.illumio.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.illumio.exception.FirewallException;

public class IPUtils {
	
	// Reference: https://gist.github.com/madan712/6651967
	public static long ipToLong(String ipString) throws FirewallException  {
		InetAddress ip;
		try {
			ip = InetAddress.getByName(ipString);
		} catch (UnknownHostException e) {
			throw new FirewallException(e.getMessage(), e);
		}
		byte[] octets = ip.getAddress();
		long result = 0;
		for (byte octet : octets) {
			result <<= 8;
			result |= octet & 0xff;
		}
		return result;
	}

	public static boolean isValidRange(long ipStart, long ipEnd, long ipToCheck) {
		return (ipToCheck >= ipStart && ipToCheck <= ipEnd);

	}

}
