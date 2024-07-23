import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Graphics;

/**
 * A class that implements the Border interface to create rounded corners for
 * panels.
 *
 * @see Border
 * @see Component
 * @see Insets
 * @see Graphics
 * @see Color
 * @see Rectangle
 * @author DMelnik
 * @version 1.0
 */

public class RoundedBorder implements Border {
    private int radius;

    /**
     * Constructs a RoundedBorder with the specified radius for the corners.
     *
     * @param radius the radius of the rounded corners
     */

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    /**
     * Returns the insets of the border.
     *
     * @param c the component for which this border insets value applies
     * @return the insets of the border
     */

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    /**
     * Returns whether or not the border is opaque.
     *
     * @return false, because the border is not opaque
     */

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    /**
     * Paints the border for the specified component with the specified position and
     * size.
     *
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(c.getForeground());
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}