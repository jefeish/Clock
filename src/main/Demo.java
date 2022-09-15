import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import java.awt.Dimension;

/**
 * Class to setup the desired 'Clocks' wit 'Clock.java'
 */
public class Demo extends JFrame
{
    public static void main(final String[] args) {
        final Demo demo = new Demo();

        final Clock clock1 = new Clock();
        final Clock clock2 = new Clock();
        final Clock clock3 = new Clock();

        Dimension dimension = clock1.getClockSize();
        demo.setSize((int)dimension.getWidth()*3, (int)dimension.getHeight()+28);
        demo.setTitle("Clock Demo");
        demo.setVisible(true);
        demo.getContentPane().setLayout(new GridLayout());

        clock1.setTitle("New York");
        clock1.setTimeZone(12);
        clock1.showDigital(true);
        clock1.grid = false;
        clock1.debug = false;
        
        clock2.setTitle("Berlin");
        clock2.setTimeZone(-6);
        clock2.showDigital(true);
        clock2.grid = false;
        clock2.debug = false;
        
        clock3.setTitle("London");
        clock3.setTimeZone(-7);
        clock3.showDigital(true);
        clock3.grid = false;
        clock3.debug = false;

        demo.getContentPane().setBackground(Color.black);
        demo.getContentPane().add(clock1);
        demo.getContentPane().add(clock2);
        demo.getContentPane().add(clock3);
        demo.setVisible(true);
    }
}