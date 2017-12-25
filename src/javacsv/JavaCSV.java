/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacsv;

import javacsv.util.HashGeneratorUtils;
import javacsv.util.HashGenerationException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javafx.stage.FileChooser;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;

import javafx.scene.layout.GridPane;

import javafx.scene.control.*;
import javafx.scene.layout.*;

import javafx.scene.control.cell.*;

import javafx.stage.DirectoryChooser;
import javafx.event.ActionEvent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.geometry.Insets; 
import javafx.geometry.Pos;

import java.io.File;
import java.time.LocalDate;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.event.EventHandler;

import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import static javacsv.imageBOT.DIR;

import static javacsv.util.padding.leftZeroPadding;


/**
 *
 * @author krissada.r
 */
public class JavaCSV extends Application {
    
    private final TableView<Record> tableView = new TableView<>();
 
    private final ObservableList<Record> dataList = FXCollections.observableArrayList();

    private static int totItem;
    private static double totAmount;
    //private final static ArrayList<chequeInfo> cheque = new ArrayList<chequeInfo>();
    
    private final static TextField txtProj = new TextField();
    
    public configure confi = new configure();
    public ArrayList<chequeInfo> chequeDetail = new ArrayList();
    
    //private static double totAmount;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Generate ICMG NIC Package");
 
        Group root = new Group();
 
        TableColumn columnF1 = new TableColumn("CRC");
        columnF1.setCellValueFactory(
                new PropertyValueFactory<>("f1"));
        columnF1.setMaxWidth(35);
        columnF1.getStyleClass().add("table-heading");
        //columnF1.setResizable(false);
 
        TableColumn columnF2 = new TableColumn("Cheque No.");
        columnF2.setCellValueFactory(
                new PropertyValueFactory<>("f2"));
        columnF2.setMaxWidth(75);
 
        TableColumn columnF3 = new TableColumn("Bank No.");
        columnF3.setCellValueFactory(
                new PropertyValueFactory<>("f3"));
        columnF3.setMaxWidth(60);
 
        TableColumn columnF4 = new TableColumn("Branch No.");
        columnF4.setCellValueFactory(
                new PropertyValueFactory<>("f4"));
        columnF4.setMaxWidth(70);
 
        TableColumn columnF5 = new TableColumn("Acocunt No.");
        columnF5.setCellValueFactory(
                new PropertyValueFactory<>("f5"));
        columnF5.setMaxWidth(120);
        
        TableColumn columnF6 = new TableColumn("Doc Type");
        columnF6.setCellValueFactory(
                new PropertyValueFactory<>("f6"));
        columnF6.setMaxWidth(65);
        //columnF6.setResizable(false);
 
        TableColumn columnF7 = new TableColumn("Amount");
        columnF7.setCellValueFactory(
                new PropertyValueFactory<>("f7"));
        columnF7.setMaxWidth(100);

        TableColumn columnF8 = new TableColumn("Date");
        columnF8.setCellValueFactory(
                new PropertyValueFactory<>("f8"));
        columnF8.setMaxWidth(70);
        
        TableColumn columnF9 = new TableColumn("Send Bank");
        columnF9.setCellValueFactory(
                new PropertyValueFactory<>("f9"));
        columnF9.setMaxWidth(70);

        TableColumn columnF10 = new TableColumn("Send Branch");
        columnF10.setCellValueFactory(
                new PropertyValueFactory<>("f10"));
        columnF10.setMaxWidth(80);
        
        TableColumn columnF11 = new TableColumn("PCT ");
        columnF11.setCellValueFactory(
                new PropertyValueFactory<>("f11"));
        columnF11.setMaxWidth(35);
        
        TableColumn columnF12 = new TableColumn("Verify I");
        columnF12.setCellValueFactory(
                new PropertyValueFactory<>("f12")); 
        
        TableColumn columnF13 = new TableColumn("Verify II");
        columnF13.setCellValueFactory(
                new PropertyValueFactory<>("f13")); 
        
        TableColumn columnF14 = new TableColumn("Remark");
        columnF14.setCellValueFactory(
                new PropertyValueFactory<>("f14"));
        //columnF14.setMinWidth(110);
        
        Button browsFile = new Button("Open CSV File ...");
        
