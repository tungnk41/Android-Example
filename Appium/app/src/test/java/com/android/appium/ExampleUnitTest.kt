package com.android.appium

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.remote.MobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.time.Duration


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private var driver: WebDriver? = null

    @Before
    @Throws(MalformedURLException::class)
    fun before() {

        val capabilities = DesiredCapabilities()

        capabilities.setCapability("deviceName","emulator-5554")
        capabilities.setCapability("platformName", "Android")
        capabilities.setCapability("appPackage", "com.android.caltest")
        capabilities.setCapability("appActivity", "com.android.caltest.MainActivity")

//        capabilities.setCapability("browserName","Chrome")

        driver = AndroidDriver(URL("http://127.0.0.1:4723/wd/hub"), capabilities)
        driver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(10L))
    }

    @After
    fun end() {
        driver?.quit()
    }

    @Test
    fun testAndroidApp() {
        val edtFirst = driver?.findElement(By.id("edtFirst"))
        val edtSecond = driver?.findElement(By.id("edtSecond"))
        val tvResult = driver?.findElement(By.id("tvResult"))
        val btn = driver?.findElement(By.id("btnClick"))
        btn?.click()

        assertEquals(tvResult?.text.toString().toInt(), edtFirst?.text.toString().toInt() + edtSecond?.text.toString().toInt())
    }

    @Test
    fun testBrowser() {
        driver?.get("https://www.google.com")
    }
}

