package com.bebo.config;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Random;

public class TestData {

    @DataProvider(name = "BoxData")
    public static Object[][] getDataFromDataProvider(Method testMethod){
        Object[][] testArray = null;
        if(testMethod.getName().equalsIgnoreCase("loginToBoxWithValidCredentials")){
            testArray = new Object[][]{
                    {"gilberto.aspros+001@gmail.com", "Test619"}
            };
        }else if(testMethod.getName().equalsIgnoreCase("faledTestScreenShot")){
            testArray = new Object[][]{
                    {"gilberto.aspros+1@gmail.com", "Test619"}
            };
        }else if(testMethod.getName().equalsIgnoreCase("createNewFolder")){
            testArray = new Object[][]{
                    {"folder_"+(new Random().nextInt(100)), "", "Editor"}
            };
        }else if(testMethod.getName().equalsIgnoreCase("renameCreatedFolder")){
            testArray = new Object[][]{
                    {"Renamed_Folder_"+(new Random().nextInt(100))}
            };
        } else {
            testArray = new Object[][]{
                    {"gilberto.aspros+001@gmail.com", "Test612"}
            };
        }
        return testArray;
    }
}
