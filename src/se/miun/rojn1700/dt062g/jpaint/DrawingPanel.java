package se.miun.rojn1700.dt062g.jpaint;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;

/** TODO:
 * <h1>DrawingPanel</h1>
 * This file contains the class DrawingPanel, and extension of JPanel that allows the user to draw shapes on it. The
 * shapes need to be implemented as Drawables.
 * <p>
 * Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author Robin Jönsson(rojn1700)
 * @version 1.1
 * @since 2018-01-02
 */
public class DrawingPanel extends JPanel {
    private Drawing drawing;

    /**
     * Instantiates a new DrawingPanel with an empty drawing.
     */
    DrawingPanel() {
        drawing = new Drawing();
    }

    /**
     * Instantiates a new DrawingPanel with a set drawing.
     * @param drawing the drawing to instantiate the DrawingPanel with.
     */
    DrawingPanel(Drawing drawing) {
        setDrawing(drawing);
    }

    /**
     * Setter for drawing.
     *
     * @param drawing the Drawing to set.
     */
    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
        paintComponent(getGraphics());
    }

    /**
     * Adds a drawing to the current drawing. The shapes from the parameter will be added to the shapes of the
     * class member Drawing.
     *
     * @param drawing the Drawing to add to the current Drawing.
     */
    public void addDrawing(Drawing drawing) {
        String drawingToString = drawing.toString();
        for(int i = 0; i < drawing.getSize(); i++) {
            this.drawing.addShape(drawing.getShape(i));
        }
        paintComponent(getGraphics());
    }

    /**
     * Getter for drawing.
     *
     * @return the drawing.
     */
    public Drawing getDrawing() { return drawing;  }

    /**
     * Paints the shapes on the DrawingPanel.
     *
     * @param g the Graphics on which to draw the Drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0,0,getWidth(),getHeight());
        drawing.draw(g);
    }
}
