import java.awt.Color;
import java.awt.GridLayout;

/**
 * Class to setup the desired 'Clocks' wit 'Clock.java'
 */
public class Demo extends JFrame
{
    public static void main(final String[] args) {
        final Demo demo = new Demo();
        demo.setSize(1200, 550);
        demo.setTitle("Clock Demo");
        demo.setVisible(true);
        demo.getContentPane().setLayout(new GridLayout());
        final Clock clock1 = new Clock();
        clock1.setTitle("New York");
        clock1.setTimeZone(0);
        clock1.showDigital(true);
        clock1.grid = false;
        final Clock clock2 = new Clock();
        clock2.setTitle("London");
        clock2.setTimeZone(5);
        clock2.showDigital(true);
        final Clock clock3 = new Clock();
        clock3.setTitle("Tokyo");
        clock3.setTimeZone(-3);
        clock3.showDigital(true);
        clock3.debug = false;
        demo.getContentPane().add(clock1);
        demo.getContentPane().add(clock2);
        demo.getContentPane().add(clock3);
        demo.getContentPane().setBackground(Color.black);
        demo.setVisible(true);
    }
}