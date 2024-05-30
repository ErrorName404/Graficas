public class Employees {
    private int id=0;
    private float januarySales = 0;
    private float februarySales = 0;
    private float marchSales = 0;
    private float aprilSales = 0;
    private float maySales = 0;
    private float juneSales = 0;
    private int branch=0;

    public Employees(int id, float januarySales, float februarySales, float marchSales, float aprilSales, float maySales, float juneSales, int branch){
        this.id=id;
        this.januarySales=januarySales;
        this.februarySales=februarySales;
        this.marchSales=marchSales;
        this.aprilSales=aprilSales;
        this.maySales=maySales;
        this.juneSales=juneSales;
        this.branch=branch;
    }

    public Employees requestID(Employees employee){
        Input input = new Input();
        Error error= new Error();
        do {
            setID(input.readInt("Please, input the employee ID"));
            if(getID()==0) {
                error.print("Error! ID number 0 is reserved, try a different one");
            }
            if(getID()<0) {
                error.print("Error! ID cannot be a negative number, try a different one");
            }
        } while (getID()==0 || getID()<0);
        return employee;
    }
    public Employees requestSales(Employees employee,String month){
        Input input= new Input();
        Error error= new Error();
        switch (month) {
            case "all" -> {
                do {
                    employee.setJanuarySales(input.readFloat("Please, input the total sales of january"));
                    if (employee.getJanuarySales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getJanuarySales() < 0);

                do {
                    employee.setFebruarySales(input.readFloat("Please, input the total sales of february"));
                    if (employee.getFebruarySales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getFebruarySales() < 0);

                do {
                    employee.setMarchSales(input.readFloat("Please, input the total sales of march"));
                    if (employee.getMarchSales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getMarchSales() < 0);

                do {
                    employee.setAprilSales(input.readFloat("Please, input the total sales of april"));
                    if (employee.getAprilSales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getAprilSales() < 0);

                do {
                    employee.setMaySales(input.readFloat("Please, input the total sales of may"));
                    if (employee.getMaySales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getMaySales() < 0);

                do {
                    employee.setJuneSales(input.readFloat("Please, input the total sales of june"));
                    if (employee.getJuneSales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getJuneSales() < 0);
            }

            case "january" -> {
                do {
                    employee.setJanuarySales(input.readFloat("Please, input the total sales of january"));
                    if (employee.getJanuarySales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getFebruarySales() < 0);
            }

            case "february" -> {
                do {
                    employee.setFebruarySales(input.readFloat("Please, input the total sales of february"));
                    if (employee.getFebruarySales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getFebruarySales() < 0);
            }

            case "march" -> {
                do {
                    employee.setMarchSales(input.readFloat("Please, input the total sales of march"));
                    if (employee.getMarchSales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                } while (employee.getMarchSales() < 0);
            }

            case "april" -> {
                do {
                    employee.setAprilSales(input.readFloat("Please, input the total sales of april"));
                    if (employee.getAprilSales() < 0) {
                        error.print("The sales can't be a negative number, try a different one");
                    }
                }while (employee.getAprilSales() < 0) ;
            }

            case "may" -> {
                do{
                    employee.setMaySales(input.readFloat("Please, input the total sales of may"));
                    if(employee.getMaySales()<0){
                        error.print("The sales can't be a negative number, try a different one");
                    }
                }while(employee.getMaySales()<0);
            }
            case "june" -> {
                do{
                    employee.setJuneSales(input.readFloat("Please, input the total sales of june"));
                    if(employee.getJuneSales()<0){
                        error.print("The sales can't be a negative number, try a different one");
                    }
                }while(employee.getJuneSales()<0);
            }
            default -> error.print("Invalid month!");
        }
        return employee;
    }

    public Employees requestBranch(Employees employee){
        Input input = new Input();
        Error error= new Error();
        do {
            setBranch(input.readInt("Please, input the branch of the employee\n[1-North][2-South]"));
            if(getBranch()<1 || getBranch()>2) {
                error.print("Error! Invalid option. Try again");
            }
        } while (getBranch()<1 || getBranch()>2);
        return employee;
    }


    public int getID(){ return id; }
    public float getJanuarySales(){ return januarySales; }
    public float getFebruarySales(){ return februarySales; }
    public float getMarchSales(){ return marchSales; }
    public float getAprilSales(){ return aprilSales; }
    public float getMaySales(){ return maySales; }
    public float getJuneSales(){ return juneSales; }
    public int getBranch(){ return branch; }

    public float getTotal(){
        return (januarySales+februarySales+marchSales+aprilSales+maySales+juneSales);
    }

    public void setID(int newID){ 
        this.id=newID; 
    }
    public void setJanuarySales(float newJanuarySales){
        this.januarySales=newJanuarySales;
    }
    public void setFebruarySales(float newFebruarySales){
        this.februarySales=newFebruarySales;
    }
    public void setMarchSales(float newMarchSales){
        this.marchSales=newMarchSales;
    }
    public void setAprilSales(float newAprilSales){
        this.aprilSales=newAprilSales;
    }
    public void setMaySales(float newMaySales){
        this.maySales=newMaySales;
    }
    public void setJuneSales(float newJuneSales){
        this.juneSales=newJuneSales;
    }
    public void setBranch(int newBranch){
        this.branch=newBranch;
    }

}
