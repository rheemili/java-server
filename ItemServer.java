import com . sun . net . httpserver . HttpServer;
import com . sun . net . httpserver . HttpHandler;
import com . sun . net . httpserver . HttpExchange;
import java . io . IOException;
import java . io . OutputStream;
import java . net . InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

 public class ItemServer {
     private static Map<String, String> items = new HashMap<>();
	 private static int nextId=1;
	public static void main ( String [] args ) throws IOException {
	 items.put("1", "Laptop");
	 items.put("2", "Keyboard");
	 items.put("3", "Mouse");
	 nextId =4;
 HttpServer server = HttpServer . create (
 new InetSocketAddress (8000) , 0
 ) ;

 server . createContext ( "/items" , new ItemsHandler () ) ;
 server . start () ;
 System . out . println ( " Server running on http :// localhost :8000 " ) ;
 }

 static class ItemsHandler implements HttpHandler {
 @Override
	public void handle ( HttpExchange exchange ) throws IOException {
	 String method = exchange.getRequestMethod();
	 String path = exchange.getRequestURI().getPath();
	 
	 System.out.println(method + " " +path);
	 
	 if(method.equals("GET") && path.equals("/items")) {
		 StringBuilder sb = new StringBuilder();
		 for(Map.Entry<String, String> entry : items.entrySet()) {
			 sb.append(entry.getKey())
			 .append(": ")
			 .append(entry.getValue())
			 .append("\n");
		 }
		 sendResponse(exchange, 200, sb.toString());
	 } else {
		 sendResponse(exchange, 404, "Not found");
	 }
	 
 }
	private void sendResponse ( HttpExchange exchange , int code ,
 String body ) throws IOException {
 byte [] bytes = body . getBytes () ;
 exchange . sendResponseHeaders ( code , bytes . length ) ;
 OutputStream os = exchange . getResponseBody () ;
 os . write ( bytes ) ;
 os . close () ;
}
 }
 }