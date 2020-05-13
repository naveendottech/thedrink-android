package com.mjdistillers.drinkthedrink.others;

import androidx.lifecycle.MutableLiveData;

import com.mjdistillers.drinkthedrink.APIs;
import com.mjdistillers.drinkthedrink.model.request.GetUserUpdateProfileRequest;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetMultipartRequestToUpdateProfile {



    public void GetMultipartRequestToUpdateProfile(MutableLiveData<String> mldString,
                                              GetUserUpdateProfileRequest request) {

         final String id = "id";
         final String token = "token";
         final String name = "name";
         final String email = "email";
         final String phone = "phone";
         final String username = "username";
         final String role = "role";
         final String address = "address";
         final String city = "city";
         final String state = "state";
         final String country = "country";
         final String my_status = "my_status";
         final String work_at = "work_at";
         final String fav_spirit = "fav_spirit";
         final String fav_cocktail = "fav_cocktail";
         final String fav_drink = "fav_drink";
         final String visibility_status = "visibility_status";
         final String profile_image = "profile_image";

        Response response;
        try {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            client.readTimeout(1, TimeUnit.MINUTES);
            MediaType MEDIA_TYPE = null;

            MEDIA_TYPE = MediaType.parse("image/*");

            MultipartBody.Builder multipartBuilder = new MultipartBody.Builder();
            multipartBuilder.setType(MultipartBody.FORM);


            multipartBuilder.addFormDataPart(id, Integer.toString(request.getId()));
            multipartBuilder.addFormDataPart(token, request.getToken());
            multipartBuilder.addFormDataPart(name, request.getName());
            multipartBuilder.addFormDataPart(email, request.getEmail());
            multipartBuilder.addFormDataPart(phone, request.getPhone());
            multipartBuilder.addFormDataPart(username, request.getUsername());
            multipartBuilder.addFormDataPart(role, Integer.toString(request.getRole()));
            multipartBuilder.addFormDataPart(address, request.getAddress());
            multipartBuilder.addFormDataPart(city, request.getCity());
            multipartBuilder.addFormDataPart(state, request.getState());
            multipartBuilder.addFormDataPart(country, request.getCountry());
//            multipartBuilder.addFormDataPart(my_status,Integer.toString(request.getVisibiltiy_status()));
            multipartBuilder.addFormDataPart(work_at, Integer.toString(request.getWork_at()));
            multipartBuilder.addFormDataPart(fav_spirit, request.getFav_spirit());
            multipartBuilder.addFormDataPart(fav_cocktail, request.getFav_cocktail());
            multipartBuilder.addFormDataPart(fav_drink, request.getFavouriteDrink());
            multipartBuilder.addFormDataPart(visibility_status, Integer.toString(request.getVisibiltiy_status()));

            File file = new File(request.getFileToUpload());

            if(file != null) {
                multipartBuilder.addFormDataPart(profile_image, file.getName(), RequestBody.create(MEDIA_TYPE, file));
            }


            RequestBody requestBody = multipartBuilder.build();

            Request requestt = new Request.Builder()
                    .url(APIs.BASE_URL+ APIs.A_UPDATE_USER_PROFILE)
                    .post(requestBody)
                    .build();

            response = client.build().newCall(requestt).execute();

            mldString.postValue(response.body().string());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
