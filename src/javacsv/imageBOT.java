/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javacsv;

import java.awt.Color;
import java.awt.Font;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.nio.file.*;
import java.util.zip.*;

import javacsv.util.getThaiBaht;
import org.w3c.dom.Element;

import javax.imageio.IIOImage;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;

import com.multiconn.fop.codec.TIFFEncodeParam;
import com.multiconn.fop.codec.TIFFField;
import com.multiconn.fop.codec.TIFFImageDecoder;
import com.multiconn.fop.codec.TIFFImageEncoder;
import java.awt.image.IndexColorModel;




/**
 *
 * @author krissada.r
 */
public class imageBOT {
    
    static final String DIR =   System.getProperty("user.dir"); //"C:\\temp\\";
    
    
    public static String genImage(chequeInfo cheque,String Proj) throws IOException {
        
        FileOutputStream gfFos = new FileOutputStream(DIR+"\\"+cheque.sUID+"_GF.jpeg");
        FileOutputStream bwfFos = new FileOutputStream(DIR+"\\"+cheque.sUID+"_BWF.tif");
        FileOutputStream bwbFos = new FileOutputStream(DIR+"\\"+cheque.sUID+"_BWB.tif");
        
        
        File StampImageFile = new File(DIR+"\\BWB.jpg");
        File GFBackground = new File(DIR+"\\GF.jpeg");
        
        //System.out.println(StampImageFile);
        //System.out.println(GFBackground);
        
        Color MICRband = new Color(230, 230, 230); // MICRBank  Color
        
        byte[] map = new byte[]{(byte) (255), (byte) (0)};
        IndexColorModel bwColorModel = new IndexColorModel (1, 2, map, map, map);
        
        BufferedImage STAMPImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_BYTE_BINARY);
        BufferedImage GFBGImage = new BufferedImage(700, 350, BufferedImage.TYPE_BYTE_GRAY);
        
        BufferedImage GFBufferedImage = new BufferedImage(700, 350, BufferedImage.TYPE_BYTE_GRAY);
        BufferedImage BWFBufferedImage = new BufferedImage(1400,700, BufferedImage.TYPE_BYTE_BINARY,bwColorModel);
        BufferedImage BWBBufferedImage = new BufferedImage(1400,700, BufferedImage.TYPE_BYTE_BINARY,bwColorModel);

        
        STAMPImage = ImageIO.read(StampImageFile);
        GFBGImage = ImageIO.read(GFBackground);
        
        Graphics2D GFGraphics = GFBufferedImage.createGraphics();
        GFGraphics.drawImage(GFBGImage, 0 , 0 , 700, 350,null);
        
        //GFGraphics.setBackground(MICRband);
        //GFGraphics.clearRect(0, 290, 700, 150);
        
        setChequeInfoGF(GFGraphics,cheque);
        setMICR(GFGraphics,cheque);
        
        GFGraphics.setColor(Color.BLACK);
        GFGraphics.setFont(new Font("Cordia New", Font.BOLD, 30));
        GFGraphics.drawString(Proj, 300, 50);
        
        writeJpegCompressedImage(GFBufferedImage,gfFos);
        
        Graphics2D BWFGraphics = BWFBufferedImage.createGraphics();
        BWFGraphics.drawImage(GFBufferedImage, 0, 0, 1400, 700, null);

