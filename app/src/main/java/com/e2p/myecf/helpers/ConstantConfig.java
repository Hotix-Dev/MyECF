package com.e2p.myecf.helpers;

import com.e2p.myecf.models.Client;
import com.e2p.myecf.models.DashItem;
import com.e2p.myecf.models.Statement;

import java.util.ArrayList;

public class ConstantConfig {
    /********************** *****************( Final )************************  *******************/

    //FINAL App Id
    public static final String FINAL_APP_ID = "1";

    /***************************************(Non Final )*******************************************/
    //BASE URL
    public static String BASE_URL = "http://41.228.164.76:91/MyECFAPI/";

    public static ArrayList<Statement> ALL_STATEMENTS = null;
    public static ArrayList<Client> ALL_CLIENTS = null;
    public static Client  SELECTED_CLIENT = null;

    public static String  AB_TITLE = "";

}

