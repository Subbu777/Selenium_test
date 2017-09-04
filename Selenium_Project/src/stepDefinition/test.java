package stepDefinition;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.en.And;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

//import org.junit.Assert;


public class test {
public static WebDriver driver;

public static WebElement start_now;

public static WebElement enterno;
public static Sheet s1;
public int rows;
public static FileInputStream file;
public static Workbook objwb;

public static WritableWorkbook wwb;
public static WritableSheet ws;

public static Label r;
public static Label r1; 
public static Label r2;
public static Label r3;


@When("^I open automationpractice website$")
public void I_open_automationpractice_website()throws Exception
{
	
	System.setProperty("webdriver.chrome.driver","C:\\test\\chromedriver.exe");
	ChromeDriver driver=new ChromeDriver();
	driver.get("https://www.gov.uk/get-vehicle-information-from-dvla");
	
	start_now=driver.findElement(By.xpath("//*[@id='get-started']/a"));
	start_now.click();
	
	 file=new FileInputStream("C:\\test\\Vehicle details.xls");
		objwb=Workbook.getWorkbook(file);
		s1=objwb.getSheet(0);
		
		
	wwb = Workbook.createWorkbook(new FileOutputStream("C:\\test\\Vehicle1.xls"));
    ws = wwb.createSheet("Vehicle",0);
     Label l1 = new Label(0, 0, "Vehicle no");
     Label l2 = new Label(1, 0, "make");
     Label l3 = new Label(2, 0, "colour");
     Label l4 = new Label(3, 0, "Result");
     
     ws.addCell(l1);
     ws.addCell(l2);
     ws.addCell(l3);
     ws.addCell(l4);
     Thread.sleep(2000);
     				
		int rows=s1.getRows();
		System.out.println(rows);
		
		for(int i=1;i<rows;i++)
		{
			
		Thread.sleep(3000);
		
		enterno=driver.findElement(By.name("Vrm"));
		enterno.sendKeys(s1.getCell(0,i).getContents());
		driver.findElement(By.xpath("//*[@id='content']/form/div/div/div[2]/fieldset/button")).click();
		
		DateFormat df=new SimpleDateFormat("yyyy_MM_dd hh_ss_mm");
		Date date=new Date();
		String  time=df.format(date);
		
		System.out.println(time);
		
		File scrfile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile, new File("C:\\test\\Vehicle"+time+".png"));
		
		
			String un=s1.getCell(0,i).getContents();
			String u_make=s1.getCell(1,i).getContents();
			String u_colour=s1.getCell(2,i).getContents();
			
			Thread.sleep(2000);
			String make=driver.findElement(By.xpath("//*[@id='pr3']/div/ul/li[2]/span[2]/strong")).getText();
			String colour=driver.findElement(By.xpath("//*[@id='pr3']/div/ul/li[3]/span[2]/strong")).getText();
			
			String res="matching";
			String res1 = "not matching";
			
			Thread.sleep(2000);
			if(make.equals(u_make) && colour.equals(u_colour) )
			{
				System.out.println(make+ " "+colour+" data matching");
				r = new Label(0, i, s1.getCell(0, i).getContents());
				r1 = new Label(1, i, s1.getCell(1, i).getContents());
	            r2 = new Label(2, i, s1.getCell(2, i).getContents());
	            r3 = new Label(3, i, res);
	       
	        ws.addCell(r);              
	        ws.addCell(r1);
	        ws.addCell(r2);
	        ws.addCell(r3);
				
				
			}
			else
	        {
	            System.out.println("not matching");
	            r = new Label(0, i, s1.getCell(0, i).getContents());
				r1 = new Label(1, i, s1.getCell(1, i).getContents());
	            r2 = new Label(2, i, s1.getCell(2, i).getContents());
	            r3 = new Label(3, i, res1);
	                                    
	        ws.addCell(r);  
	        ws.addCell(r1);
	        ws.addCell(r2);
	        ws.addCell(r3);
	                                      
	        }
			
			Thread.sleep(2000);
			driver.findElement(By.xpath("//*[@id='content']/div[2]/a")).click();
	 
	}
		 wwb.write();
	     wwb.close();
		 driver.close();
		
			
		}
	
	
}
	
	