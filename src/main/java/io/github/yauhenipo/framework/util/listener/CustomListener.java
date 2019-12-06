package io.github.yauhenipo.framework.util.listener;

import io.github.yauhenipo.framework.base.driver.Browser;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Log4j2
public class CustomListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult tr) {
        File file = Browser.getDriver().getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(file, new File(Paths.get("target", "screenshots", tr.getMethod().getMethodName() + ".png").toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.error(tr.getThrowable());
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        log.info(String.format("%s.%s is successed", tr.getTestClass().getName(), tr.getMethod().getMethodName()));
    }
}