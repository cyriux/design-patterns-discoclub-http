package designpatternsdiscoclub;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

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

	private final Synthesizer synthesizer;
	private final Instrument[] instruments;
	private final Timer timer = new Timer(false);

	public SynthResource() {
		this.synthesizer = initSynth();
		instruments = synthesizer == null ? null : synthesizer.getAvailableInstruments();
	}

	private static Synthesizer initSynth() {
		try {
			Synthesizer s = MidiSystem.getSynthesizer();
			s.open();
			return s;
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Get("/:timbre/:note/:duration")
	public Payload playback(String timbre, String note, String duration) {
		playNote(timbre, note, duration);
		return new Payload(201);

	}

	@Get("/:timbre/:note")
	public Payload playback(String timbre, String note) {
		playNote(timbre, note, "500");
		return new Payload(201);
	}

	@Get("/:note")
	public Payload playback(String note) {
		playNote("0", note, "500");
		return new Payload(201);
	}

	public void playNote(String timbre, String note, String duration) {
		int channel = 0;
		int noteNumber = Integer.parseInt(note);
		int instrumentNumber = Integer.parseInt(timbre);
		int durationMs = Integer.parseInt(duration);
		if (instrumentNumber >= instruments.length) {
			instrumentNumber = 0;
		}
		Instrument instrument = instruments[instrumentNumber];
		synthesizer.loadInstrument(instrument);
		MidiChannel[] channels = synthesizer.getChannels();
		MidiChannel mc = channels[channel];
		mc.programChange(instrument.getPatch().getProgram());
		mc.noteOn(noteNumber, 120);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				mc.noteOff(noteNumber);
			}
		}, durationMs);
	}

}
