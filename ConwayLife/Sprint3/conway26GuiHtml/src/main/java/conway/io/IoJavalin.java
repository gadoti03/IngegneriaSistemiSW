package conway.io;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import unibo.basicomm23.utils.CommUtils;
import main.java.conway.domain.*;

public class IoJavalin {
	
	private GameController gameController;
	private IOController ioController = new IOController();
	private String owner = "";
	private boolean running = false;
	
	public IoJavalin() {		
        var app = Javalin.create(config -> {
			config.staticFiles.add(staticFiles -> {
				staticFiles.directory = "/page";
				staticFiles.location = Location.CLASSPATH; // Cerca dentro il JAR/Classpath
				/*
				 * i file sono "impacchettati" con il codice, non cercati sul disco rigido esterno.
				 */
		    });
		}).start(8080);
        
        app.get("/", ctx -> {
    		Path path = Path.of("./src/main/resources/page/ConwayInOutPage.html");   
		    if (Files.exists(path)) {
		        // Usiamo Files.newInputStream che è più moderno di FileInputStream
		        ctx.contentType("text/html").result(Files.newInputStream(path));
		    } else {
		        ctx.status(404).result("File non trovato nel file system");
		    }
		    //ctx.result("Hello from Java!"));  //la forma più semplice di risposta
        }); 
        
        app.get("/greet/{name}", ctx -> {
            String name = ctx.pathParam("name");
            ctx.result("Hello, " + name + "!");
        }); //http://localhost:8080/greet/Alice
        
        app.get("/api/users", ctx -> {
            Map<String, Object> user = Map.of("id", 1, "name", "Bob");
            ctx.json(user); // Auto-converts to JSON
        });
        
        /*
         * Javalin v5+): Si passa solo la "promessa" (il Supplier del Future). 
         * Javalin è diventato più intelligente: se il Future restituisce una Stringa, 
         * lui fa ctx.result(stringa). Se restituisce un oggetto, lui fa ctx.json(oggetto).
         * 
         */
        app.get("/async", ctx -> {
        	ctx.future(() -> {
	        	// Creiamo il future
	            CompletableFuture<String> future = new CompletableFuture<>();
	            
	            // Eseguiamo il lavoro in un altro thread
	            new Thread(() -> { 
	                try {
	                    Thread.sleep(2000); // Simulazione calcolo pesante
	                    future.complete("Risultato calcolato asincronamente");
	                } catch (Exception e) {
	                    future.completeExceptionally(e);
	                }
	            });
	            
	            return future; // Restituiamo il future a Javalin
        	});
        });
        
        app.get("/async1", ctx -> {
            ctx.future(() -> CompletableFuture.supplyAsync(() -> {
                // Simuliamo l'operazione lenta
                try {
                    Thread.sleep(2000); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Risultato calcolato con supplyAsync";
            }));
        });
        
        app.ws("/chat", ws -> {
            ws.onConnect(ctx -> {
            	if (gameController == null) {
            		ILife life = new Life(20,20);
                	ioController.addContext(ctx);
                	gameController = new LifeController(life, ioController);
				} else {
					ioController.addContext(ctx);
				}
            	if(running) {
            		ctx.send("running");
				}
            	System.out.println("Client connected!");
        	});
            ws.onMessage(ctx -> {
                String message = ctx.message();
            	System.out.println("chat: "+message);
            	String[] parts = message.split("::");
            	String sender = parts[0];
            	if (owner.isEmpty() || owner.equals(sender)) {
					owner = sender;
	            	String command = parts[1];
	            	System.out.println("sender: "+sender+" command: "+command);
	            	if(command.startsWith("cell")) {
	            		String[] _parts = command.substring(5, command.length() - 1).split(",");
	            		int x = Integer.parseInt(_parts[0]);
	            		int y = Integer.parseInt(_parts[1]);
	            		gameController.switchCellState(x, y);
					}
	            	if(command.equals("start")) {
						gameController.onStart();
						running = true;
					}
					if(command.equals("stop")) {
						gameController.onStop();
						running = false;
					}
					if(command.equals("clear")) {
						gameController.onClear();
					}
					if(command.equals("exit")) {
						gameController.onStop();
						ctx.closeSession();
						running = false;
					}
				}
            });
            ws.onClose(ctx ->{
            	gameController.onStop();
            });
        });        
	}
	
 
	

	
	public static void main(String[] args) {
		var resource = IoJavalin.class.getResource("/pages");
		CommUtils.outgreen("DEBUG: La cartella /page si trova in: " + resource);
		new IoJavalin();
	}

}
