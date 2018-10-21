package com.illumio.firewall;

import static com.illumio.utils.Constants.INBOUND;
import static com.illumio.utils.Constants.OUTBOUND;
import static com.illumio.utils.Constants.TCP;
import static com.illumio.utils.Constants.UDP;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class FirewallTest {

	private static Firewall firewall;
	
	@BeforeClass
	public static void setUp() {
		firewall = new Firewall("/Users/rishabhjain/eclipse-workspace/Illumio/unitTest/com/illumio/firewall/rules.csv");
	}
	
	@Test
	public void testAcceptPacket1() {
		Assert.assertTrue(firewall.acceptPacket(OUTBOUND, TCP, 10002, "192.168.10.11"));
	}
	
	@Test
	public void testAcceptPacket2() {
		Assert.assertTrue(firewall.acceptPacket(INBOUND, UDP, 50, "192.168.180.11"));
	}
	
	@Test
	public void testAcceptPacket3() {
		Assert.assertFalse(firewall.acceptPacket(INBOUND, UDP, 48, "192.168.180.11"));
	}
	
	@Test
	public void testAcceptPacket4() {
		Assert.assertFalse(firewall.acceptPacket(INBOUND, UDP, 56, "192.168.180.11"));
	}

}
