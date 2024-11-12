package com.example.memorygame;

public class MemoryCard {
    private int cardviewId;
    private int imageId;
    private int color = android.R.color.holo_red_light;
    private boolean isFlipped = false;
    private boolean isMatched = false;

    public MemoryCard(int imageId) {
        this.imageId = imageId;
    }

    public void onClick() {
        isFlipped = !isFlipped;
        if (isFlipped) {
            color = android.R.color.transparent;
        }else{
            color = R.color.design_default_color_error;
        }
    }

    public int getColor() {
        return color;
    }

    public int getImageId() {
        if (isFlipped) {
            return imageId;
        }else{
            return R.drawable.baseline_question_mark_24;
        }
    }
}
