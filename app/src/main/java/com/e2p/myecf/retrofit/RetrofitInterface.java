package com.e2p.myecf.retrofit;

import com.e2p.myecf.models.AnnualCharge;
import com.e2p.myecf.models.ChargeHandler;
import com.e2p.myecf.models.Client;
import com.e2p.myecf.models.ClientCharge;
import com.e2p.myecf.models.Statement;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;

import java.util.ArrayList;

public interface RetrofitInterface {

    /***
     ** GET *********************************************************************************************
     **/

    //Get All Clients service call
    @GET
    Call<ArrayList<Client>> getAllClientsQuery(@Url String URL);

    //Get Client By Code service call
    @GET
    Call<Client> getClientByCodeQuery(@Url String URL,
                                      @Query("Id") Integer Id,
                                      @Query("code") String code);

    //Get All Statements service call
    @GET
    Call<ArrayList<Statement>> getAllStatementsQuery(@Url String URL,
                                                     @Query("cabinetId") Integer cabinetId,
                                                     @Query("clientId") Integer clientId,
                                                     @Query("exerciceDebut") Integer exerciceDebut,
                                                     @Query("exerciceFin") Integer exerciceFin);

    //Get All Clients Charges Call
    @GET
    Call<ArrayList<AnnualCharge>> getAllClientsChargesQuery(@Url String URL,
                                                            @Query("cabinetId") Integer cabinetId,
                                                            @Query("exerciceId") Integer exerciceId);

    //Get All Annual Charges Details Call
    @GET
    Call<ArrayList<ClientCharge>> getAllAnnualChargesDetailsQuery(@Url String URL,
                                                                  @Query("cabinetId") Integer cabinetId,
                                                                  @Query("exerciceId") Integer exerciceId,
                                                                  @Query("clientId") Integer clientId);

    //Get Charges Dossier Call
    @GET
    Call<ArrayList<ChargeHandler>> getChargesDossierQuery(@Url String URL,
                                                          @Query("cabinetId") Integer cabinetId,
                                                          @Query("exerciceId") Integer exerciceId,
                                                          @Query("clientId") Integer clientId);

    /***
     ** POST ********************************************************************************************
     **/

}
