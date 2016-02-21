package com.github.wingadium.search.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.wingadium.search.R;
import com.github.wingadium.search.ui.adapter.models.QuestionModel;
import com.github.wingadium.search.ui.adapter.viewholder.QuestionViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 24/05/15
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder> {

    private final LayoutInflater mInflater;
    private final List<QuestionModel> mModels;

    public QuestionAdapter(Context context, List<QuestionModel> models) {
        mInflater = LayoutInflater.from(context);
        mModels = new ArrayList<QuestionModel>(models);
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = mInflater.inflate(R.layout.question_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        final QuestionModel model = mModels.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return mModels.size();
    }

    public void animateTo(List<QuestionModel> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(List<QuestionModel> newModels) {
        for (int i = mModels.size() - 1; i >= 0; i--) {
            final QuestionModel model = mModels.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<QuestionModel> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final QuestionModel model = newModels.get(i);
            if (!mModels.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(List<QuestionModel> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final QuestionModel model = newModels.get(toPosition);
            final int fromPosition = mModels.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public QuestionModel removeItem(int position) {
        final QuestionModel model = mModels.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, QuestionModel model) {
        mModels.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final QuestionModel model = mModels.remove(fromPosition);
        mModels.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}
