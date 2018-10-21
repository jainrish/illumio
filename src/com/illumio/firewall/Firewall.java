package com.illumio.firewall;

import static com.illumio.utils.Constants.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.illumio.exception.FirewallException;
import com.illumio.model.Rule;
import com.illumio.utils.IPUtils;

public class Firewall implements IFirewall{

	List<Rule> inTCP, outTCP, inUDP, outUDP;
	private static final Logger LOG = Logger.getLogger(Firewall.class.getName());

	public Firewall(String filePath) {
		inTCP = new ArrayList<>();
		outTCP = new ArrayList<>();
		inUDP = new ArrayList<>();
		outUDP = new ArrayList<>();

		try (FileInputStream inputStream = new FileInputStream(filePath)) {

			try (Scanner sc = new Scanner(inputStream, "UTF-8")) {
				LOG.log(Level.INFO, String.format("Started reading file at path : %s", filePath));
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] tokens = line.split(COMMA);
					long ipStart, ipEnd;
					int portStart, portEnd;

					if (tokens[2].contains(HYPHEN)) {
						portStart = Integer.parseInt(tokens[2].split(HYPHEN)[0]);
						portEnd = Integer.parseInt(tokens[2].split(HYPHEN)[1]);
					} else {
						portStart = Integer.parseInt(tokens[2]);
						portEnd = portStart;
					}
					try {
						if (tokens[3].contains(HYPHEN)) {
							ipStart = IPUtils.ipToLong(tokens[3].split(HYPHEN)[0]);
							ipEnd = IPUtils.ipToLong(tokens[3].split(HYPHEN)[1]);
						} else {
							ipStart = IPUtils.ipToLong(tokens[3]);
							ipEnd = ipStart;
						}
					} catch (FirewallException e) {
						LOG.log(Level.WARNING, String.format("Skipping line '%s' due to invalid ip address...", line));
						continue;
					}
					Rule rule = new Rule(portStart, portEnd, ipStart, ipEnd);

					if (INBOUND.equals(tokens[0]) && TCP.equals(tokens[1])) {
						inTCP.add(rule);
					} else if (INBOUND.equals(tokens[0]) && UDP.equals(tokens[1])) {
						inUDP.add(rule);
					} else if (OUTBOUND.equals(tokens[0]) && TCP.equals(tokens[1])) {
						outTCP.add(rule);
					} else {
						outUDP.add(rule);
					}

				}
			}
			LOG.log(Level.INFO, "Finished reading file");
		} catch (Exception e1) {
			LOG.log(Level.SEVERE, "Could not find rules csv file");
			System.exit(1);
		}
		
		Collections.sort(inUDP);
		Collections.sort(outUDP);
		Collections.sort(inTCP);
		Collections.sort(outTCP);
		
		LOG.log(Level.FINE, "Finished initializing rules");
	}

	
	@Override
	public boolean acceptPacket(String direction, String protocol, int port, String ipAddress) {

		List<Rule> list = getListFromProtocolAndDirection(direction, protocol);

		int index = getFirstIndex(list, port);

		if (index == -1) {
			return false;
		}

		long ipToCheck;
		try {
			ipToCheck = IPUtils.ipToLong(ipAddress);
		} catch (FirewallException e) {
			LOG.log(Level.WARNING, "Invalid IP Address");
			return false;
		} 
		
		for (int i = index; list.get(i).getPortStart()<=port; i++) {
			if(IPUtils.isValidRange(list.get(i).getIpStart(), list.get(i).getIpEnd(), ipToCheck)) {
				return true;
			} 
		}

		return false;
	}

	private static int getFirstIndex(List<Rule> rulesList, int port) {

		int start = 0, end = rulesList.size() - 1;
		int res = -1;

		while (start <= end) {
			int middle = (start + end) / 2;
			int portEnd = rulesList.get(middle).getPortEnd();
			if (port > portEnd) {
				start = middle + 1;
			} else if (port == portEnd) {
				res = middle;
				end = middle - 1;
			} else if (port < portEnd) {
				if (port >= rulesList.get(middle).getPortStart()) {
					res = middle;
					end = middle - 1;
				} else {
					end = middle - 1;
				}
			}
		}
		
		return res;
	}

	private List<Rule> getListFromProtocolAndDirection(String direction, String protocol) {

		if (INBOUND.equalsIgnoreCase(direction) && TCP.equalsIgnoreCase(protocol)) {
			return inTCP;
		} else if (INBOUND.equalsIgnoreCase(direction) && UDP.equalsIgnoreCase(protocol)) {
			return inUDP;
		} else if (OUTBOUND.equalsIgnoreCase(direction) && TCP.equalsIgnoreCase(protocol)) {
			return outTCP;
		} else {
			return outUDP;
		}
	}

}
