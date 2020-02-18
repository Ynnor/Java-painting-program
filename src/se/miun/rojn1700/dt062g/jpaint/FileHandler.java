package se.miun.rojn1700.dt062g.jpaint;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * <h1>FileHandler</h1>
 * This file contains the class FileHandler, which handles IO of XML-files using JAXB
 * <p>
 * Giving proper comments in your program makes it more
 * user friendly and it is assumed as a high quality code.
 *
 *
 * @author  Robin Jönsson(rojn1700)
 * @version 1.0
 * @since   2018-12-07
 */

/**
 * The class FileHandler
 */
public class FileHandler {
    /**
     * Saves a drawing to an xml-file. Automatically ends file extension if filename does not already have it.
     *
     * @param drawing the drawing to be saved.
     * @param fileName the file name of the generated xml file.
     */
    static public void saveToXML(Drawing drawing, String fileName) {
        if(!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Drawing.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(drawing,new File(fileName));
        } catch (javax.xml.bind.JAXBException e){
            System.out.print(e.getMessage());
        };
    }

    /**
     * Saves a drawing to an xml-file. Generates a file name with the format "name_by_author.xml"
     *
     * @param drawing the drawing to be saved.
     */
    static public void saveToXML(Drawing drawing) {
        String fileName = drawing.getName() + "_by_" + drawing.getAuthor() + ".xml";
        saveToXML(drawing,fileName);

    }

    /**
     * Loads a drawing from an xml file.
     *
     * @param fileName the file name of the xml file with the drawing
     * @return the drawing represented in the file.
     */
    static public Drawing loadFromXML(String fileName) {
        if(!fileName.endsWith(".xml")) {
            fileName += ".xml";
        }
        try {
            JAXBContext context = JAXBContext.newInstance(Drawing.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Drawing d = (Drawing) unmarshaller.unmarshal(new File(fileName));
            return d;
        } catch (javax.xml.bind.JAXBException e) {
            System.out.print(e.getMessage());
            return new Drawing();
        } catch (java.lang.IllegalArgumentException e) {
            System.err.println("File not found.");
            return new Drawing();
        }
    }
}
