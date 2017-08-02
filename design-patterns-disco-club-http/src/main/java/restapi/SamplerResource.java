package restapi;

import java.util.List;

import designpatternsdiscoclub.SamplePlayer;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.payload.Payload;

@Prefix("/sampler")
public class SamplerResource {

	private final SamplePlayer samplePlayer = new SamplePlayer("app/", ".wav");

	@Get("/:sample")
	public Payload playback(String sample) {
		samplePlayer.play(sample);
		return new Payload(201);
	}

	@Get("/")
	public List<String> listSamples() {
		return samplePlayer.allSamples();
	}

}
