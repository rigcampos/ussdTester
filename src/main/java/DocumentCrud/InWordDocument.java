/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DocumentCrud;

import JViews.ViewManager;
import automatizacionweb.Manager;
import automatizacionweb.ProgramConstants;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author rigcampos
 */
public class InWordDocument {
    
    private String testName;

    public InWordDocument(String testName) {
        this.testName = testName;
    }
    
    public void updateWordDoc(ArrayList<String> list,int w, int h, String path){
      
      try{
          
            FileInputStream plantilla = new FileInputStream(path);
            XWPFDocument document = new XWPFDocument(plantilla);
            FileOutputStream out = new FileOutputStream(testName + ProgramConstants.DOCRESULTEXT);
            addDate(Manager.getInstance().getStartDate(), document, ProgramConstants.DOCFECHAINICO);
            for(String st: list){
                if(st.contains(ProgramConstants.DOCIMAGENAME)){
                    addImage(st,document,w,h);
                }else{
                    addCptitle(st,document);
                }
            }
            addDate(Manager.getInstance().getNowDate(), document, ProgramConstants.DOCFECHAFIN);
            document.write(out);
            document.close();
            out.close();
            
        }catch(IOException s){
            ViewManager.getInstance().updateErrorMessage("Error en el archivo de word");
        }  
    }
    
    public void updateWordDocText(String st, String path){
      
      try{
          
            FileInputStream plantilla = new FileInputStream(path);
            XWPFDocument document = new XWPFDocument(plantilla);
            FileOutputStream out = new FileOutputStream(testName + ProgramConstants.DOCRESULTEXT);
            addDate(Manager.getInstance().getStartDate(), document, ProgramConstants.DOCFECHAINICO);
            addCptitle(st,document);
            addDate(Manager.getInstance().getNowDate(), document, ProgramConstants.DOCFECHAFIN);
            document.write(out);
            document.close();
            out.close();
        }catch(IOException s){
          
        }  
    }
    
    public void addCptitle(String cp, XWPFDocument document){
        XWPFParagraph p = document.createParagraph();
        XWPFRun r = p.createRun();
        r.setText(cp);
        r.addBreak();
    }
    
    public void addImage(String image, XWPFDocument document, int w, int h) throws IOException{
        try (FileInputStream fis = new FileInputStream(image)) {
            XWPFParagraph p = document.createParagraph();
            XWPFRun r = p.createRun();
            r.addPicture(fis,
                    Document.PICTURE_TYPE_PNG,
                    image,
                    Units.toEMU(w),//152
                    Units.toEMU(h));//228
            r.addBreak();
        }catch(InvalidFormatException ife){
            System.out.println(ife);
        }
    }
    
    public void addDate(String date,XWPFDocument document, String target){
        
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(target)) {
                        text = text.replace(target, target + ": " + date);
                        r.setText(text, 0);
                    }
                }
            }
        }
    }
    
}
