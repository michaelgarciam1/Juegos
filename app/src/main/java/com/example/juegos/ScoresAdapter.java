package com.example.juegos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<Score> mScoresData;
    private Context mContext;

    /**
     * Constructor that passes in the scores data and the context.
     *
     * @param scoresData ArrayList containing the scores data.
     * @param context    Context of the application.
     */
    ScoresAdapter(Context context, ArrayList<Score> scoresData) {
        this.mScoresData = scoresData;
        this.mContext = context;
    }

    @Override
    public ScoresAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoresAdapter.ViewHolder holder, int position) {
        // Get current score.
        Score currentScore = mScoresData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentScore);
        // Assuming there is an image resource in the Score class, you can load it here.
        // Glide.with(mContext).load(currentScore.getImageResource()).into(holder.mSportsImage);
    }

    @Override
    public int getItemCount() {
        return mScoresData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mNamePlayerText;
        private TextView mScoreText;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            // Initialize the views.
            mNamePlayerText = itemView.findViewById(R.id.title);
            mScoreText = itemView.findViewById(R.id.subTitle);

        }

        void bindTo(Score currentScore) {
            // Populate the textviews with data.
            mNamePlayerText.setText(currentScore.getName());
            mScoreText.setText(currentScore.getScore());
        }

        @Override
        public void onClick(View view) {
            // Handle item click if needed.
        }
    }
}