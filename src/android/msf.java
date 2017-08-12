package com.plugin.msf;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.msf.plugin.MsfService;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.cordova.PluginResult;
import android.content.Intent;
import android.app.Activity;
import android.provider.MediaStore;

/**
 * This class echoes a string called from JavaScript.
 */
public class msf extends CordovaPlugin {

    private CallbackContext callbackContext;

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;

       if(action.equals("init")) {
            AlibabaSDK.asyncInit(cordova.getActivity(), new InitResultCallback() {

            @Override
            public void onSuccess() {
//                Toast.makeText(cordova.getActivity(), "初始化成功", Toast.LENGTH_SHORT)
//                        .show();
            }
 
            @Override
            public void onFailure(int code, String message) {
//                Toast.makeText(cordova.getActivity(), code+message, Toast.LENGTH_SHORT)
//                        .show();
            }

        });
            return true;
        }else if(action.equals("sign")) {
            JSONObject jsonObj = args.getJSONObject(0);
            String outerId = jsonObj.getString("outerId");
            String orderIds = jsonObj.getString("orderIds");
			String tpid = jsonObj.getString("tpid");
			String serviceType = jsonObj.getString("serviceType");
			int requestCode = 9999;
			final MsfService myService = AlibabaSDK.getService(MsfService.class);
            myService.sign(this.cordova.getActivity(), outerId, orderIds, tpid, Integer.valueOf(serviceType), requestCode);
            cordova.setActivityResultCallback((CordovaPlugin)this);
			return true;
		}
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 9999){
                                String msg = data.getStringExtra("ResultToPartner");
                //JSONArray res = new JSONArray(msg);
                callbackContext.success(msg);
            }

        } else if (resultCode == Activity.RESULT_CANCELED && data != null) {

        } else if (resultCode == Activity.RESULT_CANCELED) {

        } else {

        }
    }
}
