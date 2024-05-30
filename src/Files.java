import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.SeriesDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javax.swing.JFrame;



public class Files {
    final private String dataFileName;
    private RandomAccessFile dataStream;
    private ArrayList<Employees> employeesList = null;
    private final int bytesEntry;
    private final int idBytes=4;
    private final int monthlySalesBytes=4;
    private final int branchBytes=4;
    private int totalEntries=0;
    JFrame frame=null;

    public Files(String dataFileName) {
        this.dataFileName = dataFileName;
        bytesEntry = idBytes+(6*monthlySalesBytes)+branchBytes;
        employeesList = new ArrayList<Employees>();
    }
    private void open(){
        try{
            dataStream = new RandomAccessFile(dataFileName, "rw");
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void loadInMemory(){
        Employees employee = new Employees(0,0,0,0,0,0,0,0);
        long fileSize=0;
        try{

            fileSize = dataStream.length();
            totalEntries = (int)fileSize / bytesEntry;
            for(int x=0; x<totalEntries; x++){
                employee = new Employees(0,0,0,0,0,0,0,0);

                dataStream.seek(x*bytesEntry);

                employee.setID(dataStream.readInt());
                employee.setJanuarySales(dataStream.readFloat());
                employee.setFebruarySales(dataStream.readFloat());
                employee.setMarchSales(dataStream.readFloat());
                employee.setAprilSales(dataStream.readFloat());
                employee.setMaySales(dataStream.readFloat());
                employee.setJuneSales(dataStream.readFloat());
                employee.setBranch(dataStream.readInt());
                employeesList.add(employee);
            }
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void prepareFiles(){
        open();
        loadInMemory();
        close();
    }

    public void close(){
        try{
            if (dataStream != null)
                dataStream.close();
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean employeeExists(int searchedEmployee){
        Employees employee = new Employees(0,0,0,0,0,0,0,0);
        for (int x=0;x<employeesList.size();x++){
            employee = new Employees(0,0,0,0,0,0,0,0);
            employee=employeesList.get(x);
            if(employee.getID()==searchedEmployee){
                return true;
            }
        }
        return false;
    }

    public void add(){
        Employees employee = new Employees(0,0,0,0,0,0,0,0);
        Error error = new Error();
        boolean exists=true;
        do{
            employee = new Employees(0,0,0,0,0,0,0,0);
            employee = employee.requestID(employee);
            exists=employeeExists(employee.getID());
            if(exists){
                error.print("This value, already exists");
            }
        }while (exists);
        employee = employee.requestSales(employee,"all");
        employee = employee.requestBranch(employee);
        employeesList.add(employee);
    }
    public void barChart(){
        //pie chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset ();

        //get totals
        float totalNorth=getTotalBranch("north");
        float totalSouth=getTotalBranch("south");

        //add values to data set
        dataset.setValue(totalNorth,"Sales","North");
        dataset.setValue(totalSouth,"Sales","South");



        //construct object chart
        JFreeChart barchart = ChartFactory.createBarChart(
                "Total sales by branch",
                "",
                "",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);


        ChartPanel chart = new ChartPanel(barchart);
        chart.setMouseWheelEnabled(true);
        chart.setPreferredSize(new Dimension(500,500));


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setAlwaysOnTop( true );
        frame.setTitle("Chart");
        frame.add(chart);
        frame.repaint();
        frame.setVisible(true);
        frame.toFront();
        frame.pack();
        frame.repaint();
    }
    public void lineChart(){
        //pie chart
        float [] salesNorth=monthtlySales("north");
        float [] salesSouth=monthtlySales("south");
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        TimeSeries north = new TimeSeries("north");
        TimeSeries south = new TimeSeries("south");
        for (int x=0;x<6;x++){
            north.add(new Month(x+1,2024),salesNorth[x]);
        }
        dataset.addSeries(north);

        for (int x=0;x<6;x++){
            south.add(new Month(x+1,2024),salesSouth[x]);
        }
        dataset.addSeries(south);

        JFreeChart LineChart = ChartFactory.createTimeSeriesChart(
                "Sales by month", // Chart
                "Date", // X-Axis Label
                "Total Sales", // Y-Axis Label
                dataset);

        ChartPanel chart = new ChartPanel(LineChart);

        chart.setMouseWheelEnabled(true);
        chart.setPreferredSize(new Dimension(500,500));


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setAlwaysOnTop( true );
        frame.setTitle("Chart");
        frame.add(chart);
        frame.repaint();
        frame.setVisible(true);
        frame.toFront();
        frame.pack();
        frame.repaint();
    }

    public float[] monthtlySales(String branch){
        float[] sales=new float[6];
        Employees employee = new Employees(0,0,0,0,0,0,0,0);
        //[1-North][2-South]
        Error error = new Error();
        for (int x=0;x<employeesList.size();x++) {
            employee = new Employees(0,0,0,0,0,0,0,0);
            employee = employeesList.get(x);
            if (branch=="north"&&employeesList.get(x).getBranch()==1) {
                sales[0]=sales[0]+employee.getJanuarySales();
                sales[1]=sales[1]+employee.getFebruarySales();
                sales[2]=sales[2]+employee.getMarchSales();
                sales[3]=sales[3]+employee.getAprilSales();
                sales[4]=sales[4]+employee.getMaySales();
                sales[5]=sales[5]+employee.getJuneSales();
            }
            else if (branch=="south"&&employeesList.get(x).getBranch()==2) {
                sales[0]=sales[0]+employee.getJanuarySales();
                sales[1]=sales[1]+employee.getFebruarySales();
                sales[2]=sales[2]+employee.getMarchSales();
                sales[3]=sales[3]+employee.getAprilSales();
                sales[4]=sales[4]+employee.getMaySales();
                sales[5]=sales[5]+employee.getJuneSales();
            }
        }
        return sales;
    }



    public void pieChart(){
        //pie chart
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        //get totals
        float totalNorth=getTotalBranch("north");
        float totalSouth=getTotalBranch("south");

        //add values to data set
        pieDataset.setValue("North",totalNorth);
        pieDataset.setValue("South",totalSouth);



        //construct object chart
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Total sales by branch",//title
                pieDataset,//dataset
                true,//legend
                true,//tooltip
                false//use to generate URLs
        );
        final PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator("{0} = {1}");
        PiePlot plot1 = (PiePlot) pieChart.getPlot();
        plot1.setLabelGenerator(labelGenerator);


        ChartPanel chart = new ChartPanel(pieChart);
        chart.setMouseWheelEnabled(true);
        chart.setPreferredSize(new Dimension(500,500));


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setAlwaysOnTop( true );
        frame.setTitle("Chart");
        frame.add(chart);
        frame.repaint();
        frame.setVisible(true);
        frame.toFront();
        frame.pack();
        frame.repaint();
    }

    public float getTotalBranch(String branch){
        Employees employee = new Employees(0,0,0,0,0,0,0,0);
        //[1-North][2-South]
        Error error = new Error();
        float total=0;
        for (int x=0;x<employeesList.size();x++) {
            employee = new Employees(0,0,0,0,0,0,0,0);
            employee = employeesList.get(x);
            if (branch=="north"&&employeesList.get(x).getBranch()==1) {
                total = total + employee.getTotal();
            }
            else if (branch=="south"&&employeesList.get(x).getBranch()==2) {
                total = total + employee.getTotal();
            }
        }
        return total;
    }
    public void saveToDisk(){
        Employees employee = new Employees(0,0,0,0,0,0,0,0);
        try{
            File data=new File(dataFileName);
            data.delete();
            open();

            for(int x=0; x<employeesList.size(); x++){
                employee = new Employees(0,0,0,0,0,0,0,0);
                dataStream.seek(x*bytesEntry);
                employee=employeesList.get(x);
                dataStream.writeInt(employee.getID());
                dataStream.writeFloat(employee.getJanuarySales());
                dataStream.writeFloat(employee.getFebruarySales());
                dataStream.writeFloat(employee.getMarchSales());
                dataStream.writeFloat(employee.getAprilSales());
                dataStream.writeFloat(employee.getMaySales());
                dataStream.writeFloat(employee.getJuneSales());
                dataStream.writeInt(employee.getBranch());
            }
            close();
        } catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}