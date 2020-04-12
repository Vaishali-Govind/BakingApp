package eu.anifantakis.bakingapp.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.Objects;

import eu.anifantakis.bakingapp.R;
import eu.anifantakis.bakingapp.activities.RecipeInfoActivity;
import eu.anifantakis.bakingapp.data.IngredientsAdapter;
import eu.anifantakis.bakingapp.data.StepsAdapter;
import eu.anifantakis.bakingapp.data.model.Recipe;
import eu.anifantakis.bakingapp.databinding.FragmentRecipeInfoBinding;

public class RecipeInfoFragment extends Fragment {

    private Recipe recipe;
    private StepsAdapter stepsAdapter;
    private final String CURRENT_STEP = "current_step";
    private final String CURRENT_TAB = "current_tab";

    private FragmentRecipeInfoBinding binding;
    OnStepClickListener Callback;

    public interface OnStepClickListener{
        void onStepSelected(int position);
    }

    public RecipeInfoFragment(){}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_info, container, false);
        final View rootView = binding.getRoot();

        if (recipe == null){
            recipe =((RecipeInfoActivity) Objects.requireNonNull(getActivity())).getRecipe();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rvIngredients.setLayoutManager(layoutManager);
        binding.rvIngredients.setHasFixedSize(true);


        LinearLayoutManager layoutManagerSteps = new LinearLayoutManager(getContext());
        binding.rvSteps.setLayoutManager(layoutManagerSteps);
        binding.rvSteps.setHasFixedSize(true);

        binding.tabHost.setup();
        addTab(getString(R.string.tab_title_ingredients), R.id.tab1);
        addTab(getString(R.string.tab_title_steps), R.id.tab2);

        initViews();

        // save the currently selected tab, so we can restore it in case of a phone rotation
        if (savedInstanceState != null) {
            binding.tabHost.setCurrentTab(savedInstanceState.getInt(CURRENT_TAB));

            stepsAdapter.PositionSelected = savedInstanceState.getInt(CURRENT_STEP);
            stepsAdapter.notifyDataSetChanged();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            Callback = (OnStepClickListener) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                " must implement OnStepClickListener");
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_TAB, binding.tabHost.getCurrentTab());
        outState.putInt(CURRENT_STEP, stepsAdapter.PositionSelected);
    }

    private void addTab(String tabName, int contentId) {
        TabHost.TabSpec spec = binding.tabHost.newTabSpec(tabName);
        spec.setContent(contentId);
        spec.setIndicator(tabName);
        binding.tabHost.addTab(spec);
    }
    private void initViews() {
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(recipe.getIngredients());
        binding.rvIngredients.setAdapter(ingredientsAdapter);

        stepsAdapter = new StepsAdapter(recipe.getSteps(), (int clickedItemIndex) -> {
            Callback.onStepSelected(clickedItemIndex);
        });
        binding.rvSteps.setAdapter(stepsAdapter);
    }


}