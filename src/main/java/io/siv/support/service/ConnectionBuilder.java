package io.siv.support.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.browserstack.local.Local;

final class ConnectionBuilder {

	private final DesiredCapabilities capabilities;

	public ConnectionBuilder() {
		capabilities = new DesiredCapabilities();
	}
	
	public ConnectionBuilder withProperty(String key, String value) {
		capabilities.setCapability(key, value);
		return this;
	}
	
	public ConnectionBuilder withProperty(String key, boolean value) {
		capabilities.setCapability(key, value);
		return this;
	}

	public Connection build() {
		Local l = makeLocal();
		WebDriver driver = makeDriver();
		return new ConnectionImpl(driver, l, capabilities);
	}

	private String makeUrl() {
		return "https://" + property("studio.browserstack.user") + ":" + property("studio.browserstack.key") +  property("studio.browserstack.context");
	}

	private Local makeLocal() {
		Local l = null;

		if (isRemote()) {
			Map<String, String> options = new HashMap<String, String>();
			options.put("key", property("studio.browserstack.key"));
			options.put("localIdentifier", property("browserstack.localIdentifier"));
			options.put("forcelocal", property("studio.local.force"));//https://www.browserstack.com/local-testing/automate
			l = new Local();

			try {
				l.start(options);
			} catch (Exception e) {
				throw new RuntimeException("BrowserStack Local server failed to start", e);
			}
		}

		return l;
	}

	private WebDriver makeDriver() {
		return isRemote() ? makeRemote() : makeDomestic();
	}

	private WebDriver makeRemote() {
		String url = makeUrl();

		try {
			
			return new RemoteWebDriver(new URL(url), capabilities);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("url malformed as " + url, e);
		}
	}

	private WebDriver makeDomestic() {
		String domesticOs = property("studio.host.os");
		String extension = domesticOs.contains("mac") ? "" : ".exe";
		String b = property("browser").toLowerCase();

		switch (b) {
		case "chrome": {
			System.setProperty("webdriver.chrome.driver", property("studio.driver.path") + "chromedriver" + extension);
			return new ChromeDriver();
		}
		case "firefox": {
			System.setProperty("webdriver.gecko.driver", property("studio.driver.path") + "geckodriver" + extension);
			return new FirefoxDriver();
		}
		case "safari": {
			if (!domesticOs.contains("mac"))
				throw new RuntimeException(domesticOs + " Trying to use Safari? Buy a Mac.");
			return new SafariDriver();
		}
		default:
			throw new RuntimeException(b +
					" browser not supported. Supported browsers are [chrome, firefox, safari]");
		}
	}
	
	private String property(String p) {
		return (String) capabilities.getCapability(p);
	}

	private boolean isRemote() {
		return property("studio.profileId").contains("remote");
	}
}