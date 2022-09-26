package client.ui.gui;

import client.ui.Inputs;
import client.ui.gui.sequence.ISequence;
import client.ui.gui.sequence.MainSequence;
import math.Vector2;
import utils.Colors;
import utils.Heartbeat;
import utils.MainLogger;
import utils.PrefixedLogger;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel implements Heartbeat.Syncable {

	public static final int UPDATE_CLOSE = 0;
	public static final int UPDATE_SUCCESS = 1;

	protected int width;
	protected int height;

	private Image image;

	private Graphics g;

	protected Heartbeat heartbeat;

	protected MainLogger logger;

	protected ISequence seq;

	protected boolean isClosed;

	protected Uno frame;

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 700;

	public MainPanel(Uno frame) {
		this.frame = frame;
		this.image = null;
		this.g = null;
		this.isClosed = false;
		this.width = WIDTH;
		this.height = HEIGHT;
		this.seq = new MainSequence(this);
		this.heartbeat = new Heartbeat(60);
		this.heartbeat.getList().add(this);

		Inputs i = Inputs.getInstance();
		this.addMouseListener(i);
		this.addKeyListener(i);
		this.addMouseMotionListener(i);

		this.logger = new PrefixedLogger("[MainPanel] ", "GUI");

		this.setPreferredSize(new Dimension(this.width, this.height));

		this.setFocusable(true);
	}

	public ISequence getSequence() {
		return seq;
	}

	public void setSequence(ISequence seq) {
		this.seq = seq;
	}

	public void start() {
		if (!this.heartbeat.isAlive()) {
			this.heartbeat.start();
			this.logger.info(Colors.wrap("Started heartbeat", Colors.BLUE_BRIGHT));
		}
	}

	public void close() {
		this.logger.info(Colors.wrap("Closing MainPanel...", Colors.YELLOW_BOLD_BRIGHT));
		this.seq.close();
		this.logger.info("Closed sequence");
		this.heartbeat.interrupt();
		this.logger.info("Interrupted heartbeat (FPS Control)");

		this.logger.info(Colors.wrap("Successfully closed MainPanel", Colors.BLUE_BRIGHT));

		this.isClosed = true;
	}

	public Heartbeat getHeartbeat() {
		return heartbeat;
	}

	public MainLogger getLogger() {
		return logger;
	}

	public boolean isClosed() {
		return isClosed;
	}

	@Override
	public void tick() {
		if (this.isClosed) {
			return;
		}

		if (!this.frame.isShowing()) {
			this.close();
			return;
		}


		if (this.gameUpdate(this.seq) == UPDATE_CLOSE) {
			this.close();
			return;
		}

		this.gameRender(this.seq);
		this.screenRender();

		Inputs.update();
	}

	private int gameUpdate(ISequence mainSeq) {
		return mainSeq.update(null);
	}

	private void gameRender(ISequence mainSeq) {
		if (this.image == null) {
			this.image = this.createImage(this.width, this.height);
			if (this.image == null) {
				this.logger.warning("Failed to create image");
			} else {
				this.g = image.getGraphics();
			}
		}

		if (this.g != null) {
			this.g.setColor(Color.gray);
			this.g.fillRect(0, 0, this.image.getWidth(null), this.image.getHeight(null));

			mainSeq.render(this.g);
		}
	}

	private void screenRender() {
		if (this.isClosed) {
			return;
		}

		Graphics g;
		try {
			g = this.getGraphics();
			if (g != null && this.image != null) {
				try {
					g.drawImage(this.image, 0, 0, null);
					g.setColor(Color.GREEN);
					g.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
					g.drawString("FPS: " + this.heartbeat.getRealTps(), 4, 14);
					g.drawString("FPS Load: " + Math.round(this.heartbeat.getLoad() * 100) + "%", 4, 30);

					g.setColor(new Color(0, 255, 0, 25));
					Vector2 mousePos = Inputs.getMousePosition().floor();
					g.drawLine(0, (int) mousePos.y, this.width, (int) mousePos.y);
					g.drawLine((int) mousePos.x, 0, (int) mousePos.x, this.height);

				} catch (Exception e) {
					this.logger.warning("Error occurred when rendering");
					e.printStackTrace();
				}
			}
			Toolkit.getDefaultToolkit().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void printComponent(Graphics g) {
	}
}
