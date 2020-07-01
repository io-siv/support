package io.siv.support.service;

import org.openqa.selenium.WebDriver;

/**
 * Composing a WebDriver and Local when needed. Local is a badly named object
 * from BrowserStack which creates and manages a secure tunnel connection from
 * your local machine to BrowserStack. It is used in cases where your test
 * server sits behind a vpn.
 * 
 * Cleanup is important. Use your @After hook to call disconnect. The driver and
 * local be processed down as necessary.
 */
public interface Connection {
	public WebDriver driver();
	public void disconnect();
	public String getCapability(String name);
}
