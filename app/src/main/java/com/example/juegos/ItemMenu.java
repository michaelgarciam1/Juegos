package com.example.juegos;

import java.util.ArrayList;

public class ItemMenu {

        private String title;
        private Class activity;
        private final int imageResource;

        public ItemMenu(String title, int imageResource,Class activity) {
            this.title = title;
            this.imageResource = imageResource;
            this.activity = activity;
        }


        String getTitle() {
            return title;
        }

        public int getImageResource() {
            return imageResource;
        }

        public Class getActivity(){
            return activity;
        }

        public static ArrayList<ItemMenu> opciones(){
            ArrayList<ItemMenu> options= new ArrayList<ItemMenu>();
            options.add(new ItemMenu("2048", R.drawable.ic_2048,juego2048.class));
            options.add(new ItemMenu("Senku", R.drawable.ic_senku,Senku.class));
            options.add(new ItemMenu("Score", R.drawable.ic_score,ScoreActivity.class));
            options.add(new ItemMenu("Settings", R.drawable.ic_settings,Setting.class));

            return options;
    }
    }


