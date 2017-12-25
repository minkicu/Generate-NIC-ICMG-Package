/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacsv.util;

/**
 *
 * @author krissada.r
 */
public class HashGenerationException extends Exception  {
    public HashGenerationException() {
	super();
    }
	
    public HashGenerationException(String message, Throwable throwable) {
	super(message, throwable);
    }

    public HashGenerationException(String message) {
	super(message);
    }

    public HashGenerationException(Throwable throwable) {
        super(throwable);
    }
}
