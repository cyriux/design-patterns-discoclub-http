package restapi;

import java.util.List;

import designpatternsdiscoclub.SynthPlayer;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.payload.Payload;

/**
 * See https://fr.wikipedia.org/wiki/General_MIDI for the references of the
 * instruments
 *
 */

@Prefix("/synth")
public class SynthResource {

	private final SynthPlayer synthPlayer = new SynthPlayer();

	@Get("/")
	public List<String> listInstruments() {
		return synthPlayer.allInstruments();
	}

	@Get("/:timbre/:note/:duration")
	public Payload playback(String timbre, String note, String duration) {
		synthPlayer.playNote(timbre, note, duration);
		return new Payload(201);

	}

	@Get("/:timbre/:note")
	public Payload playback(String timbre, String note) {
		synthPlayer.playNote(timbre, note, "500");
		return new Payload(201);
	}

	@Get("/:note")
	public Payload playback(String note) {
		synthPlayer.playNote("0", note, "500");
		return new Payload(201);
	}

}
