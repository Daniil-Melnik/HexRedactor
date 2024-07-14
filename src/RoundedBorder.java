import javax.swing.border.Border;
import java.awt.*;

/*
* Класс-переопределение
* Назначение: создание закруглённых углов у панелей
* */

public class RoundedBorder implements Border {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(radius, radius, radius, radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return false;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(c.getForeground());
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }
}