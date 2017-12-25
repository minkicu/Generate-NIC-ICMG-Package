/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacsv;

/**
 *
 * @author krissada.r
 */
public class chequeInfo {
    String sSendBank = "000";
    String sSendBranch = "0000";
    String sBranchBatch = "000000";
    String sUID = "0000000000000000000000";
    String sCRC="99";
    String sChequeNo="00000000";
    String sClearingHouse = "10100";
    String sBankNo="000";
    String sBranchNo="0000";
    String sAccountNo="0000000000";
    String sDocType="00";
    String sAmount="000000000000";
    String sReturnReason="00";
    String sIQATag="00";
    String sPCTTag="00";
    String sImageHAsh="                                            ";
    String sChequeDate = "99/99/9999";
    String sRemark="";
    String sVerI="";
    String sVerII="";
    
    public void setChequeInfo(Record d1) {
                sCRC = d1.getF1();
                sChequeNo = d1.getF2();
                sBankNo = d1.getF3();
                sBranchNo = d1.getF4();
                sAccountNo = d1.getF5();
                sDocType = d1.getF6();
                sAmount = d1.getF7();
                sChequeDate = d1.getF8();
                sSendBank = d1.getF9();
                sSendBranch = d1.getF10();
                sPCTTag = d1.getF11();
                sVerI = d1.getF12();
                sVerII = d1.getF13();
                sRemark = d1.getF14();
    }
    
    
}