package com.github.albahrani.aquacontrol.client.api;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;

public class AddChannel {
	public static void main(String[] args) {
		JSONConfigurationChannel channel = new JSONConfigurationChannel();
		channel.setName("white");
		channel.setColor("#000000");
		List<String> pinList = new LinkedList<>();
		pinList.add("PWM 0");
		pinList.add("PWM 1");
		channel.setPins(pinList);

		Client client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);
		Response response = client.target("http://localhost:8081/channels/ch1")
				.request()
				.put(Entity.entity(channel, MediaType.APPLICATION_JSON_TYPE));
		System.out.println(response.getStatus());
		System.out.println(response.getStatusInfo());
		response.close();
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
