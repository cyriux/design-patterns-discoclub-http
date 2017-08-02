package main;

import ircapi.IrcListener;
import restapi.AudioServer;

public class Main {

	public static void main(String[] args) {
		IrcListener.startIrcServer();
		AudioServer.startWebServer();
	}

}
