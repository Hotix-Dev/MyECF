package com.e2p.myecf.retrofit;

import com.e2p.myecf.models.Client;
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
    Call<ArrayList<Client> > getAllClientsQuery(@Url String URL);

    //Get All Statements service call
    @GET
    Call<ArrayList<Statement>> getAllStatementsQuery(@Url String URL,
                                                      @Query("cabinetId") String cabinetId,
                                                      @Query("clientId") String clientId,
                                                      @Query("exerciceDebut") String exerciceDebut,
                                                      @Query("exerciceFin") String exerciceFin);

    /***
     ** POST ********************************************************************************************
     **/

}
