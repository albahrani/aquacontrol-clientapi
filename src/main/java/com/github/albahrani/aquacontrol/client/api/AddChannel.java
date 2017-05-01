package com.github.albahrani.aquacontrol.client.api;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.jersey.jackson.JacksonFeature;

public class AddChannel {
	public static void main(String[] args) {
		System.out.println("Adding channels");
		Client client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);

		try (Scanner scanner = new Scanner(System.in)) {
			boolean done = false;
			while (!done) {
				JSONConfigurationChannel channel = new JSONConfigurationChannel();
				System.out.print("Channel ID: ");
				String channelId = scanner.nextLine();
				System.out.print("Display Name: ");
				channel.setName(scanner.nextLine());
				System.out.print("Color: ");
				channel.setColor(scanner.nextLine());
				List<String> pinList = new LinkedList<>();
				System.out.println("Now we add pins to the channel.");
				System.out.println("Define a pin number and press enter.");
				System.out.println("('X' means you are done adding pins)");
				String pinIndex = "xxx";
				while (!"X".equalsIgnoreCase(pinIndex)) {
					System.out.print(" Pin: PWM ");
					pinIndex = scanner.nextLine();
					pinList.add("PWM " + pinIndex);
				}
				channel.setPins(pinList);

				Response response = client.target("http://localhost:8081/channels/" + channelId).request()
						.put(Entity.entity(channel, MediaType.APPLICATION_JSON_TYPE));

				if (Family.SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
					System.out.println("Channel " + channelId + " added successfully.");
				} else {
					System.err.println("Error while adding channel " + channelId);
					System.err.println(response.getStatusInfo().getReasonPhrase());
				}
				response.close();

				System.out.println("Do you want to add another channel? (y=yes, n=no)");
				String anotherChannel = scanner.nextLine();
				if ("y".equalsIgnoreCase(anotherChannel)) {
					done = false;
				} else {
					done = true;
				}
			}
		}

		client.close();
	}

	static class JSONConfigurationChannel {
		private String name;

		private String color;

		private List<String> pins;

		public List<String> getPins() {
			return pins;
		}

		public String getName() {
			return name;
		}

		public void setName(String channelName) {
			this.name = channelName;
		}

		public void setPins(List<String> pins) {
			this.pins = pins;
		}

		public String getColor() {
			return this.color;
		}

		public void setColor(String color) {
			this.color = color;
		}
	}
}
