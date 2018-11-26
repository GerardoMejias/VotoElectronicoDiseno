package cr.ac.itcr.votoelectronico1;

import java.util.List;

//import javax.security.auth.callback.Callback;

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Gerardo on 13/6/2018.
 */
public interface PropuestaServicio {

    @GET("https://190.211.101.14:30962/WebApplication1/webresources")
    void getPropuesta(Callback<List<Propuesta>> callback);

    //@POST("")
    //Call<;


}
