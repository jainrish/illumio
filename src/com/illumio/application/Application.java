package com.illumio.application;

import java.util.Scanner;

import com.illumio.firewall.Firewall;

public class Application {

	public static void main(String[] args) {

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Enter the file path:\n");
			String filePath = sc.nextLine();
			String[] packet;

			Firewall firewall = new Firewall(filePath);
			while (true) {
				System.out.println(
						"Enter space seperated direction, protocol, port and ip address or press 'q' to quit\n");
				packet = sc.nextLine().split(" ");

				if (packet.length == 1 && "q".equalsIgnoreCase(packet[0])) {
					System.exit(0);
				}

				if (packet.length != 4) {
					System.out.println("Invalid packet, please try again");
					continue;
				}

				System.out.println(firewall.acceptPacket(packet[0], packet[1], Integer.parseInt(packet[2]), packet[3]));
			}
		}
	}

}