        browsFile.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0) {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.csv)", "*.csv");
                fileChooser.getExtensionFilters().add(extFilter);
                File file = fileChooser.showOpenDialog(primaryStage);
                //System.out.println(file);
                tableView.getItems().clear();
                readCSV(file.toString());
            }
            
        });
        
        Label lblOutput = new Label();
        lblOutput.setStyle("-fx-font-weight: bold");
        

        
        tableView.setItems(dataList);
        tableView.getColumns().addAll(
                columnF1, columnF2, columnF3, columnF4, columnF5, columnF6, columnF7, columnF8, columnF9, columnF10, columnF11, columnF12, columnF13, columnF14);
        tableView.setMouseTransparent(true);
 
        GridPane gridPane = new GridPane(); 
        gridPane.setMinSize(500, 1800);

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        
        
        ComboBox comboBox = new ComboBox();
        comboBox.setItems(FXCollections.observableArrayList("Same day","Next day"));
        comboBox.getSelectionModel().selectFirst();
        
        //TextField uidSeq = new TextField();
        
        final Spinner<Integer> intBatchSpinner = new Spinner<>(1, 100, 0, 1);
        initSpinner(intBatchSpinner);
        intBatchSpinner.setEditable(false);
        
        final Spinner<Integer> intSeqSpinner = new Spinner<>(1, 1000, 0, 1);
        initSpinner(intSeqSpinner);
        intSeqSpinner.setEditable(false);


        //comboBox.valueProperty().addListener((obs, oldVal, newVal) ->  System.out.println("Price of the " + newVal.getName() + " is : "  +  newVal.getPrice()));    }

        
        //gridPane.setAlignment(Pos.CENTER_LEFT);
        //lblHash.setText("123");
        
        // Create the DatePicker.
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        
        
        
        datePicker.setOnAction(event -> {
            LocalDate date = datePicker.getValue();
            //System.out.println("Selected date: " + date);
        });
        

        Label lblDatePicker = new Label("Date:");
        Label lblSettlement = new Label("Settlement:");
        Label lblBatchNumber = new Label("Batch Number:");
        Label lblUIDSeq = new Label("UID Start Seq:");
        Label lblBank = new Label("Bank:");
        Label lblProjectTitle = new Label("Project Title:");
        
        TextField txtBank = new TextField();
        txtBank.setText("004");
        txtBank.setPrefWidth(20);
