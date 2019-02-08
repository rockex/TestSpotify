package TestSpotify.TestSpotify;


import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class AdminList {
	
	public WebDriver driver;
	public int flag=0;
	
	
	
	@Test(priority = 1)
	public void SetearDriver() throws IOException {

		// System.setProperty("webdriver.gecko.driver","C:\\webdriver\\geckodriver.exe");
		// //Para Firefox
		// WebDriver driver = new FirefoxDriver(); //para Firefox
		System.setProperty("webdriver.chrome.driver", "C:\\webdriver\\chromedriver.exe"); // Para Chrome
		driver = new ChromeDriver(); // para Chrome
		driver.manage().window().maximize();
		String url = "https://www.spotify.com/cl/";
		driver.get(url);		
	}
	
	
	
	@Test(priority = 2)
	public void LoginSpotify() throws Exception {
				
		WebDriverWait wait = new WebDriverWait(driver, 10);		
			
		
		WebElement LinkIniSesion = driver.findElement(By.xpath("//a[@id='header-login-link']"));
		LinkIniSesion.click();
				 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='login-username']")));
		WebElement txtUsuario = driver.findElement(By.xpath("//input[@id='login-username']"));
		txtUsuario.sendKeys("d.manzo@hotmail.com");
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='login-password']")));
		WebElement txtPass = driver.findElement(By.xpath("//input[@id='login-password']"));
		txtPass.sendKeys("kikoex"); 
		 
		WebElement check = driver.findElement(By.xpath("//span[@class='control-indicator']"));
		check.click();
				 
		WebElement BtnInicia = driver.findElement(By.xpath("//button[@id='login-button']"));
		BtnInicia.click();
		
		 
		//bajar scroll
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='nav-link-play']")));
		WebElement LinkRepWeb = driver.findElement(By.xpath("//a[@id='nav-link-play']"));
					 
		JavascriptExecutor js = ((JavascriptExecutor) driver);        
        js.executeScript("arguments[0].scrollIntoView();", LinkRepWeb);//Baja el  scroll hasta que encuentra el  elemento
	         	        
        LinkRepWeb.click();
		
		
        int resp=1;
		//Si el boton EXISTE, es porque debe aceptar los terminos
		if(driver.findElements(By.xpath("//button[@class='btn btn-green']")).size() != 0){resp=0;}
		
		//Si es 0, acepta los terminos
		if(resp==0) {
			
			// agree
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='btn btn-green']")));
			WebElement btnAgree = driver.findElement(By.xpath("//button[@class='btn btn-green']"));
			btnAgree.click();
		}
		
		//wait de 5 segundos
		Thread.sleep(5000);
        
		/*
		 * //Click PLAY
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class='control-button spoticon-play-16 control-button--circled']")));
			WebElement btnPLAY = driver.findElement(By.xpath("//button[@class='control-button spoticon-play-16 control-button--circled']"));
			btnPLAY.click();		
		
			//Si el  boton pause no existe, presiona PLAY nuevamente
			if(driver.findElements(By.xpath("//button[@class='control-button spoticon-pause-16 control-button--circled']")).size() == 0) {btnPLAY.click();} 
		 * 
		 */
		
		String[] PlayList = { "Nickelback", "System of a Down", "Metallica", "Pearl Jam" };
		int numRandom = (int) (Math.random() * 3);
		//System.out.println("---->:::: "+numRandom);
		
		for(int j=0;j<=3;j++) {	
			BuscarCancion(PlayList[j]);
			
			//wait de 30 segundos
			Thread.sleep(30000);
			flag = 1;
		}
		
		//cierra navegador
        driver.quit();

	}
	
	
	//@Test(priority = 3)
	public void BuscarCancion(String Artista) throws Exception {
				
		WebDriverWait wait = new WebDriverWait(driver, 10);		
			
		if(flag==0) {
			WebElement BtnBuscar = driver.findElement(By.xpath("//div[@class='icon search-icon NavBar__icon']"));
			BtnBuscar.click();
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='SearchInputBox__input']")));
		WebElement txtBusqueda = driver.findElement(By.xpath("//input[@class='SearchInputBox__input']"));
		txtBusqueda.clear();
		txtBusqueda.sendKeys(Artista);		
		
		//wait de 8 segundos
		Thread.sleep(8000);
		
		//Se cae o no genera 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']/div/div[4]/div[2]/div[1]/section/div[2]/div/div/div[1]/div[2]/section/section/ol/div")));		
		int TotalRegistros = driver.findElements(By.xpath("//*[@id='main']/div/div[4]/div[2]/div[1]/section/div[2]/div/div/div[1]/div[2]/section/section/ol/div")).size();
		//int numeroRandom = (int) (Math.random() * TotalRegistros);	
		
		int minimo = 1;
		int maximo = 4;
		int numeroRandom=(int)Math.floor(Math.random()*(maximo-minimo+1)+(minimo));
		
		System.out.println("--> numeroRandom: " + numeroRandom + "--> TotalRegistros: " + TotalRegistros);
		

		//Si el span no existe, cambia a otro que si exista (4)
		//if(driver.findElements(By.xpath("//*[@id='main']/div/div[4]/div[2]/div[1]/section/div[2]/div/div/div[1]/div[2]/section/section/ol/div["+ numeroRandom +"]/div/li/div[1]/div[2]/span")).size() == 0) {
		//	numeroRandom = 4;
		//	System.out.println("--> Se cambio Numero Random: " + numeroRandom);
		//} 
		
		
		WebElement DivSeleccionar = driver.findElement(By.xpath("//*[@id='main']/div/div[4]/div[2]/div[1]/section/div[2]/div/div/div[1]/div[2]/section/section/ol/div["+ numeroRandom +"]/div/li/div[1]/div[2]/span"));
		DivSeleccionar.click();
		
	}
	
	
	

}
