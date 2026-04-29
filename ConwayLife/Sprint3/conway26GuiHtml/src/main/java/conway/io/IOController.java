package conway.io;

import java.util.ArrayList;

import io.javalin.websocket.WsContext;
import main.java.conway.domain.IGrid;
import main.java.conway.domain.IOutDev;

public class IOController implements IOutDev {
	
	ArrayList<WsContext> ctxs = new ArrayList<>();
	
	public void addContext( WsContext wsContext ) {
		this.ctxs.add(wsContext);
	}

	@Override
	public void display(String msg) {
		for (WsContext ctx : ctxs) {
			ctx.send(msg);
		}

	}

	@Override
	public void displayCell(IGrid grid, int x, int y) {
		for (WsContext ctx : ctxs) {
			int alive = grid.getCell(x, y).isAlive() ? 0 : 1;
			ctx.send("cell("+x+","+y+","+alive+")");
		}
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayGrid(IGrid grid) {
		for (int x = 0; x < grid.getCols(); x++) {
			for (int y = 0; y < grid.getRows(); y++) {
				displayCell(grid, x, y);
			}
		}
	}

}
