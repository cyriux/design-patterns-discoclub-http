package main;

import ircapi.AudioIrcListener;
import restapi.AudioServer;

public class Main {

	public static void main(String[] args) {
		AudioIrcListener.startIrcServer();
		AudioServer.startWebServer();
	}

}
