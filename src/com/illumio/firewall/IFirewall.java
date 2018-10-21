package com.illumio.firewall;

public interface IFirewall {
	public boolean acceptPacket(String direction, String protocol, int port, String ipAddress);
}
