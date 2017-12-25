/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacsv;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author krissada.r
 */
public class ImageUtil {
    
    
    
    public static void setMICR (Graphics2D graphic,chequeInfo cheque){
        String MICR;
        MICR = "A"+cheque.sCRC+" C"+cheque.sChequeNo+"C"+cheque.sBankNo+"D"+cheque.sBranchNo+"A "+cheque.sAccountNo+"C"+cheque.sDocType;//+"B"+"cheque.sAmountB";
        graphic.setColor(Color.BLACK);
        graphic.setFont(new Font("MICR Encoding", Font.BOLD, 24));
        graphic.drawString(MICR, 30, 325);
    }
}
