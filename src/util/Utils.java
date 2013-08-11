package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import data.Component;
import data.Config;
import data.Defect;

public class Utils {
    
    private static ArrayList<Defect> loadRawData (String rawDataFileName) throws IOException{
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

    private static String extractComponent(String line) {
        final String FOUR_SPACES="    ";
        int fourSpacesIndex = line.indexOf(FOUR_SPACES);
        String lineRemovedID = line.substring(fourSpacesIndex+4);
        int firstSpaceIndex = lineRemovedID.indexOf(" ");
        String component = lineRemovedID.substring(0,firstSpaceIndex);
        return component;
    }

    private static String extractID(String line) {
        return line.substring(0, 6);
    }
    
    public static ArrayList<Defect> vmDefectList(String rawDataFileName) throws IOException{
        return filterDefects(loadRawData(rawDataFileName), Component.VM);
    }
    
    public static ArrayList<Defect> jclDefectList(String rawDataFileName) throws IOException{
        return filterDefects(loadRawData(rawDataFileName), Component.JCL);
    }
    
    public static ArrayList<Defect> jitDefectList(String rawDataFileName) throws IOException{
        return filterDefects(loadRawData(rawDataFileName), Component.JIT);
    }

    private static ArrayList<Defect> filterDefects(ArrayList<Defect> fullList, Component comp) {
        ArrayList<Defect> result = new ArrayList<Defect>();
        for(Defect d: fullList){
            if(d.getComponent().equals(comp)){
                result.add(d);
            }
        }
        return result;
    }
    
    public static ArrayList<Defect> loadRecordedVMData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName, Config.VM_SHEET_NAME, Component.VM);
    }
    
    public static ArrayList<Defect> loadRecordedJCLData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName,Config.JCL_SHEET_NAME, Component.JCL);
    }
    
    public static ArrayList<Defect> loadRecordedJITData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName, Config.JIT_SHEET_NAME, Component.JIT);
    }
    
    public static ArrayList<Defect> loadArchivedVMData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName, Config.ARCHIVED_VM_SHEET_NAME, Component.VM);
    }
    
    public static ArrayList<Defect> loadArchivedJCLData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName,Config.ARCHIVED_JCL_SHEET_NAME, Component.JCL);
    }
    
    public static ArrayList<Defect> loadArchivedJITData(String excelFileName) throws InvalidFormatException, IOException{
        return loadRecordedData(excelFileName, Config.ARCHIVED_JIT_SHEET_NAME, Component.JIT);
    }
    
    public static void writeVMNewDefectData(String excelFileName, ArrayList<Defect> newList) throws InvalidFormatException, IOException{
        writeNewDefectData(excelFileName, Config.VM_SHEET_NAME,Component.VM,newList);
    }
    
    private static void writeNewDefectData(String excelFileName, String sheetName, Component comp, ArrayList<Defect> newList) throws InvalidFormatException, IOException{
        InputStream input = new FileInputStream(excelFileName);
        Workbook wb = WorkbookFactory.create(input);
        Sheet sheet = wb.getSheet(sheetName);
        final int numOfRows = sheet.getPhysicalNumberOfRows();
        
        ArrayList<Defect> existDefectList = loadRecordedData(excelFileName, sheetName, comp);
        int addToRow = numOfRows;
        for(int i = 0; i < newList.size(); i++){
            Defect defect = newList.get(i);
            if(!existDefectList.contains(defect)){
                Row row = sheet.createRow(addToRow);
                Cell idCell = row.createCell(0);
                idCell.setCellValue(Double.parseDouble(defect.getId()));
                Cell startDateCell = row.createCell(1);
                startDateCell.setCellValue(defect.getStartDate());
                
                addToRow++;
            }
        }
        
        OutputStream output = new FileOutputStream(excelFileName);
        wb.write(output);
        
        input.close();
        output.close();
    }

    private static ArrayList<Defect> loadRecordedData(String excelFileName, String sheetName, Component comp)
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
            
            Cell startDateCell = row.getCell(1);
            Date startDate = startDateCell.getDateCellValue();
            d.setStartDate(startDate);
            
            Cell endDateCell = row.getCell(2);
            if (endDateCell != null) {
                Date endDate = endDateCell.getDateCellValue();
                d.setEndDate(endDate);
            }
            
            result.add(d);
        }
        return result;
    }
    
    
    public static void copyFile(String srcFile, String destFile) throws IOException{
        File f = new File(srcFile);
        FileInputStream fis = new FileInputStream(f);
        File output = new File(destFile);
        FileOutputStream fous = new FileOutputStream(output);
        
        byte[] buffer = new byte[1024];
        int readNum = -1;
        while((readNum =fis.read(buffer)) != -1){
            fous.write(buffer, 0, readNum);
        }
        
        fis.close();
        fous.close();
    }
}
