import java.io.*;

class Files{
    public void inicialize(){
        File file = new File("Data.dat");
        File historicalFile = new File("HistoricalData.dat");
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        FileOutputStream fos2 = null;
        DataOutputStream dos2 = null;

        try{
            fos = new FileOutputStream("Data.dat", true);
            dos = new DataOutputStream(fos);
            fos2 = new FileOutputStream("HistoricalData.dat", true);
            dos2 = new DataOutputStream(fos2);
        } catch (IOException e) {
            //System.out.println("Error: " + e.getMessage());
        }
        finally{
            try{
                fos.close();
                fos2.close();
            }
            catch (Exception e) {}
        }
    }

    public void write(Data data,String fileName){
        int menuOption=0;
        Input input = new Input();
        File file = new File(fileName);
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try{

            fos = new FileOutputStream(file, true);
            dos = new DataOutputStream(fos);

            dos.writeInt(data.getID());
            dos.writeUTF(data.getName());
            dos.writeFloat(data.getPrice());
            dos.writeBoolean(data.getisActive());
        } catch (IOException e) {
            //System.out.println("Error: " + e.getMessage());
        }
        finally{
            try{
                fos.close();
            }
            catch (Exception e) {}
        }
    }

    public Data find(Data data) {
        File file = new File("Data.dat");
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            int idInFile;
            String nameInFile;
            float priceInFile;
            boolean isActiveInFile;
            do {
                idInFile=dis.readInt();
                nameInFile=dis.readUTF();
                priceInFile=dis.readFloat();
                isActiveInFile=dis.readBoolean();
                if (data.getID() == idInFile||data.getPrice() == priceInFile||data.getName().toLowerCase().equalsIgnoreCase(nameInFile)){
                    data.setExists(true);
                }
            }while(!data.getExists() && dis.available() > 0);

            if(data.getExists()){
                data.setID(idInFile);
                data.setName(nameInFile);
                data.setPrice(priceInFile);
                data.setisActive(isActiveInFile);
            }
        } catch (Exception e) {
            //System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                fis.close();
            } catch (Exception f) {
                //System.out.println("Error: " + f.getMessage());
            }
        }
        return data;
    }

    public void toHistorical(){
        Data data=new Data(false,0,"",-0.1f,true);

        File file = new File("Data.dat");
        File tempFile = new File("TempData.dat");
        FileInputStream fis = null;
        DataInputStream dis = null;
        boolean anyEntryMoved=false;
        try{
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            int idInFile;
            String nameInFile;
            float priceInFile;
            boolean isActiveInFile;
            do {
                idInFile=dis.readInt();
                nameInFile=dis.readUTF();
                priceInFile=dis.readFloat();
                isActiveInFile=dis.readBoolean();
                data.setID(idInFile);
                data.setName(nameInFile);
                data.setPrice(priceInFile);
                data.setisActive(isActiveInFile);
                if(!(data.getisActive())){
                        write(data,"HistoricalData.dat");
                        anyEntryMoved=true;
                }
                else{
                    write(data,"TempData.dat");
                }
            }while(dis.available() > 0);
            if(!anyEntryMoved){
                System.out.println("There is not deactivated entries\n");
            }
            else{
                System.out.println("Deactivated entries moved\n");
            }
        } catch (IOException e) {
            //System.out.println("Error: " + e.getMessage());
        }
        finally{
            try{
                fis.close();
                file.delete();
                tempFile.renameTo(file);
                //System.out.println("Entries moved!");
            }
            catch (Exception e) {
                //System.out.println("Error: " + e.getMessage());
            }
        }
    }

    public void deleteEntry(Data data){
        Data auxData= new Data(data.getExists(),data.getID(),data.getName(),data.getPrice(),data.getisActive());
        File file = new File("Data.dat");
        File tempFile = new File("TempData.dat");
        FileInputStream fis = null;
        DataInputStream dis = null;

        try{
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            int idInFile;
            String nameInFile;
            float priceInFile;
            boolean isActiveInFile;
            do {
                idInFile=dis.readInt();
                nameInFile=dis.readUTF();
                priceInFile=dis.readFloat();
                isActiveInFile=dis.readBoolean();
                if (auxData.getID() == idInFile && auxData.getPrice() == priceInFile && auxData.getName().toLowerCase().equalsIgnoreCase(nameInFile)){
                    printData(data);
                    System.out.println("DELETED!");
                    idInFile=dis.readInt();
                    nameInFile=dis.readUTF();
                    priceInFile=dis.readFloat();
                    isActiveInFile=dis.readBoolean();

                }
                data.setID(idInFile);
                data.setName(nameInFile);
                data.setPrice(priceInFile);
                data.setisActive(isActiveInFile);
                write(data,"TempData.dat");
            }while(dis.available() > 0);

        } catch (IOException e) {
            //System.out.println("Error: " + e.getMessage());
        }
        finally{
            try{
                fis.close();
                file.delete();
                tempFile.renameTo(file);
            }
            catch (Exception e) {
                //System.out.println("Error: " + e.getMessage());
            }
        }

    }
    public void printData(Data data){
        Error error= new Error();
        if (data.getExists()) {
            if(data.getisActive()){
                System.out.printf("\n| %-10s | %-20s | %10s |%n", "ID", "Name", "Price" );
                System.out.println("-----------------------------------------------------");
                System.out.printf("| %-10d | %-20s | %10s |%n", data.getID(), data.getName(), data.getPrice());
                System.out.println("-----------------------------------------------------\n");
                }
            else{
                error.print("Inactive Entry!");
            }
        }
        else {
            error.print("Entry not found!");
        }
    }

    public void printDataDeactivated(Data data){
                System.out.printf("\n| %-10s | %-20s | %10s |%n", "ID", "Name", "Price" );
                System.out.println("-----------------------------------------------------");
                System.out.printf("| %-10d | %-20s | %10s |%n", data.getID(), data.getName(), data.getPrice());
                System.out.println("-----------------------------------------------------\n");
    }
    public void printHistorical(){
        Error error=new Error();
        File file = new File("HistoricalData.dat");
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream("HistoricalData.dat");
            dis = new DataInputStream(fis);
            if(checkEmpty("HistoricalData.dat")){
                error.print("There is no deleted data!");
            }
            else {
                System.out.printf("\n| %-10s | %-20s | %10s |%n", "ID", "Name", "Price");
                System.out.println("-----------------------------------------------------");
                while (dis.available() > 0) {
                    int id = dis.readInt();
                    String name = dis.readUTF();
                    float price = dis.readFloat();
                    boolean isActive = dis.readBoolean();
                    System.out.printf("| %-10d | %-20s | %10s |%n", id, name, price);
                }
                System.out.println("-----------------------------------------------------\n");
            }
        } catch (Exception e) {
            //System.out.println("Error: "+e);

        } finally {
            try {
                fis.close();
            } catch (Exception b) {
                //System.out.println("Error: "+b);
            }
        }
    }

    public boolean printAllActive() {
        boolean anyEntryActive=false;
        Error error= new Error();
        int idInFile;
        String nameInFile;
        float priceInFile;
        boolean isActiveInFile;
        File file = new File("Data.dat");
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            if(checkEmpty("Data.dat")){
                error.print("Error! empty file");
            }
            else {
                System.out.printf("\n| %-10s | %-20s | %10s |%n", "ID", "Name", "Price");
                System.out.println("-----------------------------------------------------");
                while (dis.available() > 0) {
                    idInFile = dis.readInt();
                    nameInFile = dis.readUTF();
                    priceInFile = dis.readFloat();
                    isActiveInFile = dis.readBoolean();
                    if (isActiveInFile) {
                        System.out.printf("| %-10d | %-20s | %10s |%n", idInFile, nameInFile, priceInFile);
                        anyEntryActive=true;
                    }
                }
                System.out.println("-----------------------------------------------------\n");
                if(!anyEntryActive){
                    error.print("There isnt't active entries!");
                }
            }
        } catch (Exception e) {
            //System.out.println("Error: "+e);

        } finally {
            try {
                fis.close();

            } catch (Exception b) {
                //System.out.println("Error: "+b);
            }
        }
        return anyEntryActive;
    }

    public boolean printAllDeactivated() {
        boolean anyEntryDeactivated=false;
        Error error= new Error();
        int idInFile;
        String nameInFile;
        float priceInFile;
        boolean isActiveInFile;
        File file = new File("Data.dat");
        FileInputStream fis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            if(checkEmpty("Data.dat")){
                error.print("There is no entries!");
            }
            else {
                System.out.printf("\n| %-10s | %-20s | %10s |%n", "ID", "Name", "Price");
                System.out.println("-----------------------------------------------------");
                while (dis.available() > 0) {
                    idInFile = dis.readInt();
                    nameInFile = dis.readUTF();
                    priceInFile = dis.readFloat();
                    isActiveInFile = dis.readBoolean();
                    if (!isActiveInFile) {
                        System.out.printf("| %-10d | %-20s | %10s |%n", idInFile, nameInFile, priceInFile);
                        anyEntryDeactivated=true;
                    }
                }
                System.out.println("-----------------------------------------------------\n");
                if(!anyEntryDeactivated){
                    error.print("There isnt't any deactivated entries!");
                }
            }
        } catch (Exception e) {
            //System.out.println("Error: "+e);

        } finally {
            try {
                fis.close();

            } catch (Exception b) {
                //System.out.println("Error: "+b);
            }
        }
        return anyEntryDeactivated;
    }

    public Data edit(Data data){
        Data auxData = new Data(data.getExists(),
                                data.getID(),
                                data.getName(),
                                data.getPrice(),
                                data.getisActive());

        File file = new File("Data.dat");
        File tempFile = new File("TempData.dat");
        FileInputStream fis = null;
        DataInputStream dis = null;
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try{
            fis = new FileInputStream(file);
            dis = new DataInputStream(fis);

            fos = new FileOutputStream(tempFile, true);
            dos = new DataOutputStream(fos);

            int idInFile;
            String nameInFile;
            float priceInFile;
            boolean isActiveInFile;
            do {
                idInFile=dis.readInt();
                nameInFile=dis.readUTF();
                priceInFile=dis.readFloat();
                isActiveInFile=dis.readBoolean();
                if (auxData.getID() == idInFile && auxData.getPrice() == priceInFile && auxData.getName().toLowerCase().equalsIgnoreCase(nameInFile)){
                    if(auxData.getisActive()){
                        auxData=auxData.update(auxData);
                    }
                    write(auxData,"TempData.dat");
                }
                else{
                    data.setID(idInFile);
                    data.setName(nameInFile);
                    data.setPrice(priceInFile);
                    data.setisActive(isActiveInFile);
                    write(data,"TempData.dat");
                }

            }while(dis.available() > 0);

        } catch (IOException e) {
            //System.out.println("Error: " + e.getMessage());
        }
        finally{
            try{
                fis.close();
                fos.close();
                file.delete();
                tempFile.renameTo(file);
            }
            catch (Exception e) {
                //System.out.println("Error: " + e.getMessage());
            }
        }
        return data;
    }

    public boolean checkEmpty(String path){
        File file = new File(path);
        boolean anyEntryActivated;
        if(file.exists()){
            return false;
        }
        if(file.length()==0){
            return true;
        }
        return false;
    }
}