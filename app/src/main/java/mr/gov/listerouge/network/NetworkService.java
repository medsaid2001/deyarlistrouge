package mr.gov.listerouge.network;

import org.json.JSONObject;

import mr.gov.listerouge.api.ApiRedlistResponse;
import mr.gov.listerouge.api.ApiResponse;
import mr.gov.listerouge.models.AddDataRequest;
import mr.gov.listerouge.models.CreateList;
import mr.gov.listerouge.models.DeviceInfo;
import mr.gov.listerouge.models.OneResponse;
import mr.gov.listerouge.models.RedlistRequest;
import mr.gov.listerouge.models.VehiRequest;
import mr.gov.listerouge.models.VehicleDetailsResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Headers;


public interface NetworkService {
    @POST("partners/getListR")
    Call<ApiResponse> getItems(
            @Header("entity-Api-Key") String apikey,
            @Query("nni") String nni,
            @Body RedlistRequest redlist,
            @Header("app-name") String appname,
            @Header("app-version") String appversion,
            @Query("appId") String appId,
            @Query("page") int page,
            @Query("limit") int limit
    );

    @POST("partners/getListR")
    Call<ApiRedlistResponse> getItems2(
            @Header("entity-Api-Key") String apikey,
            @Query("nni") String nni,
            @Body RedlistRequest redlist,
            @Header("app-name") String appname,
            @Header("app-version") String appversion,
            @Query("appId") String appId,
            @Query("page") int page,
            @Query("limit") int limit
    );
    @POST("partners/createLr")
    Call<Void> createLr( @Body CreateList redlist,
                         @Header("entity-api-key") String apikey,
                         @Header("app-name") String appname,
                         @Header("app-version") String appversion,
                         @Query("nni") String nni,
                         @Query("appId") String appId ,
                         @Query("deviceSn") String deviceSn);
    @POST("partners/updateLr")
    Call<Void> updateLr( @Body CreateList redlist,
                         @Header("entity-api-key") String apikey,
                         @Header("app-name") String appname,
                         @Header("app-version") String appversion,
                         @Query("nni") String nni,
                         @Query("appId") String appId ,
                         @Query("deviceSn") String deviceSn,
                         @Query("id") String id
                         );
    @POST("partners/getOneLr")
    Call<OneResponse> getOneLr(@Body RedlistRequest redlist,
                               @Header("entity-Api-Key") String apikey,
                               @Header("app-name") String appname,
                               @Header("app-version") String appversion,
                               @Query("nni") String nni,
                               @Query("appId") String appId ,
                               @Query("id") String id);
    @POST("partners/supprimerLr")
    Call<Void> supprimerLr(@Body RedlistRequest redlist,
                               @Header("entity-Api-Key") String apikey,
                               @Header("app-name") String appname,
                               @Header("app-version") String appversion,
                               @Query("nni") String nni,
                               @Query("appId") String appId ,
                               @Query("id") String id);

    @POST("deyloul/getVehiculeDetails")
    Call<VehicleDetailsResponse> getVehiculeDetails(
            @Body VehiRequest redlist,
            @Header("api-Key") String apikey
    );
    @Headers({
            "entity-Api-Key: a96e90c5-d561-4a1d-8307-a22b8999cc9f",
            "Content-Type: application/json",
            "Accept: application/json",
            "app-name: deyar_redlist",
            "app-name: 4"
    })
    @POST("partners/checkAnrptsIdStatus2")
    Call<DeviceInfo> checkAnrptsIdStatus(@Body DeviceInfo deviceInfo);


    @Headers({
            "entity-Api-Key: a96e90c5-d561-4a1d-8307-a22b8999cc9f",
            "Accept: application/json",
            "app-name: deyar_redlist",
    })
    @POST("partners/getPersonSecDetails2")
    Call<String> getPersonDetails(
            @Query("nni") String nni,  // URL query parameter
            @Body JSONObject body      // JSON request body
    );

    @Headers({
            "entity-Api-Key: a96e90c5-d561-4a1d-8307-a22b8999cc9f",
            "Content-Type: application/json",
            "Accept: application/json",
            "app-name: deyar_redlist",
    })
    @POST("partners/identify2")
    Call<String> identify(@Body JSONObject body); // JSON body for POST request


    @POST("partners/checkAnrptsIdStatus2")
    Call<String> checkAnrptsIdStatus2(
            @Header("entity-Api-Key") String apikey,
            @Body RedlistRequest redlist,
            @Header("app-name") String appname,
            @Header("app-version") String appversion
    );

    @Headers({
            "entity-Api-Key: a96e90c5-d561-4a1d-8307-a22b8999cc9f",
            "Accept: application/json",
            "Content-Type: application/json",
            "app-name: deyar_redlist"
    })
    @POST("/partners/getPersonSecDetails2")
    Call<String> getPersonDetails2(@Query("nni") String nni, @Body JSONObject body);
}
