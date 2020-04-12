package eu.anifantakis.bakingapp.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.anifantakis.bakingapp.R;
import eu.anifantakis.bakingapp.fragments.RecipeInfoDetailFragment;
import eu.anifantakis.bakingapp.data.model.Step;
import eu.anifantakis.bakingapp.utils.AppUtils;

public class RecipeInfoDetailActivity extends AppCompatActivity {

   
    private static final String FRAGMENT_KEY = "detail_fragment";
    private Fragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info_detail);

        // persistence: Save the state of the detail fragment so video is preserved after device rotation
        if (savedInstanceState==null){
            detailFragment = new RecipeInfoDetailFragment();
        }
        else{
            detailFragment = getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
        }

        Bundle bundle = new Bundle();
        Step step = getIntent().getParcelableExtra(AppUtils.EXTRAS_STEP);
        String recipeName = getIntent().getStringExtra(AppUtils.EXTRAS_RECIPE_NAME);
        bundle.putParcelable(AppUtils.EXTRAS_STEP, step);
        detailFragment.setArguments(bundle);

        ActionBar ab = getSupportActionBar();
        if (step.getId()>0)
            ab.setTitle(getString(R.string.step) + (step.getId()));
        else
            ab.setTitle(getString(R.string.recipe_introduction));
        ab.setSubtitle(recipeName);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.from_recipe_detail, detailFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, detailFragment);
    }
}
