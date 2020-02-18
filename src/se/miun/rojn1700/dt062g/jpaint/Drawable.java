package se.miun.rojn1700.dt062g.jpaint;

import javax.xml.bind.annotation.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.hypot;
/**
 * <h1>Drawables</h1>
 * This file contains classes and interfaces required to create different drawable shapes, ie. circles and rectangles.
 * <p>
 * Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author Robin Jönsson(rojn1700)
 * @version 1.4
 * @since 2018-12-18
 */

/**
 * The interface Drawable.
 */
public interface Drawable {
    /**
     * Draw.
     */
    void draw();

    /**
     * Draw.
     *
     * @param g the g
     */
    void draw(java.awt.Graphics g);
}

/**
 * The type Drawing.
 * Contains a collection of shapes to represent a full drawing. Also stores the name and the author of the drawing.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Drawing implements Drawable {
    @XmlElement
    private String name = "";
    @XmlElement
    private String author = "";
    @XmlElement(name = "shape")
    private ArrayList<Shape> shapes;

    /**
     * Instantiates a new Drawing.
     */
    public Drawing() {
        shapes = new ArrayList<>();
    }

    /**
     * Instantiates a new Drawing.
     *
     * @param name   the name
     * @param author the author
     */
    public Drawing(String name, String author) {
        shapes = new ArrayList<>();
        this.name = name;
        this.author = author;
    }

    @Override
    public void draw() {
        System.out.println("A drawing by " + author + " called " + name);
        for (int i = 0; i < shapes.size(); i++) {
            System.out.println(shapes.get(i));
        }
    }

    @Override
    public void draw(Graphics g) {
        for (Shape s : shapes) {
            s.draw(g);
        }
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Adds a shape to the ArrayList shapes.
     *
     * @param shape the shape
     */
    public void addShape(Shape shape) {
        if (shape != null) {
            shapes.add(shape);
        }
    }

    public Shape getShape(Integer index) {
        return shapes.get(index);
    }

    /**
     * Gets amount of shapes in the drawing.
     *
     * @return the size of the ArrayList shapes.
     */
    public int getSize() {
        return shapes.size();
    }

    /**
     * Gets the total circumference of the shapes of the drawing.
     *
     * @return the total circumference
     */
    public double getTotalCircumference() {
        double totalCircumference = 0;
        for (int i = 0; i < shapes.size(); i++) {
            try {
                totalCircumference += shapes.get(i).getCircumference();
            } catch (NoEndPointException e) {
            }
            ;
        }
        return totalCircumference;
    }

    /**
     * Gets the total area of the shapes of the drawing.
     *
     * @return the total area
     */
    public double getTotalArea() {
        double totalArea = 0;
        for (int i = 0; i < shapes.size(); i++) {
            try {
                totalArea += shapes.get(i).getArea();
            } catch (NoEndPointException e) {
            }
            ;
        }
        return totalArea;
    }

    public void clear() {
        shapes.clear();
        author = "";
        name = "";
    }

    @Override
    public String toString() {
        return "Drawing[name=" + getName() + ";author=" + getAuthor() + ";size=" + getSize() + ";circumference=" + getTotalCircumference() + ";area" + getTotalArea() + "]";
    }


}

