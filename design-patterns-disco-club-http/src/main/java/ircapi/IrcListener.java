package ircapi;

import java.io.IOException;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import designpatternsdiscoclub.SamplePlayer;
import designpatternsdiscoclub.SynthPlayer;

public class IrcListener extends ListenerAdapter {

	private final SynthPlayer synth = new SynthPlayer();
	private final SamplePlayer sampler = new SamplePlayer("app/", ".wav");

	@Override
	public void onGenericMessage(GenericMessageEvent event) {
		// When someone says ?helloworld respond with "Hello World"
		System.out.println(event.getMessage());
		if (event.getMessage().startsWith("?helloworld"))
			event.respond("Hello world!");
		if (event.getMessage().startsWith("audioserver sampler/"))
			event.respond(sampler.allSamples().toString());
		if (event.getMessage().startsWith("audioserver synth/"))
			event.respond(synth.allInstruments().toString());
	}

	public static void main(String[] args) throws Exception {
		startIrcServer();
	}

	public static void startIrcServer() {
		// may use a web client like https://kiwiirc.com to test IRC
		final String botNiickname = "DesignPatternsDiscoClubBot";
		final String networkName = "irc.foonetic.net";
		final String channelName = "#designpatternsdiscoclub";
		Configuration configuration = new Configuration.Builder().setName(botNiickname).addServer(networkName)
				.addAutoJoinChannel(channelName).addListener(new IrcListener()).buildConfiguration();
		PircBotX bot = new PircBotX(configuration);
		try {
			bot.startBot();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}
}
