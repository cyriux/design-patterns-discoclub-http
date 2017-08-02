package designpatternsdiscoclub;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SamplePlayer {

	private final String path;
	private final String extension;

	public SamplePlayer(String path, String extension) {
		this.path = path;
		this.extension = extension;
	}

	public void play(String sample) {
		String pathname = path + sample + extension;
		final File file = new File(pathname);
		play(file);
	}

	public void play(File file) {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			AudioFormat format = stream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			final Clip clip = (Clip) AudioSystem.getLine(info);

			clip.addLineListener(new LineListener() {
				public void update(LineEvent event) {
					if (event.getType() == LineEvent.Type.STOP)
						clip.close();
				}
			});
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			throw new RuntimeException(e.getClass().getSimpleName() + " " + e.getMessage());
		}
	}

	public List<String> allSamples() {
		final File file = new File(path);
		return Arrays.asList(file.listFiles()).stream().map(f -> f.getName()).collect(Collectors.toList());
	}
}
