package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import data.Component;
import data.Defect;

public class Analyze {

    public ArrayList<Defect> getNewDefect(Component comp, String rawDataFileName, String recordedExcelFile) {
        try {
            if (comp.equals(Component.JCL)) {
                return getNewDefect(
                        Utils.jclDefectList(rawDataFileName),
                        Utils.loadRecordedJCLData(recordedExcelFile));
            } else if (comp.equals(Component.VM)) {
                return getNewDefect(
                        Utils.vmDefectList(rawDataFileName),
                        Utils.loadRecordedVMData(recordedExcelFile));
            } else {
                return getNewDefect(
                        Utils.jitDefectList(rawDataFileName),
                        Utils.loadRecordedJITData(recordedExcelFile));
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public ArrayList<Defect> getClosedDefect(Component comp, String rawDataFileName, String recordedExcelFile) {
        try {
            if (comp.equals(Component.JCL)) {
                return getClosedDefect(
                        Utils.jclDefectList(rawDataFileName),
                        Utils.loadRecordedJCLData(recordedExcelFile),
                        Utils.loadArchivedJCLData(recordedExcelFile));
            } else if (comp.equals(Component.VM)) {
                return getClosedDefect(
                        Utils.vmDefectList(rawDataFileName),
                        Utils.loadRecordedVMData(recordedExcelFile),
                        Utils.loadArchivedVMData(recordedExcelFile));
            } else {
                return getClosedDefect(
                        Utils.jitDefectList(rawDataFileName),
                        Utils.loadRecordedJITData(recordedExcelFile),
                        Utils.loadArchivedJITData(recordedExcelFile));
            }
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private ArrayList<Defect> getClosedDefect(ArrayList<Defect> rawDataList,
            ArrayList<Defect> recordedList, ArrayList<Defect> archivedList){
        ArrayList<Defect> result = new ArrayList<Defect>();
        for(Defect d : recordedList){
            if(!rawDataList.contains(d)){
                result.add(d);
            }
        }
        
        for(Defect d: archivedList){
            if(d.isClosed()){
                result.add(d);
            }
        }
        return result;
    }

    private ArrayList<Defect> getNewDefect(ArrayList<Defect> rawDataList,
            ArrayList<Defect> recordedList) {
        
        ArrayList<Defect> result = new ArrayList<Defect>();
        for (Defect recordedDefect : recordedList) {
            if(recordedDefect.isNew()){
                result.add(recordedDefect);
            }
        }
        
        
        for(Defect rawDefect : rawDataList){
            if(!recordedList.contains(rawDefect)){
                //if no record, set the start date to today
                rawDefect.setStartDate(new Date());
                result.add(rawDefect);
            }
        }
        return result;
    }
}
