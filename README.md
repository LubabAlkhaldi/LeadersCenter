# LeadersCenter Automated Tests 🛒🧪

**Leaders Center** is a Jordanian e-commerce website that offers a wide range of electronics, smart home devices, and household appliances.  
The site supports product filtering by category or brand, secure online payments, and customer support via a contact form and social media.

This project contains automated UI tests for [leaders.jo](https://leaders.jo) using **Selenium WebDriver** and **TestNG** with Java.  
It covers critical user flows including:

- 🔐 User registration and login  
- 📦 Product filtering and search  
- 🛒 Shopping cart operations  
- 💳 Checkout process  
- 📬 Submitting the contact form  
- ▶️ Navigating to the YouTube channel

The test suite uses explicit waits and JavaScript execution to handle dynamic elements, with assertions to validate expected outcomes.

---

## 🧰 Tools & Technologies Used

| Tool           | Purpose                            |
|----------------|------------------------------------|
| **Java**       | Main programming language          |
| **Selenium**   | Browser automation framework       |
| **TestNG**     | Test execution and organization    |
| **Maven**      | Build and dependency management    |
| **ChromeDriver** | WebDriver for Chrome browser     |
| **JavaScriptExecutor** | Handle dynamic UI actions |
| **Git**        | Version control                    |
| **GitHub**     | Hosting and collaboration          |

---

## ✅ Test Cases Summary

| Test Case ID  | Feature                  | Description                                             |
|---------------|--------------------------|---------------------------------------------------------|
| TC_LDR_001    | User Registration        | Register a new user and log out                         |
| TC_LDR_002    | User Login               | Login with registered credentials                       |
| TC_LDR_003    | Category Navigation      | Browse products by category (e.g., Electronics > Mobiles) |
| TC_LDR_004    | Brand Filtering          | Filter products by brand (e.g., Samsung)                |
| TC_LDR_005    | Product Search           | Search for products using keywords                      |
| TC_LDR_006    | Add to Cart              | Add available product to the shopping cart              |
| TC_LDR_007    | Cart Review              | Open and verify the contents of the cart                |
| TC_LDR_008    | Checkout Process         | Fill in billing details and proceed to checkout         |
| TC_LDR_009    | Contact Us Form          | Submit an inquiry through the contact form             |
| TC_LDR_010    | YouTube Channel Access   | Navigate to the YouTube channel and play a video        |
