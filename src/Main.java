public class Main {
    public static void main(String[] args) {
        Files file = new Files();
        Input input = new Input();
        Error error = new Error();

        int mainMenuOption;
        int subMenuOption;
        String stringToFind;
        String menuText= "Please, choose an option\n[1]- ADD EMPLOYEE\n[2]- BAR CHART\n[3]- POINT CHART\n[4]- PIE CHART\n[9]- EXIT";

        Data data=new Data(false,0,"",-0.1f,true);
        file.inicialize();

        do {
            data=new Data(false,0,"",-0.1f,true);
            mainMenuOption = input.readInt(menuText);
            switch (mainMenuOption) {
                case 1 ->{
                    do{
                        data=data.request();
                        file.write(data,"Data.dat");
                        subMenuOption = input.readInt("\nWould you like to add one more entry?\n1 - YES\t\t2 - NO");
                        if(subMenuOption<1||subMenuOption>5){
                            error.print("ERROR, NOT AN OPTION!");
                        }
                    }while(subMenuOption!=2);
                    System.out.println("\n");
                }
                case 2 -> {
                    if(!(file.checkEmpty("Data.dat"))){
                        data=data.typeIdentification(input.readString("Please, input the value you want to find"));
                        data=file.find(data);
                        file.printData(data);
                    }
                    else{
                        error.print("Error! empty file");
                    }
                }
                case 3 -> {
                    if(file.checkEmpty("Data.dat")){
                        error.print("Error! empty file");
                    }
                    else file.printAllActive();
                }
                case 4 -> {
                    data=data.typeIdentification(input.readString("Please, input the value you want to edit"));
                    file.find(data);
                    if (data.getExists() == true) {
                        data=file.edit(data);
                    }
                    else {
                        error.print("Value not found");
                    }
                }
                case 5 -> {
                    data.deactivate(data);
                }
                case 6->{
                    file.toHistorical();
                }
                case 7->{
                    file.printHistorical();
                }
                case 8->{
                    data.delete(data);
                }
                case 9->{}

                default -> error.print("ERROR, NOT AN OPTION!");
            }
        } while (mainMenuOption != 9);
        input.close();
    }
}

