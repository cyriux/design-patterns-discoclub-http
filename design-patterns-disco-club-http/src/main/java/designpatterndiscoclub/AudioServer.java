package designpatterndiscoclub;
import net.codestory.http.WebServer;

public class AudioServer {

	public static void main(String[] args) {
		new WebServer().configure(routes -> routes
				.get("/", "<h1>Welcome to the Design Pattern Disco Club HTTP!</h1>")
				.add(new SamplerResource())
				.add(new SynthResource())
				.add(new DirectorResource())
		).start();
	}
}
