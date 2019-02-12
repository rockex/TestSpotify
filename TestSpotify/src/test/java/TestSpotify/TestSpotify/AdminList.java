package TestSpotify.TestSpotify;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
		
		String[] PlayList = { "Nickelback", "System of a Down", "Metallica", "Pearl Jam", "Linkin Park", "Rammstein", 
							  "Five Finger Death Punch", "Foo Fighters", "Incubus"};
		int numRandom = (int) (Math.random() * 3);
		//System.out.println("---->:::: "+PlayList.length+" numRandom: "+numRandom);
		


		for(int j=0;j<PlayList.length;j++) {
			
			try {				
				BuscarCancion(PlayList[j]);				
				
				if(flag ==2) {j--;}//Reintenta con el mismo artista si no carga la pista!
				flag = 1;
				
			}catch(Exception ex){
				
				System.out.println("ERROR AL SELECCIONAR "+ PlayList[j] +", SE DESCUENTA -1 AL CICLO: "+ j +" PARA REINTENTAR");
				j--;							
			}			
		}//Fin FOR			

	
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
		
		//wait de 8 segundos = 8000 milisegundos
		Thread.sleep(8000);
		
		//Conteo de Canciones 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='main']/div/div[4]/div[2]/div[1]/section/div[2]/div/div/div[1]/div[2]/section/section/ol/div")));		
		int TotalRegistros = driver.findElements(By.xpath("//*[@id='main']/div/div[4]/div[2]/div[1]/section/div[2]/div/div/div[1]/div[2]/section/section/ol/div")).size();

		//Seteo Rango Random
		int minimo = 1;
		int maximo = 4;
		int numeroRandom=(int)Math.floor(Math.random()*(maximo-minimo+1)+(minimo));		
		System.out.println("--> numeroRandom: " + numeroRandom + "--> TotalRegistros: " + TotalRegistros);
				
		
		WebElement DivSeleccionar = driver.findElement(By.xpath("//*[@id='main']/div/div[4]/div[2]/div[1]/section/div[2]/div/div/div[1]/div[2]/section/section/ol/div["+ numeroRandom +"]/div/li/div[1]/div[2]/span"));
		DivSeleccionar.click();		
		WebElement ProgressTime = driver.findElement(By.xpath("//*[@id='main']/div/div[5]/footer/div/div[2]/div/div[2]/div[1]"));
			
		int cont = 0;
		while (true) {//Condición trivial: siempre cierta
			System.out.println("-->EN ESPERA AL INICIO DE LA PISTA..." + cont);
			
			if(ProgressTime.getText().equalsIgnoreCase("0:01")) {System.out.println("-->INICIO DE PISTA EXITOSO");break;}
			
			//Valida el tiempo de espera, para reintentar otra pista
			if(cont>=600) {
				System.out.println("-->ERROR EN LA CARGA DE LA PISTA");
				flag = 2;
				return;
			}
			
			cont = cont +1;		
		}
		
		//Cuando inicie la pista, obtiene los datos de  la cancion
		WebElement NameSong = driver.findElement(By.xpath("//*[@id='main']/div/div[5]/footer/div/div[1]/div/div/div[1]/div/span"));
		WebElement TimeSong = driver.findElement(By.xpath("//*[@id='main']/div/div[5]/footer/div/div[2]/div/div[2]/div[3]"));
		System.out.println("Song: "+ NameSong.getText() +" --> Duracion: "+TimeSong.getText()+" --> Progress: "+ProgressTime.getText());
		
				
		
		//RESTA SEGUNDOS A UNA FECHA O VARIABLE TIME
		DateFormat df = new SimpleDateFormat("m:ss");
		Date TimeLimite = df.parse(TimeSong.getText());
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(TimeLimite);
	    cal.add(Calendar.SECOND, -8);
	    TimeLimite = cal.getTime();
	        
		
		while (true) {//Condición trivial: siempre cierta			
			if(ProgressTime.getText().toString().equalsIgnoreCase(df.format(TimeLimite).toString())) {
				System.out.println("SE COMPLETO TIEMPO DE REPRODUCCION, FIN....NEXT SONG!!!");
				break;}else {System.out.println("**"+ProgressTime.getText()+"**");}
		}
		
		
	}
	
	
	

}
