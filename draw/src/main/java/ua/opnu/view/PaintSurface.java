package ua.opnu.view;

import ua.opnu.model.DrawShape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Поверхня для малювання фігур. Звичайний компонент Swing
 */
public class PaintSurface extends JComponent {

    // Список фігур, які намальовані на поверхні
    private final List<DrawShape> shapes = new ArrayList<>();

    // Тип поточної фігури
    private int shapeType;

    // Тут ми зберігаємо координати миші на початку перетягування і
    // при його закінченні. Клас Point зберігає координати точки (x, y)
    private Point startDrag;
    private Point endDrag;

    // Перелік кольорів
    private final List<Color> colors = Arrays.asList
            (Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.RED, Color.BLUE, Color.PINK);

    public PaintSurface() {

        // Спочатку тип фігури дорівнює 0
        shapeType = 0;

        // Встановлюємо бажані розміри поверхні.
        // Так як ми успадкувались від класу JComponent,
        // ми викликаємо метод цього класу
        super.setPreferredSize(new Dimension(400, 400));

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                startDrag = new Point(e.getX(), e.getY());
                endDrag = startDrag;
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                DrawShape shape = DrawShape.newInstance(shapeType);

                // Якщо shape == null — означає невідомий тип — нічого не додаємо
                if (shape != null) {
                    shape.setStartPoint(startDrag);
                    shape.setEndPoint(endDrag);
                    shapes.add(shape);
                }

                startDrag = null;
                endDrag = null;
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endDrag = new Point(e.getX(), e.getY());
                repaint();
            }
        });
    }

    // Встановлення типу фігури
    public void setShapeType(int type) {
        this.shapeType = type;
    }

    /*
     * Метод paint викликається автоматично, коли відбувається перемалювання
     * вікна.
     */
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        paintBackgroundGrid(g2);
        g2.setStroke(new BasicStroke(2));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));

        // Малюємо збережені фігури
        for (DrawShape s : shapes) {
            if (s == null) continue;
            g2.setPaint(Color.BLACK);
            Shape sh = s.getShape();
            if (sh != null) {
                g2.draw(sh);
                g2.setPaint(colors.get(shapes.indexOf(s) % colors.size()));
                g2.fill(sh);
            }
        }

        // Малюємо сірий попередній контур якщо малюємо поточну фігуру
        if (startDrag != null && endDrag != null) {
            g2.setPaint(Color.LIGHT_GRAY);
            DrawShape preview = DrawShape.newInstance(shapeType);
            if (preview != null) {
                Shape pShape = preview.getShape(startDrag, endDrag);
                if (pShape != null) g2.draw(pShape);
            }
        }
    }

    // Очищення всіх фігур (для кнопки Clear)
    public void clearShapes() {
        shapes.clear();
        repaint();
    }

    // Фоновий малюнок (сітка)
    private void paintBackgroundGrid(Graphics2D g2) {
        g2.setPaint(Color.LIGHT_GRAY);
        for (int i = 0; i < getSize().width; i += 10) {
            Shape line = new Line2D.Float(i, 0, i, getSize().height);
            g2.draw(line);
        }
        for (int i = 0; i < getSize().height; i += 10) {
            Shape line = new Line2D.Float(0, i, getSize().width, i);
            g2.draw(line);
        }
    }
}