        BWFGraphics.dispose();
        BWFGraphics.setComposite(AlphaComposite.Src);
        BWFGraphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        BWFGraphics.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        BWFGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);


        
        saveTiff (bwfFos, BWFBufferedImage, TIFFEncodeParam.COMPRESSION_GROUP4);
        
        Graphics2D BWBGraphics = BWBBufferedImage.createGraphics();
        //BWBGraphics.drawImage(BWBBGImage, 0, 0, 1400, 700, null);
        BWBGraphics.setBackground(Color.WHITE);
        BWBGraphics.clearRect(0, 0, 1400, 700);
        BWBGraphics.drawImage(STAMPImage, 50 , 50 , 350, 350,null);
        BWBGraphics.setColor(Color.BLACK);
        BWBGraphics.setFont(new Font("Times New Roman", Font.BOLD, 40));
        BWBGraphics.drawString(cheque.sUID, 530, 350);
        

        saveTiff (bwbFos, BWBBufferedImage, TIFFEncodeParam.COMPRESSION_GROUP4);
        
        return zipImage(cheque.sUID);
        
        
    }
    
    public static void writeJpegCompressedImage(BufferedImage image,FileOutputStream  fos) throws IOException {
        
        try {
            
            final ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(fos);
            writer.setOutput(ios);
            
            
            //Modify DPI
        
            IIOMetadata imageMetaData = writer.getDefaultImageMetadata(new ImageTypeSpecifier(image), null);
            Element tree = (Element) imageMetaData.getAsTree("javax_imageio_jpeg_image_1.0");
            Element jfif = (Element)tree.getElementsByTagName("app0JFIF").item(0);
            jfif.setAttribute("Xdensity", Integer.toString(100));
            jfif.setAttribute("Ydensity", Integer.toString(100));
            jfif.setAttribute("resUnits", "1");
            imageMetaData.setFromTree("javax_imageio_jpeg_image_1.0", tree);
        
            JPEGImageWriteParam params = new JPEGImageWriteParam(null);
            params.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            params.setCompressionQuality(0.9f);
        
            
            writer.write(imageMetaData, new IIOImage(image, null, imageMetaData), params);
            ios.close();
            writer.dispose();
            fos.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        

  }
    
    
    public static void setChequeInfoGF (Graphics2D graphic,chequeInfo cheque){
        graphic.setColor(Color.BLACK);
        graphic.setFont(new Font("Cordia New", Font.BOLD, 30));
        graphic.drawString("--"+cheque.sAmount.trim()+"--", 450, 165);
        graphic.setFont(new Font("Cordia New", Font.PLAIN, 20));
        graphic.drawString(cheque.sChequeDate, 480, 33);
        
        String ThaiBaht = new getThaiBaht().getText(cheque.sAmount);
        graphic.setFont(new Font("Cordia New", Font.PLAIN, 22));
        graphic.drawString("-"+ThaiBaht+"-", 128, 130);
        graphic.setFont(new Font("Cordia New", Font.BOLD, 24));
        graphic.drawString(cheque.sRemark, 50, 200);
        
        graphic.setFont(new Font("Cordia New", Font.BOLD, 28));
        graphic.drawString(cheque.sVerI, 400, 240);
        graphic.drawString(cheque.sVerII, 540, 240);

    }
    
    public static void setMICR (Graphics2D graphic,chequeInfo cheque){
        String MICR;
        MICR = "A"+cheque.sCRC+" C"+cheque.sChequeNo+"C"+cheque.sBankNo+"D"+cheque.sBranchNo+"A "+cheque.sAccountNo+"C"+cheque.sDocType;//+"B"+"cheque.sAmountB";
        graphic.setColor(Color.BLACK);
        graphic.setFont(new Font("MICR Encoding", Font.BOLD, 24));
        graphic.drawString(MICR, 30, 325);
    }
    
    
    private static String zipImage (String UIC)  {
        
        String zipFileName = DIR+"\\"+UIC.concat(".zip");
        
        try {
            String[] ImageFiles={
                    DIR+"\\"+UIC.concat("_BWF.tif"),
                    DIR+"\\"+UIC.concat("_BWB.tif"),
                    DIR+"\\"+UIC.concat("_GF.jpeg")};
            
            
            
            FileOutputStream fos = new FileOutputStream(zipFileName);
            ZipOutputStream zos = new ZipOutputStream(fos);
            
            for(int i = 0; i<ImageFiles.length;i++) {
                String filePath = ImageFiles[i];
                File file = new File(filePath);  
                zos.putNextEntry(new ZipEntry(file.getName()));

                byte[] bytes = Files.readAllBytes(Paths.get(filePath));
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
                
                
                
            }
        
            zos.close();
            
            
            for(int i = 0; i<ImageFiles.length;i++) {
                String filePath = ImageFiles[i];
                Files.delete(Paths.get(filePath));
            }

            
        
        }
        catch (IOException ex) {
            System.err.println("I/O error: " + ex);
        }

        
        return zipFileName;
        
    }
    
        private static void saveTiff (FileOutputStream file, BufferedImage bufferedImage, int compression)  {
                /*
                WritableRaster writableRaster = bufferedImage.getRaster ();
                
                for (int x = 0; x < 1400; x++) {
			for (int y = 0; y < 700; y++) {
				int[] rgb = getRGB (x, y);
				int avgColor = (rgb[0] + rgb[1] + rgb[2]) / 3;
				int[] indexedColor = new int[1];
				if (avgColor >= 153) {
					indexedColor[0] = 0;		// white
				} else {
					indexedColor[0] = 1;		// black
				}
				writableRaster.setPixel (x, y, indexedColor);
			}
		}
            */
            
		TIFFEncodeParam tiffEncodeParam = new TIFFEncodeParam ();
		tiffEncodeParam.setCompression (compression);
		tiffEncodeParam.setWriteTiled (false);
                
                long[][] xResolution = {{200, 1}};
                long[][] yResolution = {{200, 1}};
                char[] resolutionUnit = {2};

		tiffEncodeParam.setTileSize (0, 700);

		TIFFField[] extraFields = new TIFFField[3];
		extraFields[0] = new TIFFField (TIFFImageDecoder.TIFF_X_RESOLUTION, TIFFField.TIFF_RATIONAL, 1, xResolution);
		extraFields[1] = new TIFFField (TIFFImageDecoder.TIFF_Y_RESOLUTION, TIFFField.TIFF_RATIONAL, 1, yResolution);
		extraFields[2] = new TIFFField (TIFFImageDecoder.TIFF_RESOLUTION_UNIT, TIFFField.TIFF_SHORT, 1, resolutionUnit);
		tiffEncodeParam.setExtraFields (extraFields);

		TIFFImageEncoder tiffImageEncoder = null;
                
                
                tiffImageEncoder = new TIFFImageEncoder (file, tiffEncodeParam);
		try {
			tiffImageEncoder.encode (bufferedImage);
                        file.close();
                        
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}
    
        public static int[] getRGB (int x, int y) {
                int[] pixels;
                long pixelsSize = 1400 * 700 * 3;
                pixels = new int[(int) pixelsSize];
		int[] rgb = new int[3];
		System.arraycopy (pixels, (x * 700 + y) * 3, rgb, 0, 3);
		return rgb;
	}

    
}
