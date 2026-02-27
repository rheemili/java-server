import com . sun . net . httpserver . HttpServer;
import com . sun . net . httpserver . HttpHandler;
import com . sun . net . httpserver . HttpExchange;
import java . io . IOException;
import java . io . OutputStream;
import java . net . InetSocketAddress;

	public class SimpleServer {
		public static void main(String [] args) throws IOException{
			HttpServer server = HttpServer.create(
			new InetSocketAddress(8000), 0 );
			
		server.createContext("/hello", new HelloHandler());
		
		server.start();
		System.out.println("Server running on http://localhost:800");
		}
	}
	class HelloHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String response = "Hello, World!";
			
			exchange.sendResponseHeaders(200,response.length());
			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}