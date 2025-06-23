package leadersCenter.leadersCenter;

import java.time.Duration;
import java.util.Random;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestData {

	WebDriver driver = new ChromeDriver();
	String URL = "https://leaders.jo/en/";

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	JavascriptExecutor js = (JavascriptExecutor) driver;
	Actions actions = new Actions(driver);

	// Arrays of test data
	String[] firstNames = { "Lubab", "Areen", "Jamal" }; 
	String[] lastNames = { "Alkhaldi", "Alrefai", "Khaleel" };
	String[] streets = { "Al-Shaheed", "Al-Quds", "Al-Urdon" };
	int[] numbers = { 35, 47, 99 };
	int[] phones = { 456746635, 585556847, 586856899 };

	// Generate one random index for all arrays
	Random random = new Random();
	int randomIndex = random.nextInt(firstNames.length); // assuming all arrays have the same length

	// Select data using the same index
	String selectedFirstName = firstNames[randomIndex];
	String selectedLastName = lastNames[randomIndex];
	String selectedStreet = streets[randomIndex];
	int selectedNumber = numbers[randomIndex];
	int selectedPhone = phones[randomIndex];

	// Generate email and password based on selected values
	String emailValue = selectedFirstName.toLowerCase() + selectedNumber + "@gmail.com";
	String passwordValue = selectedFirstName + "@" + selectedNumber + "pass";
}