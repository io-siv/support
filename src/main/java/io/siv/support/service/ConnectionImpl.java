package io.siv.support.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.browserstack.local.Local;

class ConnectionImpl implements Connection {
	private final WebDriver driver;
	private final Local local;
	private final DesiredCapabilities capabilities;

	protected ConnectionImpl(WebDriver driver, Local local, DesiredCapabilities capabilities) {
		this.driver = driver;
		this.local = local;
		this.capabilities = capabilities;
	}

	@Override
	public WebDriver driver() {
		return driver;
	}

	@Override
	public String getCapability(String name) {
		return (String) capabilities.getCapability(name);
	}

	@Override
	public void disconnect() {
		driver.quit();
		if (null != local)
			try {
				local.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
