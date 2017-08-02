package designpatternsdiscoclub;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

public class SynthPlayer {

	private final Synthesizer synthesizer;
	private final Instrument[] instruments;
	private final Timer timer = new Timer(false);

	public SynthPlayer() {
		this.synthesizer = initSynth();
		instruments = synthesizer == null ? null : synthesizer.getAvailableInstruments();
		System.out.println(toString());
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

	public List<String> allInstruments() {
		return Arrays.asList(instruments).stream().map(i -> i.toString()).collect(Collectors.toList());
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

	@Override
	public String toString() {
		return "SynthPlayer: " + allInstruments();
	}
}