/*
        text.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                try {
                    Integer.parseInt(newValue);
                    if (newValue.endsWith("f") || newValue.endsWith("d")) {
                        manualPriceInput.setText(newValue.substring(0, newValue.length()-1));
                    }
                } catch (ParseException e) {
                    text.setText(oldValue);
                }
            }

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
*/
        
        Button genNICPack = new Button("Generate ICMG NIC Package ...");
        
        confi.setClearingData(datePicker.getValue().format(DateTimeFormatter.BASIC_ISO_DATE));
        confi.setBankNo(leftZeroPadding(Integer.parseInt(txtBank.getText()),3));
        confi.setBatchNo(intBatchSpinner.getValue());
        confi.setSeq(intSeqSpinner.getValue());

        
        genNICPack.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent arg0) {
                
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(primaryStage); 

                
                confi.setClearingData(datePicker.getValue().format(DateTimeFormatter.BASIC_ISO_DATE));
                confi.setBankNo(leftZeroPadding(Integer.parseInt(txtBank.getText()),3));
                confi.setBatchNo(intBatchSpinner.getValue());
                confi.setSeq(intSeqSpinner.getValue());
                
                String[] BOTImage=new String[200];
                
                String period = comboBox.getValue().toString();
                String periodChar = "S";
                
                if ("Next day".equals(period))
                     periodChar="N";
                
                String zipFileName = selectedDirectory.getAbsolutePath()+"\\ICS_"+
                                               confi.getClearingDate()+
                                               "_"+confi.getBankNo()+"_0001_"+confi.getBatchNo()+
                                               "_NI"+periodChar+".zip";
                
                String FILENAME =  selectedDirectory.getAbsolutePath()+"\\"+
                                               confi.getClearingDate()+
                                               "_"+confi.getBankNo()+"_0001_"+confi.getBatchNo()+
                                               "_NI"+periodChar+".dat";
                
                    if(selectedDirectory == null){
                        lblOutput.setText("No Directory selected");
                    }else{

                        
                        
                        BufferedWriter bw = null;
                        FileWriter fw = null;
                        
                        try {
                                    
                            fw = new FileWriter(FILENAME);
                            bw = new BufferedWriter(fw);
                            
                            icasDataPackage header = new icasDataPackage();
                            icasDataPackage detail = new icasDataPackage();
                            header.setRecordType("H");
                            header.setBatchNumber("000001");
                            header.setClearinDate(confi.getClearingDate());
                            header.setSettlementDate(confi.getClearingDate());
                            header.setCreateTime();
                            header.setTotalItem(totItem);
                            header.setTotalAmount(totAmount);
                            
                            bw.write(header.generateHeader());
                            bw.write("\r\n");
                            
                            for (int i=0; i<chequeDetail.size(); i++){
                                String BOTImageFile;
                                chequeDetail.get(i).sUID = confi.getClearingDate()+chequeDetail.get(i).sSendBank+leftZeroPadding(confi.getiBatchNo(),5)+leftZeroPadding(confi.getSeqNo(),6);
                                chequeDetail.get(i).sBranchBatch=chequeDetail.get(i).sSendBank+leftZeroPadding(confi.getiBatchNo(),3);
                                confi.IncreaseSeq();
                                //System.out.println(chequeDetail.get(i).sUID);
                                
                                chequeInfo chqdetail = new chequeInfo();
                                chqdetail = chequeDetail.get(i);
                                detail.setRecordType("D");
                                BOTImageFile=imageBOT.genImage(chqdetail,txtProj.getText());
                                
                                BOTImage[i]=BOTImageFile;
                                
                                try {
                                    File file = new File(BOTImageFile); 
                                    chequeDetail.get(i).sImageHAsh=HashGeneratorUtils.generateSHA256(file);
                                } catch (HashGenerationException ex) {
                                    ex.printStackTrace();
                                }
                                

                                bw.write(detail.generateDetail(chqdetail));
                                bw.write("\r\n");
                                
                            }
                            
                            
                            
                            confi.setBatchNo(confi.getiBatchNo()+1);
                            
                            

                            
                        } catch (IOException e){
                            e.printStackTrace();
                        } finally {
                            try {
                                if (bw != null)
                                    bw.close();
                                
                                if (fw != null)
                                    fw.close();
                                
                                FileOutputStream fos = new FileOutputStream(zipFileName);
                                ZipOutputStream zos = new ZipOutputStream(fos);
                                
                                zos.putNextEntry(new ZipEntry(new File(FILENAME).getName()));
                                byte[] bytesdata = Files.readAllBytes(Paths.get(FILENAME));
                                    zos.write(bytesdata, 0, bytesdata.length);
                                    zos.closeEntry();
                                
                                for(int i = 0; i<chequeDetail.size();i++) {
                                    String filePath = BOTImage[i];
                                    File file = new File(filePath);  
                                    zos.putNextEntry(new ZipEntry(file.getName()));

                                    byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                                    zos.write(bytes, 0, bytes.length);
                                    zos.closeEntry();
                                    
                                    Files.delete(Paths.get(filePath));
                                }
                                
                                
        
                                zos.close();
                                
                                Files.delete(Paths.get(FILENAME));
                                
                                lblOutput.setText(zipFileName);
                                
                                
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        
                    }
                    
                    intBatchSpinner.getValueFactory().setValue(confi.getiBatchNo());// confi.getSeqNo());
                    intSeqSpinner.getValueFactory().setValue(confi.getSeqNo());// confi.getSeqNo());                    
                    

                }
        });
        
        HBox hBox = new HBox(browsFile, genNICPack);
        hBox.setSpacing(10.0);
        hBox.setPadding(new Insets(5,5,5,0));
        
        VBox vBox = new VBox(lblDatePicker,datePicker,lblSettlement,comboBox,lblBank,txtBank,lblProjectTitle,txtProj,lblBatchNumber,intBatchSpinner,lblUIDSeq,intSeqSpinner);
        vBox.setSpacing(5.0);
        vBox.setPadding(new Insets(0,5,5,5));
        
        
        
        gridPane.add(hBox, 1, 0);
        gridPane.add(vBox, 0, 1);
        gridPane.add(tableView, 1, 1);
        gridPane.add(lblOutput, 1, 2);
        
        Scene scene = new Scene(gridPane);
        primaryStage.setHeight(500);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
 
        primaryStage.setScene(scene);
        //scene.getStylesheets().add("javacsv/GenNIC.css");
        scene.getStylesheets().add(getClass().getResource("GenNIC.css").toExternalForm());
        primaryStage.show();
 
        
    }

    
    
    private void readCSV(String CsvFile) {
 
        totItem = 0;
        totAmount = 0.0;
        chequeDetail.clear();
        

        String FieldDelimiter = ",";
        
        BufferedReader br;
 
        try {
            br = new BufferedReader(new FileReader(CsvFile));
 
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(FieldDelimiter, -1);
 
                Record record = new Record(fields[0], fields[1], fields[2],
                        fields[3], fields[4], fields[5], fields[6], fields[7], fields[8], fields[9], fields[10], fields[11], fields[12], fields[13]);
                dataList.add(record);
                
                
                
                chequeInfo chq = new chequeInfo();
                chq.setChequeInfo(record);
                
                
                chequeDetail.add(chq);
                
                confi.IncreaseSeq();

                
                
                //chq.sUID=clearingDate+chq.sSendBank+chq.sSendBank+leftZeroPadding(iBatchNo,2)+leftZeroPadding(seqNo,6);
                //System.out.println(chq.sUID);
                totItem +=1;
                totAmount += Double.parseDouble(fields[6]);
                //System.out.println(totAmount);
            }
            

 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaCSV.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JavaCSV.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
 
    }
    
    public static void generateNIC(String Output,String Date,Integer Batch,String Settel){
        
    }

    private static void initSpinner(Spinner<?> spinner) {
        spinner.getEditor().setAlignment(Pos.CENTER_RIGHT);
        spinner.setEditable(true);
        spinner.setPrefWidth(80);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
