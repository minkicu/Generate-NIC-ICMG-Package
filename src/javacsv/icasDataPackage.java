/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacsv;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static javacsv.util.padding.leftZeroPadding;
import static javacsv.util.padding.spacePadding;
/**
 *
 * @author krissada.r
 */
public class icasDataPackage {
    private final String kindOfFile ="40";
    private static String recordType;
    private final String versionRelease = "1.0.0   ";
    private final String versionDate = "20111111";
    private final String processBank = "004";
    private final String processBranch = "0811";
    private static String batchNumber;
    private static String clearingDate;
    private static String settlementDate;
    private static String createTime;
    private static int totalItem;
    private static long totalAmount;
    
    public void setRecordType(String input) {
        recordType = "2";
        if ("H".equals(input) ) recordType = "1";
    }

    public void setBatchNumber(String input) {
        batchNumber = input;
    }

    public void setClearinDate(String input) {
        clearingDate = input;
    }    

    public void setSettlementDate(String input) {
        settlementDate = input;
    }  

    public void setCreateTime() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("HHmm");
        createTime = format.format(date);
    }
    
    public void setTotalItem(int input) {
        totalItem = input;
    }
    
    public void setTotalAmount(double input) {
        totalAmount =  ( (Double) (input*100.00) ).longValue(); // ( input. ). //  (int)(input*100);
    }
    
    public String generateHeader() {

        String header = "";
        header += kindOfFile;
        header += recordType;
        header += versionRelease;
        header += versionDate;
        header += processBank;
        header += processBranch;
        header += batchNumber;
        header += clearingDate;
        header += settlementDate;
        header += createTime;
        header += leftZeroPadding(totalItem,4);
        header += leftZeroPadding(totalAmount,15);
        return spacePadding(header,160) ;
    }
    
    public String generateDetail(chequeInfo d1) {
        
        String detail = "";
        Double amount = Double.parseDouble(d1.sAmount)*100;
        
        detail += kindOfFile;
        detail += recordType;
        detail += d1.sSendBank;
        detail += d1.sSendBranch;
        detail += d1.sBranchBatch;
        detail += d1.sUID;
        detail += d1.sCRC;
        detail += d1.sChequeNo;
        detail += "  "; //Clearing House Code
        detail += d1.sBankNo;
        detail += d1.sBranchNo;
        detail += d1.sAccountNo;
        detail += d1.sDocType;
        detail += leftZeroPadding(amount.longValue(),12);
        detail += "00";  //Return Reason
        detail += d1.sIQATag;
        detail += d1.sPCTTag;
        detail += d1.sImageHAsh;
        detail += spacePadding(" ",29);
        return detail;
    }
    
}
