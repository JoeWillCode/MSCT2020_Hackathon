package back;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Forecast 
{ 
    private String numOfPeople;
    private String fip;
    private File zipToFip = new File("res/ziptofip.txt");
    private double[] values;

    //constructor finds the fip needed to get the actual data, each constructor creates needed data taken from
    //the day it is created
    public Forecast(String zip, String numOfPeople)
    {
        this.fip = findFip(zip);
        this.numOfPeople = numOfPeople;
        this.values=createValues(findRawValues(createJSON()));
    }

    //functions to get values for the GUI to call on
    public double[] getValues()
    {
        return values;
    }

    //using the zip code provided, finds the fip of the location of the individual
    //this is done since the api needs a fip and since most people dont know their
    //fip, we chose to ask for their zip and convert to the needed fip, using a txt table
    private String findFip(String zip)
    {
        try
        {
            Scanner scan = new Scanner(zipToFip);
            while(scan.hasNextLine())
            {
                String line = scan.nextLine();
                String possibleZip = line.substring(0, line.indexOf(","));
                if(possibleZip.equals(zip))
                {
                    fip = line.substring(line.indexOf(",")+1);
                    break;
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return fip;
    }

    //takes the info from the page using the persons fip and makes it one long JSON string to be used
    private String createJSON()
    {
        String JSON = "";
        try
        {
            String url = "https://api.covidactnow.org/v2/county/"+fip+".json?apiKey=648b0c9292f34dd99bb0ac33b3f2e74b";
            URL covidActNowAPI = new URL(url);
            Scanner scan = new Scanner(covidActNowAPI.openStream());
                while(scan.hasNextLine())
                {
                    String line = scan.nextLine();
                    JSON = JSON.concat(line);
                }
            scan.close();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
        	e.printStackTrace();
        }
        return JSON;
    }

    //raw values to be used for calculations
    private String[] findRawValues(String JSON)
    {
        String[] values = new String[3];
        //gets population of people on the day
        values[0] = JSON.substring((JSON.indexOf("population")+13), JSON.indexOf(",", (JSON.indexOf("population")+13)));
        //gets the current number of cases
        values[1] = JSON.substring((JSON.indexOf("newCases")+11), JSON.indexOf(",", (JSON.indexOf("newCases")+11)));
        //gets the current ICU ratio
        values[2] = JSON.substring((JSON.indexOf("icuCapacityRatio")+19), JSON.indexOf(",", (JSON.indexOf("icuCapacityRatio")+19)));
        return values;
    }

    //creates values for the GUI to display
    private double[] createValues(String[] rawValues)
    {
        double[] values = new double[3];
        //gets (unmasked) percentage chance of getting COVID using ammount of people the person is in contact with on given day
        double prctOfPopWithCovid = (14.0*Double.parseDouble(rawValues[1]))/Double.parseDouble(rawValues[0]);
        values[0] = (1 - Math.pow((1 - prctOfPopWithCovid), Double.parseDouble(numOfPeople))) * 100.0;
        //gets (masked) percentage chance of getting COVID using ammount of people the person is in contact with on given day\
        values[1] = (1 - Math.pow((1 - prctOfPopWithCovid * 0.25), Double.parseDouble(numOfPeople))) * 100.0;
        //gets percentage of ICU in use that day
        if (!rawValues[2].equals("null"))
        {
        	values[2] = 100.0 * Double.parseDouble(rawValues[2]);
        }
        else
        {
        	values[2] = -1.0;
        }
        return values;
    }
}
