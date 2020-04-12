package eu.anifantakis.bakingapp.data;

import java.util.List;

import eu.anifantakis.bakingapp.data.model.Recipe;
import eu.anifantakis.bakingapp.utils.AppUtils;
import retrofit2.Call;
import retrofit2.http.GET;


public interface RequestInterface {

    @GET(AppUtils.JSON_LOC)
    Call<List<Recipe>> getJSON();
}
