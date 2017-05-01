package com.github.albahrani.aquacontrol.client.api;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status.Family;

import org.glassfish.jersey.jackson.JacksonFeature;

public class GetChannels {
	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);
		Response response = client.target("http://localhost:8081/channels").request().get();
		if (Family.SUCCESSFUL.equals(response.getStatusInfo().getFamily())) {
			Map<String, JSONConfigurationChannel> channels = response
					.readEntity(new GenericType<Map<String, JSONConfigurationChannel>>() {
					});
			System.out.println("Channel Configuration");
			System.out.println("=====================");
			String lineFormat = "%-10s|%-15s|%-15s|%-30s%n";
			System.out.printf(lineFormat, "ID", "Display Name", "Color", "Pins");
			System.out.println();
			channels.forEach((channelId, config) -> {
				System.out.printf(lineFormat, channelId, config.getName(), config.getColor(), config.getPins());
			});

		} else {
			System.err.println("Error while trying to receive channels.");
			System.err.println(response.getStatusInfo().getReasonPhrase());
		}
		response.close();
		client.close();
		
		Scanner scanner = new Scanner(System.in);
		scanner.hasNextLine();
		scanner.close();
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
