package restapi;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.payload.Payload;

@Deprecated
@Prefix("/play")
public class DirectorResource {

	@Get("/")
	public Payload playback() {
		playSamples();
		return new Payload(201);
	}

	public void playSamples() {
		try {
			Unirest.get("http://localhost:8080/sampler/gooohhh").asString();
			Unirest.get("http://localhost:8080/synth/1/5/9000").asString();
			Unirest.get("http://localhost:8080/sampler/tamb").asString();
			Thread.sleep(1000);
			Unirest.get("http://localhost:8080/sampler/gooohhh").asString();
		} catch (UnirestException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	

}
