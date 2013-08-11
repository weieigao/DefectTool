package data;

import java.util.Calendar;
import java.util.Date;

public class Defect {
    private String id;
    private Component component;
    private Date startDate;
    public Date getStartDate() {
        return startDate;
    }

    private Date endDate;
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Defect(String id, String component){
        this.id = id;
        if(component.equals("classes")){
           this.component = Component.JCL;
        } else if(component.contains("Tr")){
            this.component = Component.JIT;
        } else {
            this.component = Component.VM;
        }
    }
    
    public Defect(String id, Component comp){
        this.id = id;
        this.component = comp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
    
    public boolean isNew(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -7);
        if(startDate.after(c.getTime())){
            return true;
        }
        return false;
    }
    
    public boolean isClosed(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -7);
        if(endDate.after(c.getTime())){
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object o){
        if(((Defect)o).id.equals(this.id)){
            return true;
        }
        return false;
    }
    
}
