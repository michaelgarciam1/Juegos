package com.example.juegos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/*
 * The adapter class for the RecyclerView, contains the sports data.
 */
class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    // Member variables.
    private ArrayList<ItemMenu> mMenuData;
    private Context mContext;
    private AppCompatActivity mActivity;
    private ImageView mMenuImage;


    MenuAdapter(Context context, ArrayList<ItemMenu> mMenuData, AppCompatActivity activity) {
        this.mMenuData = mMenuData;
        this.mContext = context;
        this.mActivity = activity;
    }

    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.menu_list, parent, false));
    }

    @Override
    public void onBindViewHolder(MenuAdapter.ViewHolder holder,
                                 int position) {
        // Get current item.
        ItemMenu currentItem = mMenuData.get(position);

        // Populate the textviews with data.
        Glide.with(mContext).load(currentItem.getImageResource()).into(mMenuImage);

        holder.bindTo(currentItem);

    }

    @Override
    public int getItemCount() {
        return mMenuData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Member Variables for the TextViews
        private TextView mTitleText;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {

            super(itemView);
            itemView.setOnClickListener(this);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.title);
            mMenuImage = itemView.findViewById(R.id.sportsImage);
        }

        void bindTo(ItemMenu currentSport) {
            mTitleText.setText(currentSport.getTitle());
        }

        @Override
        public void onClick(View v) {

            ItemMenu currentItem = mMenuData.get(getAdapterPosition());
            Intent intent = new Intent(mContext, currentItem.getActivity());
            mContext.startActivity(intent);
            mActivity.finish();
        }
    }
}