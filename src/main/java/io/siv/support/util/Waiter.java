package io.siv.support.util;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiter {
	private static final long timeoutInSeconds = 4L;

	/**
	 * Adds an expected visibility wait condition to the element
	 * 
	 * @param driver The WebDriver
	 * @param element The WebElement
	 */
	public static void waitForElementVisibility(WebDriver driver, WebElement element) {
		wait(driver, ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Adds an expected click-able wait condition to the element
	 * 
	 * @param driver The WebDriver
	 * @param element The WebElement
	 */
	public static void waitForElementClickable(WebDriver driver, WebElement element) {
		wait(driver, ExpectedConditions.elementToBeClickable(element));
	}

	/**
	 * Adds an expected title wait condition.
	 * 
	 * @param driver The WebDriver
	 * @param title The title
	 */
	public static void waitForPageTitle(WebDriver driver, String title) {
		wait(driver, ExpectedConditions.titleIs(title));
	}

	/**
	 * Adds an expected text present wait condition to the element
	 * 
	 * @param driver The WebDriver
	 * @param element The WebElement
	 * @param text The text
	 */
	public static void waitFoTextToBePresentInElement(WebDriver driver, WebElement element, String text) {
		wait(driver, ExpectedConditions.textToBePresentInElement(element, text));
	}

	private static void wait(WebDriver driver, ExpectedCondition<?> condition) {
		try {
			new WebDriverWait(driver, timeoutInSeconds).until(condition);
		} catch (TimeoutException e) {
			System.out.println(condition + " timed out while waiting");
		}
	}
}
