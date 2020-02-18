package se.miun.rojn1700.dt062g.jpaint;

import java.lang.Exception;

/**
 * <h1>Drawables</h1>
 * This file contains the NoEndPointException class.
 * <p>
 * NoEndPointException is supposed to be thrown when a shape, which has no end point, tries to perform calculations
 * requiring an end point.
 *
 * @author Robin Jönsson(rojn1700)
 * @version 1.1
 * @since 2018 -11-16
 */
public class NoEndPointException extends Exception{
    @Override
    public String getMessage() {
        return "The shape has no end point.";
    }
}
