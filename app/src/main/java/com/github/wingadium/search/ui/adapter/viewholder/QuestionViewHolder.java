package com.github.wingadium.search.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.wingadium.search.R;
import com.github.wingadium.search.ui.adapter.models.QuestionModel;

public class QuestionViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTvQuest;
    private final TextView mTvAns;

    public QuestionViewHolder(View itemView) {
        super(itemView);

        mTvQuest = (TextView) itemView.findViewById(R.id.tvQuest);
        mTvAns = (TextView) itemView.findViewById(R.id.tvAns);
    }

    public void bind(QuestionModel model) {
        mTvQuest.setText(model.getQuestion());
        mTvAns.setText(model.getAnswer());
    }
}
