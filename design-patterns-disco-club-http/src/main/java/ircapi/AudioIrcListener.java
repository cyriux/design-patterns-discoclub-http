package ircapi;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.exception.DaoException;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;

import designpatternsdiscoclub.SamplePlayer;
import designpatternsdiscoclub.SynthPlayer;

public class AudioIrcListener extends ListenerAdapter {

	private final SynthPlayer synth = new SynthPlayer();
	private final SamplePlayer sampler = new SamplePlayer("app/", ".wav");
	private final static Timer metronome = new Timer(false);

	@Override
	public void onGenericMessage(GenericMessageEvent event) {
		final String msg = event.getMessage();
		System.out.println(msg);
		final String response = parse(msg);
		if (response != null) {
			event.respond(response);
		}
	}

	public String parse(String msg) {
		if (msg.equals("beat")) {
			sampler.play("vox5");
			return null;
		}
		if (msg.equals("sampler/")) {
			return sampler.allSamples().toString();
		}
		if (msg.equals("synth/")) {
			return synth.allInstruments().toString();
		}

		final String[] args = msg.split("/");
		if (args.length < 2) {
			return null;
		}
		final String resource = args[0];
		if (resource.equals("sampler")) {
			sampler.play(get(args, 1, "tamb"));
			return null;
		}
		if (resource.equals("synth")) {
			synth.playNote(get(args, 2, "0"), get(args, 1, "64"), get(args, 3, "500"));
			return null;
		}
		return null;
	}

	private final static String get(String[] args, int index, String defaultValue) {
		if (index < args.length) {
			return args[index];
		}
		return defaultValue;
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
				.addAutoJoinChannel(channelName).addListener(new AudioIrcListener()).buildConfiguration();
		final PircBotX bot = new PircBotX(configuration);
		try {
			final TimerTask timerTask = new TimerTask() {
				@Override
				public void run() {
					try {
						System.out.println("timer task");
						final long dayTimeMs = System.currentTimeMillis() % 86400000;
						final int step = (int) ((dayTimeMs / 1000) % 4);
						bot.getUserChannelDao().getChannel(channelName).send()
								.message("beat/" + dayTimeMs + "/" + step);

					} catch (DaoException e) {
						e.printStackTrace();
					}
				}
			};
			metronome.scheduleAtFixedRate(timerTask, 0, 1000);
			bot.startBot();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IrcException e) {
			e.printStackTrace();
		}
	}
}