/**
 * The type Point.
 * Represents a coordinate in a plane with two variables, x and y.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
class Point {
    @XmlElement
    private double x;
    @XmlElement
    private double y;

    /**
     * Instantiates a new Point.
     */
    public Point() {
    }

    /**
     * Instantiates a new Point with set variables.
     *
     * @param x the x-variable of the point.
     * @param y the y-variable of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets x.
     *
     * @param x the x-variable of the point.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets y.
     *
     * @param y the y-variable of the point.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Gets x.
     *
     * @return the x-variable of the point.
     */
    public double getX() {
        return x;
    }

    /**
     * Gets y.
     *
     * @return the y-variable of the point.
     */
    public double getY() {
        return y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

/**
 * The type Shape.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Circle.class, Rectangle.class})
abstract class Shape implements Drawable {
    @XmlElement
    private String color;
    /**
     * An array of points, to build up the shape derived from the class.
     */
    @XmlElement(name = "point")
    protected ArrayList<Point> points;

    /**
     * Instantiates a new Shape.
     *
     * @param x     the x-value of the 0-index point.
     * @param y     the y-value of the 0-index point.
     * @param color the color of the shape
     */
    public Shape(double x, double y, String color) {
        this(new Point(x, y), color);
    }

    /**
     * Instantiates a new Shape.
     *
     * @param point the 0-index point.
     * @param color the color of the shape.
     */
    public Shape(Point point, String color) {
        points = new ArrayList<>();
        this.color = color;
        points.add(point);
    }

    /**
     * Default constructor
     */
    public Shape() {
    }

    public void draw() {
    }

    public void draw(Graphics g) {
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets color.
     *
     * @param color the color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Gets the circumference of the shape.
     *
     * @return the circumference
     * @throws NoEndPointException the no end point exception
     */
    public abstract double getCircumference() throws NoEndPointException;

    /**
     * Gets the area of the shape.
     *
     * @return the area
     * @throws NoEndPointException the no end point exception
     */
    public abstract double getArea() throws NoEndPointException;

    /**
     * Adds a point to index 1 of points.
     * Index one is to be considered the end-point of the shape
     *
     * @param x the x-value of the end point.
     * @param y the y-value of the end point.
     */
    public void addPoint(double x, double y) {
        points.add(1, new Point(x, y));
    }

    /**
     * Adds a point to index 1 of points.
     *
     * @param point the end point
     */
    public void addPoint(Point point) {
        points.add(1, point);
    }
}

/**
 * The type Rectangle.
 */
@XmlRootElement
class Rectangle extends Shape {
    /**
     * Instantiates a new Rectangle.
     * The 0-index point is the upper left corner of the rectangle.
     *
     * @param x     the x-value of the 0-index point.
     * @param y     the y-value of the 0-index point.
     * @param color the color of the rectangle
     */
    public Rectangle(double x, double y, String color) {
        super(x, y, color);
    }

    /**
     * Instantiates a new Rectangle.
     * The 0-index point is the upper left corner of the rectangle. The end point is the lower right corner of the
     * rectangle.
     *
     * @param point the 0-index point.
     * @param color the of the rectangle.
     */
    public Rectangle(Point point, String color) {
        super(point, color);
    }

    /**
     * Default constructor
     */
    public Rectangle() {
        super();
    }

    ;

    public void draw() {
        System.out.println(this.toString());
    }

    public void draw(Graphics g) {
        g.setColor(Color.decode(getColor()));
        try {
            g.fillRect((int) points.get(0).getX(), (int) points.get(0).getY(), (int) getWidth(), (int)getHeight());
        } catch (NoEndPointException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gets the width of the rectangle.
     *
     * @return the width
     * @throws NoEndPointException if the second element, the end point, of points is a null object.
     */
    public double getWidth() throws NoEndPointException {
        if (points.size() == 1) {
            throw new NoEndPointException();
        }
        double width = abs(points.get(1).getX() - points.get(0).getX());
        return width;
    }

    /**
     * Gets the height of the rectangle.
     *
     * @return the height
     * @throws NoEndPointException if the second element, the end point, of points is a null object.
     */
    public double getHeight() throws NoEndPointException {
        if (points.size() == 1) {
            throw new NoEndPointException();
        }
        double height = abs(points.get(0).getY() - points.get(1).getY());
        return height;
    }


    @Override
    public double getCircumference() throws NoEndPointException {
        try {
            return 2 * getWidth() + 2 * getHeight();
        } catch (NoEndPointException e) {
            throw e;
        }
    }

    @Override
    public double getArea() throws NoEndPointException {
        try {
            return getWidth() * getHeight();
        } catch (NoEndPointException e) {
            throw e;
        }
    }

    public String toString() {
        try {
            double width = getWidth();
            double height = getHeight();
            return "Rectangle[start=" + points.get(0).toString() + ";end=" + points.get(1).toString() + ";width=" + width + ";height=" + height + ";color=" + getColor() + "]";
        } catch (NoEndPointException e) {
            return "Rectangle[start=" + points.get(0).toString() + ";end=N/A;width=N/A;height=N/A;color=" + getColor() + "]";
        }
    }
}

/**
 * The type Circle.
 */
@XmlRootElement
class Circle extends Shape {
    @XmlTransient
    private static double pi;

    /**
     * Instantiates a new Circle.
     * The 0-index point is the center of the circle. The end point is any point of the edge of the circle.
     *
     * @param x     the x-value of the 0-index point.
     * @param y     the y-value of the 0-index point.
     * @param color the color of the circle
     */
    public Circle(double x, double y, String color) {
        super(x, y, color);
    }

    /**
     * Instantiates a new Circle.
     *
     * @param point the 0-index point.
     * @param color the color of the circle.
     */
    public Circle(Point point, String color) {
        super(point, color);
    }

    /**
     * Default constructor.
     */
    public Circle() {
    }

    static {
        pi = 3.14;
    }

    public void draw() {
        System.out.println(this.toString());
    }

    public void draw(Graphics g) {
        try {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            double radius = getRadius();
            double startX = points.get(0).getX() - radius;
            double startY = points.get(0).getY() - radius;
            g.setColor(Color.decode(getColor()));
            g2.fillOval((int) startX, (int) startY, (int) getRadius() * 2, (int) getRadius() * 2);
        } catch (NoEndPointException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Gets the radius of the circle.
     *
     * @return the radius.
     * @throws NoEndPointException if the second element, the end point, of points is a null object.
     */
    public double getRadius() throws NoEndPointException {
        if (points.size() == 1) {
            throw new NoEndPointException();
        }
        double radius = hypot(abs(points.get(0).getX() - points.get(1).getX()), abs(points.get(0).getY() - points.get(1).getY()));
        return radius;
    }

    @Override
    public double getCircumference() throws NoEndPointException {
        try {
            return 2 * getRadius() * pi;
        } catch (NoEndPointException e) {
            throw e;
        }
    }

    @Override
    public double getArea() throws NoEndPointException {
        try {
            return getRadius() * getRadius() * pi;
        } catch (NoEndPointException e) {
            throw e;
        }
    }

    @Override
    public String toString() {
        try {
            double radius = getRadius();
            return "Circle[start=" + points.get(0).toString() + ";end=" + points.get(1).toString() + ";radius=" + radius + ";color=" + getColor() + "]";
        } catch (NoEndPointException e) {
            return "Circle[start=" + points.get(0).toString() + ";end=N/A;radius=N/A;color=" + getColor() + "]";
        }
    }
}