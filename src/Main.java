public class Main {
    public static void main(String[] args) {
        Files files = new Files("Data.dat");
        files.prepareFiles();
        String menuText= "Please, choose an option\n[1]- ADD EMPLOYEE\n[2]- BAR CHART\n[3]- POINT CHART\n[4]- PIE CHART\n[9]- EXIT";
        Input input = new Input();
        Error error = new Error();
        int mainMenuOption;

        do {
            mainMenuOption = input.readInt(menuText);
            switch (mainMenuOption) {
                case 1 ->{
                    files.add();
                }
                case 2 ->{
                    files.barChart();
                }
                case 3 ->{
                    files.lineChart();
                }
                case 4 ->{
                    files.pieChart();
                }
                case 9 ->{}

                default -> error.print("ERROR, NOT AN OPTION!");
            }
        } while (mainMenuOption != 9);
        files.saveToDisk();
        if (files.frame!=null){
            files.frame.dispose();
        }
    }
}