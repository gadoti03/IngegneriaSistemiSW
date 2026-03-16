package conway.io;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsContext;
import io.javalin.websocket.WsMessageContext;
import main.java.conway.domain.GameController;
import main.java.conway.domain.IGrid;
import main.java.conway.domain.IOutDev;
import main.java.conway.domain.Life;
import main.java.conway.domain.LifeController;
import main.java.conway.domain.LifeInterface;
import unibo.basicomm23.utils.CommUtils;
import unibo.basicomm23.interfaces.IApplMessage;
import unibo.basicomm23.msg.ApplMessage;

public class IoJavalin implements IOutDev {
	
	private WsContext context;
	
	private LifeInterface life;
	private GameController controller; 
	
	private WsMessageContext pageCtx ;
	public IoJavalin() {
		
		/* Aggiunto il 12/03/2026: Alloco "Life" e "LifeController" */
		life = new Life(20, 20);
		controller = new LifeController(life, this);
		
		
		
        var app = Javalin.create(config -> {
			config.staticFiles.add(staticFiles -> {
				staticFiles.directory = "/page";
				staticFiles.location = Location.CLASSPATH; // Cerca dentro il JAR/Classpath
				/*
				 * i file sono "impacchettati" con il codice, non cercati sul disco rigido esterno.
				 */
		    });
		}).start(8080);
 
/*
 * --------------------------------------------
 * Parte HTTP        
 * --------------------------------------------
 */
        app.get("/", ctx -> {
    		//Path path = Path.of("./src/main/resources/page/ConwayInOutPage.html");    		    
        	/*
        	 * Java cercherà il file all'interno del Classpath 
        	 * (dentro il JAR o nelle cartelle dei sorgenti di Eclipse), 
        	 * rendendo il codice universale
         	 */
        	var inputStream = getClass().getResourceAsStream("/page/ConwayInOutPage.html");       	
        	if (inputStream != null) {
        		// Trasformiamo l'inputStream in stringa (o lo mandiamo come stream)
        	    String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        	    ctx.html(content);
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
         * Javalin v5+: Si passa solo la "promessa" (il Supplier del Future). 
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
	                    future.complete("IoJavalin | Risultato calcolato asincronamente");
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
                return "IoJavalin | Risultato calcolato con supplyAsync";
            }));
        });
/*
 * --------------------------------------------
 * Parte Websocket
 * --------------------------------------------
 */
        // WebSocket per la chat (non utilizzata per ora...)
        app.ws("/chat", ws -> {
            ws.onConnect(ctx -> CommUtils.outgreen("Client connected chat!"));
            ws.onMessage(ctx -> {
                String message = ctx.message();
                CommUtils.outcyan("IoJavalin |  riceve:" + message);
                ctx.send("Echo: " + message);
            });
        });
        
        // WebSocket per la comunicazione con la pagina (comandi e aggiornamenti)
        app.ws("/eval", ws -> {
            ws.onConnect(ctx -> CommUtils.outgreen("IoJavalin | Client connected eval"));
            ws.onMessage(ctx -> {
                String message = ctx.message();     

                // CommUtils.outblue("IoJavalin |  eval receives:" + message );
               
                try {
                	IApplMessage m = new ApplMessage(message);
                    String content = m.msgContent();
                    CommUtils.outblue("IoJavalin |  eval content:" + content );
                    
                    if( content.equals("ready")) { 
                    	pageCtx = ctx;  //memorizzo connessione pagina
                    	displayGrid(life.getGrid());
                    } else if( content.equals("start")) { 
                    	controller.onStart();
                    } else if( content.equals("stop")) {
                    	controller.onStop();
                    } else if( content.equals("clear")) {
                    	controller.onClear();
                    } else if( content.contains("cell(")) { 
                    	// cell(x,y)
                    	String coords = content.replace("cell(", "").replace(")", "");
                    	String[] parts = coords.split(",");

                    	int x = Integer.parseInt(parts[0].trim());
                    	int y = Integer.parseInt(parts[1].trim());
                    	
						controller.switchCellState(x, y);

						CommUtils.outmagenta("IoJavalin | switch cell (" + x + "," + y + ")");
                    } else {
                    	ctx.send(content); // echo for unknown commands
                    }
                }catch(Exception e) {
                	CommUtils.outred("IoJavalin |  error:" + e.getMessage());
                }               
            });
        });        
	}
	
 
	

	
	public static void main(String[] args) {
		var resource = IoJavalin.class.getResource("/page");
		CommUtils.outgreen("DEBUG: La cartella /page si trova in: " + resource);
		new IoJavalin();
	}





	@Override
	public void display(String msg) {
		if (pageCtx != null) {
			pageCtx.send(msg);
		}
	}





	@Override
	public void displayCell(IGrid grid, int x, int y) {
		boolean alive = grid.getCellValue(x, y);
		int color = alive ? 1 : 0; // 1=live, 0=dead (ripristinato come all'inizio)
		if (pageCtx != null) {
			pageCtx.send("cell(" + x + "," + y + "," + color + ")");
		}
	}


	@Override
	public void close() {
		// Chiudi risorse se necessario
	}


	@Override
	public void displayGrid(IGrid grid) {
		for (int i = 0; i < grid.getRowsNum(); i++) {
			for (int j = 0; j < grid.getColsNum(); j++) {
				displayCell(grid, i, j);
			}
		}
	}

}
