import java.awt.Font;
import java.awt.RenderingHints;
import java.util.Calendar;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 */
class Clock extends JPanel implements Runnable
{
    private ImageIcon image;
    private Dimension offDimension;
    private Image offImage;
    private Graphics offGraphics;
    private String title;
    private int timeZone;
    public int pointerSize;
    public boolean debug;
    public boolean grid;
    private boolean digital;
    private volatile Thread clockThread;
    
    /**
     * 
     */
    public Clock() {
        this.image = new ImageIcon("clock2.png");
        this.offDimension = new Dimension(this.image.getIconWidth(), this.image.getIconHeight());
        this.title = "";
        this.timeZone = 6;
        this.pointerSize = 100;
        this.debug = false;
        this.grid = false;
        this.digital = false;
        this.clockThread = null;
        if (this.clockThread == null) {
            (this.clockThread = new Thread(this, "Clock")).start();
        }
        this.setSize(this.offDimension);
    }
    
    /**
     * 
     */
    public Dimension getClockSize() {
        return this.offDimension;
    }
    
    /**
     * 
     */
    public void run() {
        final Thread myThread = Thread.currentThread();
        while (this.clockThread == myThread) {
            this.repaint();
            try {
                Thread.sleep(999L);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
    
    /**
     * 
     * @param title
     */
    public void setTitle(final String title) {
        this.title = title;
    }
    
    /**
     * 
     * @param timeZone
     */
    public void setTimeZone(final int timeZone) {
        this.timeZone = timeZone;
    }
    
    /**
     * 
     * @param digital
     */
    public void showDigital(final boolean digital) {
        this.digital = digital;
    }
    
    /** 
     * 
    */
    private void grid(final Graphics g) {
        final Dimension d = this.getSize();
        final Graphics2D g2d = (Graphics2D)g;
        final int width = 1;
        final Color bg_color = g2d.getBackground();
        final int R = 255 - bg_color.getRed();
        final int G = 255 - bg_color.getGreen();
        final int B = 255 - bg_color.getBlue();
        final Color grid_color = new Color(R, G, B);
        g2d.setColor(grid_color);
        g2d.setStroke(new BasicStroke((float)width));
        for (int x = 0; x < d.getWidth(); x += 10) {
            for (int y = 0; y < d.getHeight(); y += 10) {
                g2d.drawLine(x, y, x, y);
            }
        }
    }
    
    /**
     * 
     * @param g
     * @param timeZone
     */
    private void renderClock(final Graphics g, final int timeZone) {
        final Dimension d = new Dimension(this.image.getIconWidth(), this.image.getIconHeight());
        final int fontSize = (int)Math.round(d.getWidth() * 0.08);
        final int centerX = (int)d.getWidth() / 2;
        final int centerY = (int)d.getHeight() / 2;
        this.pointerSize = (int)Math.round(d.getHeight() / 2.0 * 0.6);
        final int pointer_sec = this.pointerSize;
        final int pointer_min = this.pointerSize - (int)Math.round(this.pointerSize * 0.05);
        final int pointer_hour = (int)Math.round(this.pointerSize * 0.65);
        final int width_sec = 1;
        final int width_min = Math.round((float)(this.pointerSize / 15));
        final int width_hour = Math.round((float) (this.pointerSize / 10));
        final int second = Calendar.getInstance().get(Calendar.SECOND);
        final int minute = Calendar.getInstance().get(Calendar.MINUTE);
        int hour = Calendar.getInstance().get(Calendar.HOUR)+timeZone;
        if (hour < 0) {
            hour += 23;
        }
        else if (hour > 23) {
            hour -= 23;
        }
        final int degree_sec = second * 6 - 90;
        final int degree_min = minute * 6 - 180;
        final int degree_hour = hour * 30 + minute * 6 / 12 - 180;
        final int sec_y = (int)(pointer_sec * Math.sin(Math.toRadians(degree_sec)));
        final int sec_x = (int)(Math.cos(Math.toRadians(degree_sec)) * pointer_sec);
        final int min_x = -1 * (int)(pointer_min * Math.sin(Math.toRadians(degree_min)));
        final int min_y = (int)(Math.cos(Math.toRadians(degree_min)) * pointer_min);
        final int hour_x = -1 * (int)(pointer_hour * Math.sin(Math.toRadians(degree_hour)));
        final int hour_y = (int)(Math.cos(Math.toRadians(degree_hour)) * pointer_hour);
        final Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.white);
        g2d.clearRect(0, 0, d.width, d.height);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(this.image.getImage(), centerX - this.image.getIconWidth() / 2, centerY - this.image.getIconHeight() / 2, null, null);
        g2d.setStroke(new BasicStroke((float)width_sec));
        g2d.drawLine(centerX, centerY, centerX + sec_x, centerY + sec_y);
        g2d.setStroke(new BasicStroke((float)width_min));
        g2d.drawLine(centerX, centerY, centerX + min_x, centerY + min_y);
        g2d.setStroke(new BasicStroke((float)width_hour));
        g2d.drawLine(centerX, centerY, centerX + hour_x, centerY + hour_y);
        g2d.fillOval(centerX - (int)Math.round(this.pointerSize * 0.2) / 2, centerY - (int)Math.round(this.pointerSize * 0.2) / 2, (int)Math.round(this.pointerSize * 0.2), (int)Math.round(this.pointerSize * 0.2));
        final Font font = new Font("Lucida", 0, fontSize);
        g2d.setFont(font);
        g2d.setColor(Color.white);
        if (this.digital) {
            if (Math.abs(hour) < 10 && minute < 10) {
                g2d.drawString("0" + Math.abs(hour) + ":0" + minute, (float)(centerX - Math.round(d.getWidth() * 0.1)), (float)(centerY - Math.round(d.getHeight() * 0.15)));
            }
            else if (hour < 10) {
                g2d.drawString("0" + Math.abs(hour) + ":" + minute, (float)(centerX - Math.round(d.getWidth() * 0.1)), (float)(centerY - Math.round(d.getHeight() * 0.15)));
            }
            else if (minute < 10) {
                g2d.drawString(String.valueOf(Math.abs(hour)) + ":0" + minute, (float)(centerX - Math.round(d.getWidth() * 0.1)), (float)(centerY - Math.round(d.getHeight() * 0.15)));
            }
            else {
                g2d.drawString(String.valueOf(Math.abs(hour)) + ":" + minute, (float)(centerX - Math.round(d.getWidth() * 0.1)), (float)(centerY - Math.round(d.getHeight() * 0.15)));
            }
        }
        final int len = this.title.length();
        final int offset = len / 2 * Math.round((float)(fontSize / 2));
        g2d.drawString(this.title, (float)(centerX - offset), (float)(centerY + Math.round(d.getHeight() * 0.49)));
        if (this.debug) {
            g2d.setFont(new Font("Lucida", 0, 12));
            g2d.setColor(Color.white);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.drawString("sec =" + Calendar.getInstance().get(Calendar.SECOND) + "  x=" + sec_x + "  y=" + sec_y + "  degree=" + degree_sec + " Math.sin(" + degree_sec + ")=" + Math.toDegrees(Math.sin(degree_sec)), 10, 40);
            g2d.drawString("min =" + Calendar.getInstance().get(Calendar.MINUTE) + "  x=" + min_x + "  y=" + min_y + "  degree=" + degree_min + " Math.sin(" + degree_min + ")=" + Math.toDegrees(Math.sin(degree_min)), 10, 55);
            g2d.drawString("hour=" + Calendar.getInstance().get(Calendar.HOUR) + "  x=" + hour_x + "  y=" + hour_y + "  degree=" + degree_hour + " Math.sin(" + degree_hour + ")=" + Math.toDegrees(Math.sin(degree_hour)), 10, 70);
            g2d.drawString("image.getIconWidth()=" + this.image.getIconWidth(), 10, 85);
            g2d.drawString("image.getIconHeight()=" + this.image.getIconHeight(), 10, 100);
            g2d.drawString("centerX=" + centerX, 10, 115);
            g2d.drawString("centerY=" + centerY, 10, 130);
        }
        if (this.grid) {
            this.grid(g);
        }
    }
    
    /**
     * 
     */
    public void update(final Graphics g) {
        final Dimension d = this.getSize();
        if (this.offGraphics == null || d.width != this.offDimension.width || d.height != this.offDimension.height) {
            this.offDimension = d;
            this.offImage = this.createImage(d.width, d.height);
            this.offGraphics = this.offImage.getGraphics();
        }
        this.offGraphics.setColor(this.getBackground());
        this.offGraphics.fillRect(0, 0, d.width, d.height);
        this.offGraphics.setColor(Color.white);
        this.renderClock(this.offGraphics, this.timeZone);
        g.drawImage(this.offImage, 0, 0, null);
    }
    
    /**
     * 
     */
    public void paint(final Graphics g) {
        this.renderClock(g, this.timeZone);
    }
}