import com . sun . net . httpserver . HttpServer;
import com . sun . net . httpserver . HttpHandler;
import com . sun . net . httpserver . HttpExchange;
import java . io . IOException;
import java . io . OutputStream;
import java . net . InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.io.InputStream;

 public class ItemServer {
     private static Map<String, String> items = new HashMap<>();
	 private static int nextId=1;
	public static void main ( String [] args ) throws IOException {
	 items.put("1", "Laptop");
	 items.put("2", "Keyboard");
	 items.put("3", "Mouse");
	 nextId =4;
 HttpServer server = HttpServer . create (
 new InetSocketAddress (9006) , 0
 ) ;

 server . createContext ( "/items" , new ItemsHandler () ) ;
 server . start () ;
 System . out . println ( " Server running on http :// localhost :9006 " ) ;
 server.createContext("/health", new HealthHandler());
 }

 static class ItemsHandler implements HttpHandler {
 @Override
	public void handle ( HttpExchange exchange ) throws IOException {
	 String method = exchange.getRequestMethod();
	 String path = exchange.getRequestURI().getPath();
	 
	 System.out.println(method + " " + path);
	 
	 if(method.equals("GET")) {
		 handleGet(exchange,path);
	 } else if (method.equals("POST") && path.equals("/items")) {
		 handlePost(exchange);
	 } else {
		 sendResponse(exchange, 405, "Method Not Allowed");
	 } 
 }
	private void handlePost(HttpExchange exchange) throws IOException {
		
		InputStream is = exchange.getRequestBody();
		String body = new String(is.readAllBytes());
		is.close();
		
		if(body.isEmpty()) {
			sendResponse(exchange, 400, "{\"error\": \"Item name required\"}");
			return;
		}
		
		String id = String.valueOf(nextId++);
		String name = body.trim();
		items.put(id, name);
		
		String json = "{\"id\": \"" + id + "\", \"name\": \"" + name + "\"}";
		sendResponse(exchange, 201, json);
	}
	private void handleGet (HttpExchange exchange, String path) throws IOException {
	  
	 if (path.equals("/items")) {
		 StringBuilder sb = new StringBuilder();
		 sb.append("[\n");
		 boolean first = true;
		 for(Map.Entry<String, String> entry : items.entrySet()) {
			 if(!first) {
				 sb.append(", \n");
			 }
			 sb.append("  {\"id\": \"")
			 .append(entry.getKey())
			 .append("\", \"name\": \"")
			 .append(entry.getValue())
			 .append("\"}");
			first = false;
		 }
	 sb.append("\n]");
		 sendResponse(exchange, 200, sb.toString());
	 } else if (path.matches("/items/\\d+")){
		 String id = path.substring(7);
		 String item = items.get(id);
		 
		 if(item != null) {
			 String json = "{\"id\": \"" + id + "\", \"name\": \"" + item + "\"}";
			 sendResponse(exchange, 200, json);
		 } else {
			 sendResponse(exchange, 404, "{\"error\": \"Item not found\"}");
		 }
		 
	 } else {
		 sendResponse(exchange, 404, "Not found");
	 }
 }

private void sendResponse ( HttpExchange exchange , int code ,String body )
	throws IOException {
		exchange.getResponseHeaders().set("Content-Type", "application/json");
		
		byte[] bytes = body . getBytes ();
		exchange .sendResponseHeaders( code , bytes.length );
		OutputStream os = exchange.getResponseBody();
		os.write( bytes );
		os.close();
		}
	}
	static class HealthHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange)
			throws IOException {
				String response = "ok";
				exchange.sendResponseHeaders(200, reponse.length());
				OutputStream os = exchange.getResponseBody();
				os.write(reponse.getBytes());
				os.close();
			}
	}
 }