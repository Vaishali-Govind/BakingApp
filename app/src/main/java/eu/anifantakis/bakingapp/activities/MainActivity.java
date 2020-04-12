package eu.anifantakis.bakingapp.activities;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;

import java.util.List;

import eu.anifantakis.bakingapp.R;
import eu.anifantakis.bakingapp.data.MainDataAdapter;
import eu.anifantakis.bakingapp.data.RequestInterface;
import eu.anifantakis.bakingapp.data.model.Recipe;
import eu.anifantakis.bakingapp.databinding.ActivityMainBinding;
import eu.anifantakis.bakingapp.utils.AppUtils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private ActivityMainBinding Bindings;
    private Context contexts;
    private MainDataAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bindings = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setTitle(getString(R.string.title_activity_main));

        AppUtils.setIdleResourceTo(false);

        contexts = this;
        GridLayoutManager LayoutManager = new GridLayoutManager(this, 2);
        Bindings.rvMaster.setLayoutManager(LayoutManager);

        // allow swipe to refresh
        Bindings.mainLayoutSwipe.setOnRefreshListener(this);

        loadJSON();
    }

    private void loadJSON() {
        Bindings.mainLayoutSwipe.setRefreshing(true);

        Retrofit retro= new Retrofit.Builder()
                .baseUrl(AppUtils.BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retro.create(RequestInterface.class);
        Call<List<Recipe>> calls = request.getJSON();

        Log.d("json calls", "retrofit calls");
        calls.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {

                Log.i("json response", "success!!!");
                List<Recipe> jsonResponse = response.body();

                mainAdapter = new MainDataAdapter(jsonResponse, clickedItemIndex -> {
                    Recipe recipe = mainAdapter.getRecipeAtIndex(clickedItemIndex);
                    Intent intent = new Intent(contexts, RecipeInfoActivity.class);
                    intent.putExtra(AppUtils.EXTRAS_RECIPE, recipe);
                    startActivityForResult(intent, 1);
                });
                Bindings.rvMaster.setAdapter(mainAdapter);
                Bindings.mainLayoutSwipe.setRefreshing(false);

                AppUtils.setIdleResourceTo(true);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.e("json response", "failed: " + t.getMessage());
                Bindings.mainLayoutSwipe.setRefreshing(false);

                // if we have a network error, prompt a dialog asking to retry or exit
                AlertDialog.Builder build = new AlertDialog.Builder(contexts);
                build.setMessage(R.string.no_main_network)
                        .setNegativeButton(R.string.no_main_network_try_again, (dialog, id) -> loadJSON())
                        .setPositiveButton(R.string.no_main_network_close, (dialog, id) -> finish());
                build.create().show();

                AppUtils.setIdleResourceTo(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadJSON();
    }
}
