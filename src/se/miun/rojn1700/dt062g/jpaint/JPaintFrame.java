package se.miun.rojn1700.dt062g.jpaint;

import se.miun.rojn1700.dt062g.jpaint.client.Client;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * <h1>JPaintFrame</h1>
 * This file contains the class JPaintFrame, which derives from the class JFrame. JPaintFrame is a preset frame, with
 * an area on which you can draw shapes
 * <p>
 * Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 * @author Robin Jönsson(rojn1700)
 * @version 1.2
 * @since 2019-01-02
 */
public class JPaintFrame extends JFrame {

    private Client c;
    private JFrame frame;
    private String colorChoice;
    private DrawingPanel drawingArea;
    private JLabel mousePosLabel;
    private JLabel colorLabel;
    private JComboBox shapeList;
    private java.awt.Point drawEventStart;

    /**
     * Instantiates a new JPaintFrame.
     * Adds menu, toolbar and statusbar as well as the main drawing area, with all necessary action listeners
     */
    JPaintFrame() { this(new Client()); }

    JPaintFrame(Client c) {
        this.c = c;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the menu and add it to the frame
        createMenu();

        // Create toolbar for color and shape choice and add it to the frame
        createToolbar();

        // Create drawing area and add it to the frame
        createDrawingArea();

        // Create statusbar and add it to the frame
        createStatusbar();

        //Display the window.
        updateWindowTitle();
        frame.pack();
    }

    @Override
    public void setVisible(boolean b) {
        frame.setVisible(true);
    }

    /**
     * Update window title.
     * The window title will be updates to show the name of the drawing and the author, if names have been set.
     */
    protected void updateWindowTitle() {
        String title = "PaintMaster";
        Drawing drawing = drawingArea.getDrawing();
        if (drawing.getName() != "") {
            title += " - " + drawing.getName();
            if (drawing.getAuthor() != "") {
                title += " by " + drawing.getAuthor();
            }
        } else if (drawing.getAuthor() != "") {
            title += " - " + drawing.getAuthor();
        }
        frame.setTitle(title);
    }

