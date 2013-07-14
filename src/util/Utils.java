package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import data.Component;
import data.Defect;

public class Utils {
    
    private final String VM_SHEET_NAME = "Details-VM";
    private final String JCL_SHEET_NAME = "Details-JCL";
    private final String JIT_SHEET_NAME = "Details-JIT";
    
    private ArrayList<Defect> loadRawData (String rawDataFileName) throws IOException{
        ArrayList<Defect> result = new ArrayList<Defect>();
        File f = new File(rawDataFileName);
        BufferedReader br = new BufferedReader(new FileReader(f));
        
        String line;
        while((line=br.readLine()) != null){
            String id  = extractID(line);
            String component = extractComponent(line);
            result.add(new Defect(id, component));
        } 
        br.close();
        return result;
    }

    private String extractComponent(String line) {
        final String FOUR_SPACES="    ";
        int fourSpacesIndex = line.indexOf(FOUR_SPACES);
        String lineRemovedID = line.substring(fourSpacesIndex+4);
        int firstSpaceIndex = lineRemovedID.indexOf(" ");
        String component = lineRemovedID.substring(0,firstSpaceIndex);
        return component;
    }

    private String extractID(String line) {
        return line.substring(0, 5);
    }
    
    public ArrayList<Defect> vmDefectList(String rawDataFileName) throws IOException{
        return filterDefects(this.loadRawData(rawDataFileName), Component.VM);
    }
    
    public ArrayList<Defect> jclDefectList(String rawDataFileName) throws IOException{
        return filterDefects(this.loadRawData(rawDataFileName), Component.JCL);
    }
    
    public ArrayList<Defect> jitDefectList(String rawDataFileName) throws IOException{
        return filterDefects(this.loadRawData(rawDataFileName), Component.JIT);
    }

    private ArrayList<Defect> filterDefects(ArrayList<Defect> fullList, Component comp) {
        ArrayList<Defect> result = new ArrayList<Defect>();
        for(Defect d: fullList){
            if(d.getComponent().equals(comp)){
                result.add(d);
            }
        }
        return result;
    }
    
    public ArrayList<Defect> loadRecordedVMData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName, VM_SHEET_NAME, Component.VM);
    }
    
    public ArrayList<Defect> loadRecordedJCLData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName,JCL_SHEET_NAME, Component.JCL);
    }
    
    public ArrayList<Defect> loadRecordedJITData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName, JIT_SHEET_NAME, Component.JIT);
    }

    private ArrayList<Defect> loadRecordedData(String excelFileName, String sheetName, Component comp)
            throws FileNotFoundException, IOException, InvalidFormatException {
        ArrayList<Defect> result = new ArrayList<Defect>();
        InputStream input = new FileInputStream(excelFileName);
        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheet(sheetName);
        final int numOfRows = sheet.getPhysicalNumberOfRows();
        //Iterate on rows, first row is title, 0-based
        for(int i = 1; i < numOfRows; i++){
            Row row = sheet.getRow(i);
            Cell idCell = row.getCell(0);
            //ToDo
            //By default, the ID column is Numeric, need to determine later if needs to change it
            //to String type column
            String id = Double.toString((idCell.getNumericCellValue()));
            id = id.substring(0, id.indexOf("."));
            Defect d = new Defect(id,comp);
            result.add(d);
        }
        return result;
    }
}
