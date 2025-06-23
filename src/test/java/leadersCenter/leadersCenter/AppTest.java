package leadersCenter.leadersCenter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest extends TestData {

	// Open site and maximize browser
	@BeforeTest
	public void Setup() {
		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5)); // Set implicit wait time
	}

	// Register new user randomly
	@Test(priority = 1)
	public void userRegistrationAndSignOut() throws InterruptedException {

		WebElement account = driver.findElement(By.xpath("//span[@class='motta-svg-icon motta-svg-icon--account']"));
		account.click();

		WebElement create = driver.findElement(By.xpath("//a[@href='https://leaders.jo/en/my-account/#register']"));
		create.click();

		Thread.sleep(3000);

		WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("reg_email")));
		email.sendKeys(emailValue);

		WebElement password = driver.findElement(By.id("reg_password"));
		password.sendKeys(passwordValue);

		js.executeScript("window.scrollTo(0, 200)");

		Thread.sleep(3000);

		WebElement registerButton = driver.findElement(By.xpath("//button[@name='register']"));
		registerButton.click();

		Thread.sleep(3000);

		boolean isAccountExist = driver.getPageSource()
				.contains("An account is already registered with your email address");

		if (isAccountExist) {
			login();
			Thread.sleep(3000);
		} else {

			Thread.sleep(5000);
			WebElement logOut = driver.findElement(By.xpath("//a[normalize-space()='Log out']"));
			logOut.click();
			Thread.sleep(3000);
		}

		WebElement regPage = driver.findElement(By.xpath("//div[@class='page-header__content']"));
		Assert.assertTrue(regPage.isDisplayed());
	}

	// Login test
	@Test(priority = 2)
	public void login() throws InterruptedException {
		boolean alreadyLoggedIn = driver.getPageSource().contains("Log out");

		if (alreadyLoggedIn) {
			System.out.println("Already logged in, skipping login step.");

			WebElement myAccount = driver.findElement(By.xpath("//h1[@class='page-header__title']"));
			Assert.assertTrue(myAccount.isDisplayed(), "'My Account' element is not displayed");
			return;
		}

		WebElement accountLink = driver.findElement(By.xpath("//h2[normalize-space()='Sign in']"));
		accountLink.click();
		Thread.sleep(3000);

		WebElement loginEmail = driver.findElement(By.id("username"));
		loginEmail.click();
		loginEmail.sendKeys(emailValue);

		WebElement loginPass = driver.findElement(By.id("password"));
		loginPass.click();
		loginPass.sendKeys(passwordValue);

		WebElement loginBtn = driver.findElement(By.xpath("//button[@name='login']"));
		loginBtn.click();

		WebElement myAccount = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='page-header__title']")));
		Assert.assertTrue(myAccount.isDisplayed(), "'My Account' element is not displayed");
	}

	// Filter products by category
	@Test(priority = 3)
	public void category() throws InterruptedException {

		WebElement centerLogo = driver.findElement(By.cssSelector(
				"body > div:nth-child(3) > header:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1) > img:nth-child(1)\r\n"));
		centerLogo.click();
		Thread.sleep(1000);

		js.executeScript("window.scrollTo(0, 1000)");
		WebElement electronics = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Electronics")));
		Thread.sleep(1000);
		actions.moveToElement(electronics).click().perform();

		Thread.sleep(2000);

		WebElement selectDropdown = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//span[@class='select2-selection select2-selection--single']")));
		Thread.sleep(1000);
		actions.moveToElement(selectDropdown).click().perform();

		WebElement mobilesOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(text(), 'Mobiles')]")));
		Thread.sleep(1000);
		actions.moveToElement(mobilesOption).click().perform();

		Thread.sleep(2000);

		WebElement firstProduct = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'product')]")));
		Assert.assertTrue(firstProduct.isDisplayed(), "Mobiles category page did not display products");
	}

	// Filter products by brands
	@Test(priority = 4)
	public void brand() throws InterruptedException {
		Thread.sleep(5000);

		js.executeScript("window.scrollTo(0, 650)");
		Thread.sleep(5000);

		WebElement brand = driver.findElement(By.cssSelector("div[id='bapf_2'] span[role='combobox']"));
		brand.click();

		Thread.sleep(5000);

		WebElement samsung = driver.findElement(By.xpath("//li[contains(text(), 'SAMSUNG')]"));
		samsung.click();

		Thread.sleep(2000);
	}

	// Search for iPhone product
	@Test(priority = 5)
	public void iphoneSearch() throws InterruptedException {
		js.executeScript("window.scrollTo(0, 10)");

		WebElement searchBox = driver.findElement(By.className("dgwt-wcas-search-input"));
		searchBox.sendKeys("iPhone");
		searchBox.sendKeys(Keys.ENTER);

		js.executeScript("window.scrollTo(0, 80)");

		WebElement results = driver.findElement(By.className("products"));
		Assert.assertTrue(results.isDisplayed());
	}

	// Add randomly item
	@Test(priority = 6)
	public void addToCartOnlyOneAvailableProduct() throws InterruptedException {

		js.executeScript("window.scrollTo(0, 400)");

		Thread.sleep(3000);

		// Get all product elements listed
		List<WebElement> products = driver.findElements(By.cssSelector(".products.columns-4 li"));

		// Iterate over each product element
		for (WebElement product : products) {
			// Find the add-to-cart button inside product
			List<WebElement> addBtn = product.findElements(By.cssSelector(".add_to_cart_button"));

			// Check if add button exists and visible
			if (!addBtn.isEmpty() && addBtn.get(0).isDisplayed()) {
				// Get the product's title/name
				WebElement nameElement = product.findElement(By.cssSelector("h2.woocommerce-loop-product__title"));
				String productName = nameElement.getText();

				// Click the add-to-cart button via JavaScript
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", addBtn.get(0));
				System.out.println("Trying to add product to cart: " + productName);

				Thread.sleep(3000);

				// Get the cart counter element's text
				WebElement cartCounter = driver.findElement(By.cssSelector("span.header-cart__counter.header-counter"));
				String countText = cartCounter.getText().trim();

				// Check if cart counter shows a non-zero number
				if (countText.isEmpty() || Integer.parseInt(countText) == 0) {
					// Product not added to cart, continue loop
					continue;
				}

				// Find all product names in the cart page
				List<WebElement> cartItems = driver.findElements(By.cssSelector(".product-name"));
				boolean productInCart = false;

				// Verify if added product is in cart items
				for (WebElement item : cartItems) {
					if (item.getText().toLowerCase().contains(productName.toLowerCase())) {
						productInCart = true;
						break;
					}
				}

				// Output confirmation or bug message
				if (productInCart) {
					System.out.println("Product confirmed in cart: " + productName);
				} else {
					System.out.println("Product not found in cart page (bug): " + productName);
					continue;
				}

				// Break loop after successfully adding one product
				break;
			}
		}
	}

	// Open and verify cart page
	@Test(priority = 7)
	public void testCartReview() throws InterruptedException {

//		Thread.sleep(1000);
//		js.executeScript("window.scrollTo(0, 30)");

		driver.get(URL);

		Thread.sleep(2000);

		WebElement cartIcon = driver.findElement(By.cssSelector(
				"body > div:nth-child(3) > header:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > a:nth-child(1) > span:nth-child(2)"));

		Thread.sleep(3000);

		cartIcon.click();

		WebElement container = driver.findElement(By.cssSelector(".cart-dropdown.dropdown-content.motta-open"));

		Thread.sleep(2000);
		container.findElement(By.cssSelector(
				"div[class='cart-dropdown dropdown-content motta-open'] a[class='motta-button view-cart wc-forward motta-button--subtle']"))
				.click();

//		Thread.sleep(3000);
//		Assert.assertTrue(driver.getCurrentUrl().contains("cart"), "Not on cart page");
	}

	// Proceed to checkout page
	@Test(priority = 8)
	public void testCheckout() throws InterruptedException {

		String msg = "عذرًا، لا يمكننا اتمام طلبك في الوقت الحالي. بعض المنتجات في سلة التسوق الخاصة بك غير متوفرة.";
		if (URL.contains(msg)) {
			Thread.sleep(3000);
			driver.get(URL);
		} else {
			js.executeScript("window.scrollTo(0, 170)");

			WebElement checkoutButton = driver.findElement(By.cssSelector(".checkout-button.button.alt.wc-forward"));
			Thread.sleep(3000);

			checkoutButton.click();
			Thread.sleep(3000);

			WebElement firstName = driver.findElement(By.xpath("//input[@id='billing_first_name']"));
			firstName.sendKeys(selectedFirstName);

			WebElement lastName = driver.findElement(By.xpath("//input[@id='billing_last_name']"));
			lastName.sendKeys(selectedLastName);

			js.executeScript("window.scrollTo(0, 250)");
			Thread.sleep(2000);

			WebElement phone = driver.findElement(By.xpath("//input[@id='billing_phone']"));
			phone.sendKeys(String.valueOf(selectedPhone));

			// Comment the email; because the user login and the email exist before
//			WebElement email = driver.findElement(By.xpath("//input[@id='billing_email']"));
//			email.sendKeys(emailValue);

			WebElement address = driver.findElement(By.xpath("//input[@id='billing_address_1']"));
			address.sendKeys(selectedStreet);

			Thread.sleep(2000);

		}
		// Assert.assertTrue(driver.getCurrentUrl().contains("checkout"), "Not on
		// checkout page");
	}

	// Submit contact us form
	@Test(priority = 9)
	public void contact() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		// Click on "Contact Us" using JavaScript directly
		js.executeScript("document.querySelector(\"a[data-title='call us']\").click();");

		Thread.sleep(2000); // Better to use WebDriverWait instead of Thread.sleep

		WebElement nameField = driver.findElement(By.xpath("//input[@name='your-name']"));
		nameField.sendKeys(selectedFirstName + selectedLastName);

		WebElement phoneField = driver.findElement(By.xpath("//input[@name='tel-436']"));
		phoneField.sendKeys(String.valueOf(selectedPhone));

		js.executeScript("window.scrollTo(0, 350)");
		Thread.sleep(1000);

		WebElement subjectSelectElement = driver.findElement(By.name("your-subject"));
		Select subjectSelect = new Select(subjectSelectElement);
		subjectSelect.selectByVisibleText("inquiry");

		WebElement messageField = driver.findElement(By.xpath("//textarea[@name='your-message']"));
		messageField.sendKeys("This is a test inquiry.");

		js.executeScript("window.scrollTo(0, 450)");
		Thread.sleep(1000);

		WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
		checkbox.click();

		Thread.sleep(1000);

		WebElement submitBtn = driver.findElement(By.xpath("//input[@type='submit']"));
		submitBtn.click();

		WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(text(), 'Thank you') or contains(text(), 'شكراً')]")));
		Assert.assertTrue(confirmationMessage.isDisplayed(),
				"The confirmation message should be displayed after form submission");
	}

	// Go to Youtube of the website, choose video and open it
	@Test(priority = 10)
	public void newTab() throws InterruptedException {

		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(3000);

		driver.findElement(By.cssSelector(
				"body > div:nth-child(3) > footer:nth-child(4) > div:nth-child(1) > div:nth-child(1) > section:nth-child(3) > div:nth-child(1) > div:nth-child(4) > div:nth-child(1) > div:nth-child(5) > div:nth-child(1) > div:nth-child(1) > a:nth-child(3)"))
				.click();

		Set<String> handels = driver.getWindowHandles();
		List<String> windowList = new ArrayList<>(handels);

		driver.switchTo().window(windowList.get(1));
		Thread.sleep(2000);

		System.out.println(driver.getTitle());
		Thread.sleep(2000);

		WebElement videos = driver.findElement(By.linkText("Videos"));
		Thread.sleep(3000);

		videos.click();
		Thread.sleep(2000);

		List<WebElement> contentVid = driver.findElements(By.cssSelector(
				"ytd-rich-item-renderer:nth-child(1) div:nth-child(1) ytd-rich-grid-media:nth-child(1) div:nth-child(1) div:nth-child(1) ytd-thumbnail:nth-child(1) a:nth-child(1) yt-image:nth-child(1) img:nth-child(1)"));
		Thread.sleep(2000);
		WebElement randomContentVid = contentVid.get(random.nextInt(contentVid.size()));
		Thread.sleep(2000);

		randomContentVid.click();

		js.executeScript("window.scrollTo(0, 500)");
		Thread.sleep(2000);

	}
}