    /**
     * Update the chosen color.
     * The color of the statusbar will be updated, as well as the member variable colorChoice
     *
     * @param color the chosen color
     */
    protected void updateColor(Color color) {
        colorChoice = String.format("#%06x", color.getRGB() & 0x00FFFFFF);

        BufferedImage bImg = new BufferedImage(12, 12, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bImg.createGraphics();
        graphics.setPaint(color);
        graphics.fillRect(0, 0, bImg.getWidth(), bImg.getHeight());
        ImageIcon colorIcon = new ImageIcon(bImg);
        colorLabel.setIcon(colorIcon);
        colorLabel.updateUI();
    }

    /**
     * Creates a menu and adds it to the frame.
     */
    protected void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        // Create File menu
        JMenu menu = new JMenu("File");

        // Shortcut Alt + F
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);
        JMenuItem menuItem = new JMenuItem("New...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Drawing drawing = new Drawing();
                String name = JOptionPane.showInputDialog(frame, "Enter name of the drawing:", "Specify name", JOptionPane.PLAIN_MESSAGE);
                drawing.setName(name);
                String author = JOptionPane.showInputDialog(frame, "Enter author of the drawing:", "Specify author", JOptionPane.PLAIN_MESSAGE);
                drawing.setAuthor(author);
                drawingArea.setDrawing(drawing);
                updateWindowTitle();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Save as...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = getDrawingFileName();
                fileName = JOptionPane.showInputDialog(frame, "Enter author of the drawing:", "Specify author", JOptionPane.PLAIN_MESSAGE, null, null, fileName).toString();
                FileHandler.saveToXML(drawingArea.getDrawing(),fileName);
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Load...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileName = JOptionPane.showInputDialog(frame, "Load drawing from:", "Specify file name", JOptionPane.PLAIN_MESSAGE);
                Drawing drawing = FileHandler.loadFromXML(fileName);
                drawingArea.setDrawing(drawing);
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Info");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Drawing drawing = drawingArea.getDrawing();
                String message = getDrawingFileName();
                message.replace(".xml","");
                String numberOfShapes = "Number of shapes: " + Integer.toString(drawing.getSize());
                String totalArea = "Total area: " + Double.toString(drawing.getTotalArea());
                String totalCircumference = "Total circumference: " + Double.toString(drawing.getTotalCircumference());
                message += "\n" + numberOfShapes + "\n" + totalArea + "\n" + totalCircumference;
                JOptionPane.showMessageDialog(frame, message, "Drawing information", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Exit");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(menuItem);

        // Add the Edit menu
        menu = new JMenu("Edit");

        // Shortcut Alt + E
        menu.setMnemonic(KeyEvent.VK_E);
        menuBar.add(menu);
        menuItem = new JMenuItem("Undo");
        menu.add(menuItem);
        menuItem = new JMenuItem("Name...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter name of the drawing:", "Specify name", JOptionPane.PLAIN_MESSAGE);
                Drawing drawing = drawingArea.getDrawing();
                drawing.setName(name);
                drawingArea.setDrawing(drawing);
                updateWindowTitle();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Author...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(frame, "Enter author of the drawing:", "Specify author", JOptionPane.PLAIN_MESSAGE);
                Drawing drawing = drawingArea.getDrawing();
                drawing.setName(name);
                drawingArea.setDrawing(drawing);
                updateWindowTitle();
            }
        });
        menu.add(menuItem);

        // Add Server menu
        menu = new JMenu("Server");
        menuBar.add(menu);
        menuItem = new JMenuItem("Load...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Boolean,Integer> sw = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        Object[] filesOnServer = c.getFilenamesFromServer();
                        String msg = "The server has " + filesOnServer.length + " drawing(s). Choose one.";
                        String filename = (String) JOptionPane.showInputDialog(frame, msg, "Select a drawing", JOptionPane.PLAIN_MESSAGE, null, filesOnServer, "File");
                        if (filename != null) {
                            String path = c.getFileFromServer(filename);
                            Drawing drawing = FileHandler.loadFromXML(path);
                            drawingArea.setDrawing(drawing);
                        }
                        return true;
                    }
                };
                sw.execute();
            }
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Save as...");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filename = getDrawingFileName();
                String saveAsFilename = JOptionPane.showInputDialog(frame, "Enter the filename to save as:", "Specify filename", JOptionPane.PLAIN_MESSAGE, null, null, filename).toString();
                String pathOfDrawing = "temp.xml";
                FileHandler.saveToXML(drawingArea.getDrawing(),pathOfDrawing);
                SwingWorker<Boolean,Integer> sw = new SwingWorker<Boolean, Integer>() {
                    @Override
                    protected Boolean doInBackground() throws Exception {
                        c.saveAsFileToServer(pathOfDrawing,saveAsFilename);
                        return true;
                    }

                    @Override
                    protected void done() {
                        try {
                            Files.delete(Paths.get(pathOfDrawing));
                        } catch (java.io.IOException ioe) {
                            System.err.println(ioe.getMessage());
                        }
                        super.done();
                    }
                };
                sw.execute();
            }
        });
        menu.add(menuItem);
    }

    /**
     * Creates a toolbar and adds it to the frame.
     */
    protected void createToolbar() {
        // Creates a JPanel with the BoxLayout
        JPanel toolBar = new JPanel();
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.LINE_AXIS));
        // Creates a JPanel with 5 columns for the colors
        JPanel colorBar = new JPanel(new GridLayout(1, 5));

        // Black color picker
        JButton button = new JButton();
        button.setBackground(Color.BLACK);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateColor(Color.BLACK);
            }
        });
        colorBar.add(button);

        //Red color picker
        button = new JButton();
        button.setBackground(Color.RED);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateColor(Color.RED);
            }
        });
        colorBar.add(button);

        // Green color picker
        button = new JButton();
        button.setBackground(Color.GREEN);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateColor(Color.GREEN);
            }
        });
        colorBar.add(button);

        // Blue color picker
        button = new JButton();
        button.setBackground(Color.BLUE);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateColor(Color.BLUE);
            }
        });
        colorBar.add(button);

        // White color picker
        button = new JButton();
        button.setBackground(Color.WHITE);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateColor(Color.WHITE);
            }
        });
        colorBar.add(button);

        // The shape picker
        String[] shapeStrings = {"Rectangle", "Circle"};
        shapeList = new JComboBox(shapeStrings);
        shapeList.setMaximumSize(shapeList.getPreferredSize());

        toolBar.add(colorBar, BorderLayout.WEST);
        toolBar.add(shapeList, BorderLayout.EAST);

        // Add panel to frame
        frame.getContentPane().add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Create the drawing area and adds it to the frame.
     */
    protected void createDrawingArea() {
        drawingArea = new DrawingPanel();
        drawingArea.setPreferredSize(new Dimension(500,500));
        drawingArea.setBackground(Color.white);
        MouseInputAdapter m = new MouseInputAdapter() {
            public void mouseMoved(MouseEvent e) {
                String text = "Coordinates: ";
                text += e.getX() + "," + e.getY();
                mousePosLabel.setText(text);
            }

            public void mousePressed(MouseEvent e) {
                drawEventStart = e.getPoint();
            }

            public void mouseReleased(MouseEvent e) {
                Shape s;
                if(shapeList.getSelectedIndex() == 0) {
                    Point topLeft = new Point();
                    Point botRight = new Point();
                    if(drawEventStart.getX() < e.getX()) {
                        topLeft.setX(drawEventStart.getX());
                        botRight.setX(e.getX());
                    } else {
                        topLeft.setX(e.getX());
                        botRight.setX(drawEventStart.getX());
                    }

                    if(drawEventStart.getY() < e.getY()) {
                        topLeft.setY(drawEventStart.getY());
                        botRight.setY(e.getY());
                    } else {
                        topLeft.setY(e.getY());
                        botRight.setY(drawEventStart.getY());
                    }
                    s = new Rectangle(topLeft,colorChoice);
                    s.addPoint(botRight);
                    s.draw(drawingArea.getGraphics());
                } else {
                    s = new Circle(drawEventStart.getX(),drawEventStart.getY(),colorChoice);
                    s.addPoint(e.getX(),e.getY());
                }
                Drawing drawing = drawingArea.getDrawing();
                drawing.addShape(s);
                drawingArea.setDrawing(drawing);

            }

            public void mouseDragged(MouseEvent e) {
                String text = "Coordinates: ";
                text += e.getX() + "," + e.getY();
                mousePosLabel.setText(text);
                if(shapeList.getSelectedIndex() == 0) {
                    Point topLeft = new Point();
                    Point botRight = new Point();
                    if(drawEventStart.getX() < e.getX()) {
                        topLeft.setX(drawEventStart.getX());
                        botRight.setX(e.getX());
                    } else {
                        topLeft.setX(e.getX());
                        botRight.setX(drawEventStart.getX());
                    }

                    if(drawEventStart.getY() < e.getY()) {
                        topLeft.setY(drawEventStart.getY());
                        botRight.setY(e.getY());
                    } else {
                        topLeft.setY(e.getY());
                        botRight.setY(drawEventStart.getY());
                    }
                    drawingArea.paintComponent(drawingArea.getGraphics());
                    Rectangle r = new Rectangle(topLeft,colorChoice);
                    r.addPoint(botRight);
                    r.draw(drawingArea.getGraphics());
                } else {
                    drawingArea.paintComponent(drawingArea.getGraphics());
                    Circle c = new Circle(drawEventStart.getX(),drawEventStart.getY(),colorChoice);
                    c.addPoint(e.getX(),e.getY());
                    c.draw(drawingArea.getGraphics());
                }
            }
        };
        drawingArea.addMouseMotionListener(m);
        drawingArea.addMouseListener(m);

        // Add panel to frame
        frame.getContentPane().add(drawingArea, BorderLayout.CENTER);
    }

    /**
     * Creates the statusbar and adds it to the frame.
     */
    protected void createStatusbar() {
        JPanel statusbar = new JPanel(new BorderLayout());

        // The position of the mouse
        mousePosLabel = new JLabel("Coordinates: ", JLabel.LEFT);
        statusbar.add(mousePosLabel, BorderLayout.WEST);

        // The chosen color
        colorLabel = new JLabel("Color: ", JLabel.LEFT);
        colorLabel.setHorizontalTextPosition(JLabel.LEFT);
        statusbar.add(colorLabel, BorderLayout.EAST);

        // Add labels to panel, and panel to frame
        frame.getContentPane().add(statusbar, BorderLayout.PAGE_END);
        frame.getContentPane().setComponentZOrder(statusbar, 0);

    }

    /**
     * Creates a string on the format "name by author.xml", reverts to "name.xml", "author.xml" or ".xml" if any data
     * is missing.
     *
     * @return a string according to format described.
     */
    public String getDrawingFileName() {
        String filename = "";
        Drawing drawing = drawingArea.getDrawing();
        if (drawing.getName() != "") {
            filename += drawing.getName();
            if (drawing.getAuthor() != "") {
                filename += " by " + drawing.getAuthor();
            }
        } else if (drawing.getAuthor() != "") {
            filename += drawing.getAuthor();
        }
        filename += ".xml";
        return filename;
    }
}
