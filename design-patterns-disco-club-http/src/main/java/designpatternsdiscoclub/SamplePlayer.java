package designpatternsdiscoclub;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SamplePlayer {

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
}
