package data;

public class Defect {
    private String id;
    private Component component;
    
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
}
