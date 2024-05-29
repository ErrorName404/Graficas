public class Data {
    private int id=0;
    private float januarySales = 0;
    private float februarySales = 0;
    private float marchSales = 0;
    private float aprilSales = 0;
    private float maySales = 0;
    private float juneSales = 0;

    public Data(int id,float januarySales,float februarySales,float marchSales,float aprilSales,float maySales, float juneSales ){
        this.id=id;
        this.januarySales=januarySales;
        this.februarySales=februarySales;
        this.marchSales=marchSales;
        this.aprilSales=aprilSales;
        this.maySales=maySales;
        this.juneSales=juneSales;
    }

    public Data request(){
        Input input = new Input();
        Data data=new Data(false,0,"",-0.1f,true);
        Files file= new Files();
        do {
            data=new Data(false,0,"",-0.1f,true);
            data.setID(input.readInt("Please, input the item ID")) ;
            if(data.getID()!=0) {
                data=file.find(data);
                if(!(data.getisActive())){
                    System.out.println("\n---------------------------\n" +
                                        "| Error! Inactive Entry |" +
                                        "\n--------------------------\n");
                }
                else if (data.getExists() == true) {
                    System.out.println("\n---------------------------------------------\n" +
                            "| Error! This ID already exists, try again |" +
                            "\n---------------------------------------------\n");
                }
            }
            else{
                System.out.println("\n------------------------------------------------------\n" +
                        "| Error! ID number 0 is reserved, try a different one |" +
                        "\n------------------------------------------------------\n");
            }
        } while (data.getExists()||data.getID()==0||!(data.getisActive()));

        do{
            data.setName(input.readString("Please, input the name of the item"));
            if(data.getName().isEmpty()){
                System.out.println("\n------------------------------------------------------------\n" +
                        "| The name can't be empty, try a different one |" +
                        "\n------------------------------------------------------------\n");
            }
        }while(data.getName().isEmpty());

        do{
            data.setPrice(input.readFloat("Please, input the price of the item"));
            if(data.getPrice()<0){
                System.out.println("\n------------------------------------------------------------\n" +
                        "| The price can't be a negative number, try a different one |" +
                        "\n------------------------------------------------------------\n");
            }
        }while(data.getPrice()<0);
        data.setisActive(true);
        return data;
    }

    public Data update(Data data){
        Input input = new Input();
        Files file= new Files();
        Error error =new Error();
        int menuOption,subMenuOption;
        do{
            do{
                file.printData(data);
                subMenuOption=input.readInt("Please, choose which field you want to edit\n1 - Name\t\t2 - Price") ;
                if (subMenuOption<1||subMenuOption>2) {
                    error.print("Error! Invalid Option");
                }
                switch(subMenuOption){
                    case 1 ->{
                        do{
                            data.setName(input.readString("Please, input the new name of the item"));
                            if(data.getName().isEmpty()){
                                error.print("Error! Invalid Option");
                            }
                        }while(data.getName().isEmpty());
                    }
                    case 2->{
                        do{
                            data.setPrice(input.readFloat("Please, input the price of the item"));
                            if(data.getPrice()<0){
                                error.print("The price can't be a negative number, try a different one");
                            }
                        }while(data.getPrice()<0);
                    }
                    default -> error.print("ERROR, NOT AN OPTION!");
                }
                file.printData(data);
            }while (subMenuOption<1||subMenuOption>2);
                menuOption = input.readInt("\nWould you like to keep editing this entry?\n1 - YES\t\t2 - NO");
                if(menuOption<1||menuOption>2){
                    error.print("ERROR, NOT AN OPTION!");
                }
            }while(menuOption!=2);
        System.out.println("");
        return data;
    }

    public void deactivate(Data data){
        Error error= new Error();
        Input input = new Input();
        Files file= new Files();
        if(file.printAllActive()){
            data=data.typeIdentification(input.readString("Please, input the value you want to deactivate"));
            data=file.find(data);
            if(data.getExists() && !(data.getisActive())){
                error.print("already deactivated entry!");
            }
            else if (data.getExists() && data.getisActive()) {
                file.printData(data);
                data.setisActive(false);
                data = file.edit(data);
                error.print("Entry Deactivated!");
            }
        }
    }

    public void delete(Data data){
        Input input = new Input();
        Files file= new Files();
        Error error= new Error();
        if(file.printAllDeactivated()){
            data=data.typeIdentification(input.readString("Please, input the value you want to delete"));
            data=file.find(data);
            if(data.getExists() && data.getisActive()){
                error.print("The entry needs to be deactivated first!");
            }
            else if (data.getExists() && !(data.getisActive())) {
                file.printDataDeactivated(data);
                file.deleteEntry(data);
                error.print("Entry Deleted!");
            }
            else{
                error.print("Value not found!");
            }
        }
    }

    public Data typeIdentification(String text){
        Data data=new Data(false,0,"",0.0f,true);
        if(isInteger(text)){
            data.setID(Integer.parseInt(text));
        }
        else if(isFloat(text)){
            data.setPrice(Float.parseFloat(text));
        }
        else {
            data.setName(text);
        }
        return data;
    }

    public boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isFloat(String text) {
        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean getExists(){ return exists; }
    public int getID(){ return id; }
    public String getName(){ return name; }
    public float getPrice(){ return price; }
    public boolean getisActive(){ return isActive; }
    public void setExists(boolean newExists){ this.exists=newExists; }
    public void setID(int newID){ this.id=newID; }
    public void setName(String newName){
        this.name=newName;
    }
    public void setPrice(float newPrice){
        this.price=newPrice;
    }
    public void setisActive(boolean newisActive){
        this.isActive=newisActive;
    }
}
