 public class Portfolio extends PortfolioTest {
    private StockData[] stocks;
    private String investor;
    private int numOfStocks; 
    public Portfolio(String stockIt)
    {
        investor = stockIt;
        stocks = new StockData[3];
    }

    public String getInvestor()  
    {
        return investor;
    }

    public StockData[] getStocks()
    {
        return stocks;
    }

    public StockData getStockData(String stockName)
    {
        for(int i = 0; i < stocks.length; i++)
        {
            if(stocks[i].getStockName().equals(stockName))
            {
                return stocks[i];
            }
        }
        return null; 
    }
    
    public int getNumOfStocks() 
    {
        return numOfStocks; 
    }
    
    public void addNewStock(String companyName, String stockSymbol) throws Exception
    {
        if(numOfStocks >= stocks.length)
        {
          System.out.print("Portfolio is at capacity. Cannot add any more stocks.\n");
        }
        else
        {
           stocks[numOfStocks] = new StockData(companyName, stockSymbol);
           numOfStocks++;
        }
    }

    public boolean hasSameStartingDays()
    {
       boolean result = true;
       if(numOfStocks ==0 || numOfStocks ==1)
       {
           return true;
       }
       else
       {
           String s = stocks[0].getDates()[0];
           for(int i = 0; i < numOfStocks; i++)
           {
               if(stocks[i].getDates()[0].equals(s))
               {
                   result= true;
               }
               else
               {
                   return false;
               }
           }
           return result;
       }
    }

    public String getMostExpensiveStock()
    {
      
        if(numOfStocks == 0)
        {
            return null;
        }
            String grow = "";
           double growth = stocks[0].getMax();
           int j = 0; 
           grow = stocks[0].getStockName() + " : " + stocks[0].getMax();
           for(int i = 1; i < numOfStocks; i++)
           {
            
              if(stocks[i].getMax() > growth)
              {
                growth = stocks[i].getMax();
                j = i; 
              }
            
            }
            grow = stocks[j].getStockName() + " : " + stocks[j].getMax();
      
       
       return grow;
  
    }

    public String getCheapestStock()
    {
        
        String s = "";
        int j = 0;
        double min = stocks[0].getMin();
        for(int i = 0; i < stocks.length; i++)
        {
            if(stocks[i] != null)
            
              if(stocks[i].getMin() < min)
              {
                min = stocks[i].getMin();
                j = i; 
              }
        }
        if(stocks[j] == null)
        {
            return null;
        }
        else 
        {
            s = stocks[j].getStockName() + " : " + stocks[j].getMin();
        }
        return s;
    }

    public String getBestGrowingStock()
    {
         if(numOfStocks == 0)
        {
            return null;
        }
        double growth = stocks[0].getPercentGrowth();
        int k = 0; 
        String grow = "";
        for(int i = 1; i < numOfStocks; i++)
        {
            
              if(stocks[i].getPercentGrowth() > growth)
              {
                growth = stocks[i].getPercentGrowth();
                k = i; 
              }
            
        }
        grow = stocks[k].getStockName() + " grew by " + stocks[k].getPercentGrowth() + "%";
        return grow;
    }

    public String getWorstGrowingStock()
    {
         if(numOfStocks == 0)
        {
            return null;
        }
        double no = stocks[0].getPercentGrowth();
        int k = 0; 
        String now = "";
        for(int i = 0; i < numOfStocks; i++)
        {
             
              if(stocks[i].getPercentGrowth() < no)
              {
                no = stocks[i].getPercentGrowth();
                k = i; 
              }
            
        }
        now = stocks[k].getStockName() + " grew by " + stocks[k].getPercentGrowth() + "%";
        return now;
    }
    public void normalizePortfolio()
    {
       for (int i = 0; i < stocks.length; i++)
       {
           stocks[i].normalizeClosings();
       }
    }

    public String getStartingDay()
    {
      if(stocks[0] == null)
      {
         return null;
      }
      else
      {
        return stocks[0].getDates()[0];
      }
    }
    public void visualize()
      {
        PortfolioVisualizer.visualize(this);
      }

    public String getInvestmentAdvice()
      {
        String advice ="";
        return advice;
      }

    public String toString()
    { 
        String s = investor + "'s Portfolio\n" + "Number of Stocks: " + numOfStocks + "\nStarting Date: " + getStartingDay() + "\n" + getBestGrowingStock();
        return s;
    }

    /*
      *   Add your stocks to the portfolio here.
     *   
     */
     public static void main(String[] args) throws Exception{
        int a= 5;
    }
   }
