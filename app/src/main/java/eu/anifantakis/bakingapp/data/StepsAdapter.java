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
import eu.anifantakis.bakingapp.data.model.Step;
import eu.anifantakis.bakingapp.databinding.StepRowBinding;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {
    public interface StepItemClickListener {
        void onStepItemClick(int clickedItemIndex);
    }

    final private StepItemClickListener OnItemClickListener;
    private List<Step> steps;
    public int PositionSelected = -1;

    public StepsAdapter(List<Step> steps, StepItemClickListener clickListener){
        this.steps=steps;
        this.OnItemClickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StepRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.step_row, parent, false);
        return new StepsAdapter.ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Step step = steps.get(position);

        String StepsNumber= "";
        if (position>0)
            StepsNumber= (position) + ". ";
        holder.setName(StepsNumber+ step.getShortDescription());

        holder.binding.idStepRow.setSelected((position==PositionSelected));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public Step getStepAtIndex(int index) {
        return steps.get(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        StepRowBinding  binding;
        private Context context;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setTag(3);
            binding = DataBindingUtil.bind(itemView);
            context = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        void setName(String name){
            binding.tvStepName.setText(name);
        }

        @Override
        public void onClick(View v) {
            PositionSelected = getAdapterPosition();
            OnItemClickListener.onStepItemClick(PositionSelected);
            notifyDataSetChanged();
        }
    }
}
