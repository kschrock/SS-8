package edu.iastate.coms309.project309.util;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

public class Const {
    public static final String DOMAIN =  "coms-309-ss-8.misc.iastate.edu:8080/";
    public static final String URL_REGISTER = "http://" + DOMAIN + "owners/add";
    public static final String URL_SHOW_USERS = "http://" + DOMAIN + "owners/";
    public static final String URL_LOGIN = "http://" + DOMAIN +"owners/login";
    public static final String URL_QR = "http://" + DOMAIN +"product/company/";
    public static final String URL_ADD_POINTS = "http://" + DOMAIN +"owners/addpoints";
    public static final String WS_EVENT_UPDATE = "ws://" + DOMAIN + "notify/";
    public static final String URL_EVENT_LIST = "http://" + DOMAIN + "events/";

    public static final String URL_SHOP = "http://coms-309-ss-8.misc.iastate.edu:8080/prize/getAll/Prizes";

    public static final String URL_REDEEM =  "http://" + DOMAIN + "redeem/";
    public static final String URL_COMPANY_LOGIN = "http://" + DOMAIN + "company/login/";
    public static final String URL_COMPANY_REGISTER = "http://" + DOMAIN + "company/add/";
    public static final String URL_ADD_PRIZE = "http://" + DOMAIN+"prize/addPrize/";
    public static final String URL_SUBSCRIBE = "http://" + DOMAIN + "owners/subscribe/";
    public static final String URL_COMPANIES = "http://" + DOMAIN + "company/getAll";
	
	public static final String PaypalClientCode="AbVR5V6IJR3_bo72_rbhN6L4hEre4Bgm5TO32KKghRhZV08zX3uNPN8XfVj5eqBzZjO9EMjuFjCRHpP3";

    public static String username = "";

	public static String password = "";
    public static String event = "";
    public static String company = "";
    public static int userID = 0;


    public static String companyForDiscount="";





}

