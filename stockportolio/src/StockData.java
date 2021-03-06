import java.io.File;
import java.util.Scanner;

public class StockData 
{
    private String stockName;
    private String stockSymbol;
    private double[] openings;
    private double[] closings; 
    private String[] dates;
    private double[] movingAverages;
    private int period;
    private double[] normalizeClosings;
   
    // Do not modify this constructor header. 
    public StockData(String name, String symbol) throws Exception
    {
        stockName = name;//"Snap Inc.";
        stockSymbol = symbol;//"SNAP";
        period = 20; 
        getStockData();
        double normalizeClosings [] = new double [closings.length];
    }
    public double [] normalizeClosings()
    {
       double normal = closings[0];
        for (int i =0; i < closings.length; i++)
       {
           normalizeClosings[i] = closings[i]/normal;  
       }
       return normalizeClosings; 
    }
    public String getStockName()
    {   
        return stockName; 
    }
    public String getStockSymbol()
    {   
        return stockSymbol; 
    }
    public double[] getOpenings()
    {   
        return openings; 
    }
    public double[] getClosings()
    {   
        return closings; 
    }
    public String[] getDates()
    {   
        return dates; 
    }
    public double getMax()
        {  
      double max = closings[0];
      for (double n : closings)
        {
            if (n > max)
            {
                max = n;
            }
        }
      return max;
      }
    public double getMin()
    { 
      double min = closings[0];
      for (double n : closings)
       {
           if (n < min)
           {
                min = n;
           }
       }
       return min;
    }   
    public double getPercentGrowth()
    { 
      return (double)((closings[closings.length -1] - closings[0])/closings[0])* 100;
    }
    public String getWorstDay()
    { 
      int j = 0;
      double diff = closings[0] - openings[0];
      for(int i = 0; i< closings.length; i++)
      {
        if (diff > closings[i]-openings[i])
        {
          j= i;
          diff = closings[i]-openings[i];
        }
      }
      return dates[j];
    }
    public String getBestDay()
    {
      int j = 0;
      double diff = closings[0] - openings[0];
      for(int i = 0; i< closings.length; i++)
      {
        if (diff < closings[i]-openings[i])
        {
          j= i;
          diff = closings[i]-openings[i];
        }
      }
      return dates[j];
    }
    public double changeInValue(String day_1_close, String day_2_close)
    {
      double day1 = 0;
      double day2 = 0;
      for(int i = 0; i < dates.length;i++)
      {
        if (dates[i].equals(day_1_close))
        {
          day1 = closings[i];
        }
        if (dates[i].equals(day_2_close))
        {
          day2 = closings[i];
        }
      }
      return day2-day1;
    }
    public double [] setMovingAverages() 
    {
        int period = 20;
        double average = 0.0;
        for(int i = 0; i < closings.length -20; i++)
         {
           for(int w = i; w < w + 20; w++)
            {
                    average += closings[w];
            }
           movingAverages[i] = average/period; 
        }

        return movingAverages;
    }
    public String toString()
    {   
        String str = stockName+": " + stockSymbol + "\n";
        str += "Date\tOpen\tClose\n";
        for(int i= 0; i < closings.length; i++)
        {
            str += dates[i] + "\t" + openings[i] + "\t" +  closings[i] + "\n";
        }
        return str;
    }
    /************************************************************************************
    *
    *   THE METHODS BELOW ARE FOR READING CSV DATA AND CREATING A GRAPH OF YOUR DATA.
    *   MODIFYING ANYTHING BELOW CAN LEAD TO YOUR CSV FILES NOT BEING PROPERLY PROCESSED
    *   AND YOUR GRAPHS TO NO LONGER FUNCTION. 
    *   
    *   PROCEED AT YOUR OWN RISK, BOLD ADVENTURER. :)
    *
    *************************************************************************************/

    private void getStockData() throws Exception{
        String fileName = "../input/" + this.stockSymbol + ".csv";
        Scanner s = new Scanner(new File(fileName));
        // advance past the header row
        s.nextLine();
        int N = 0;
        while (s.hasNextLine()){
            N++;
            s.nextLine();
        }
        s.close();

        // [Date, Open, High, Low, Close, Adj Close, Volume]
        dates = new String[N];
        openings = new double[N];
        closings = new double[N];
        s = new Scanner(new File(fileName));
        s.nextLine();
        int i = 0;
        // Build a new Scanner object
        while (i < N){
            String[] line = s.nextLine().split(",");
            dates[i] = line[0];
            openings[i] = Double.parseDouble(line[1]);
            closings[i] = Double.parseDouble(line[4]);
            i++;
        }
        s.close();
    }
}