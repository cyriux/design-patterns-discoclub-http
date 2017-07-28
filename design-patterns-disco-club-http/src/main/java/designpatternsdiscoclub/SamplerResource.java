package designpatternsdiscoclub;
import java.io.File;

import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.payload.Payload;

@Prefix("/sampler")
public class SamplerResource {

	private final SamplePlayer samplePlayer = new SamplePlayer();

	@Get("/:sample")
	public Payload playback(String sample) {
		String pathname = "app/" + sample + ".wav";
		final File file = new File(pathname);
		samplePlayer.play(file);
		return new Payload(201);
	}
	
	
	
	
}
