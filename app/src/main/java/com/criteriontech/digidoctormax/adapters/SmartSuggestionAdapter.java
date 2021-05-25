package com.criteriontech.digidoctormax.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.criteriontech.digidoctormax.databinding.SmartSuggestViewBinding;
import com.criteriontech.digidoctormax.interfaces.SuggestionInterface;

import java.util.List;

public class SmartSuggestionAdapter extends RecyclerView.Adapter<SmartSuggestionAdapter.SuggestionVH> {
    List<String> modelList;
    SuggestionInterface suggestionInterface;

    public SmartSuggestionAdapter(List<String> modelList, SuggestionInterface suggestionInterface) {
        this.modelList = modelList;
        this.suggestionInterface = suggestionInterface;
    }

    @NonNull
    @Override
    public SmartSuggestionAdapter.SuggestionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        SmartSuggestViewBinding binding = SmartSuggestViewBinding.inflate(layoutInflater, parent, false);
        return new SuggestionVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SmartSuggestionAdapter.SuggestionVH holder, int position) {
        holder.binding.setChatSuggestion(modelList.get(position));
        holder.binding.getRoot().setOnClickListener(v -> suggestionInterface.onSuggestionItemClicked(modelList.get(position)));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class SuggestionVH extends RecyclerView.ViewHolder {
        SmartSuggestViewBinding binding;

        public SuggestionVH(@NonNull SmartSuggestViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
