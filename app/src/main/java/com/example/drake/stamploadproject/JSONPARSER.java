package com.example.drake.stamploadproject;

import com.example.drake.stamploadproject.Class.RedemptionCard;

import java.util.List;

public  class JSONPARSER {
   static List<RedemptionCard> redeemcardList;

   /* public static List<RedemptionCard> parseData(String content) {
        redeemcardList = new ArrayList();
        try {
            JSONObject jsonObject = new JSONObject(content);
            //create an json array
            for (int i = 0; i < jsonObject.length(); i++) {


                JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
                JSONObject data = result.getJSONObject(i);
                RedemptionCard redeem = new RedemptionCard(name, code, enddate, );

                redeem.setRewards(data.getString(Config.KEY_REWARDS));
                redeem.setEndDate(data.getString(Config.KEY_ENDDATE));
                redeem.setCodeNumber(data.getString(Config.KEY_CODE));
                redeem.setLogo(data.getString(Config.KEY_LOGO));
                redeem.setName(data.getString(Config.KEY_NAME));
                redeem.setQrcode(data.getString(Config.KEY_QR));

            }
           return redeemcardList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;

        }
    }
    */
}
