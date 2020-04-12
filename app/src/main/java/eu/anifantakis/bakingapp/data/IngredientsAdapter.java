package eu.anifantakis.bakingapp.data;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import eu.anifantakis.bakingapp.R;
import eu.anifantakis.bakingapp.data.model.Ingredient;
import eu.anifantakis.bakingapp.databinding.IngredientRowBinding;
import eu.anifantakis.bakingapp.utils.AppUtils;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder>{
    private List<Ingredient> ingredients;

    public IngredientsAdapter(List<Ingredient> ingredients){
        this.ingredients=ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IngredientRowBinding  binding = DataBindingUtil.inflate(inflater, R.layout.ingredient_row, parent, false);
        return new IngredientsAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.setTitle(ingredient.getIngredient());
        holder.setDose(ingredient.getDoseStr());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public Ingredient getIngredientAtIndex(int index) {
        return ingredients.get(index);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        IngredientRowBinding  binding;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            context = itemView.getContext();
        }
        void setTitle(String title){
            binding.tvIngredientName.setText(AppUtils.capitalizeFirstLetter(title));
        }

        void setDose(String dose){
            binding.tvIngredientDose.setText(dose);
        }
    }
}
