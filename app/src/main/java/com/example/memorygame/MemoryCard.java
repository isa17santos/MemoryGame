package com.example.memorygame;

public class MemoryCard {
    private int imageId;
    private int color = android.R.color.holo_red_light;
    private boolean isFlipped = false;
    private boolean isMatched = false;

    public MemoryCard(int imageId) {
        this.imageId = imageId;
    }

    public void flipCardUp() {
        if (isFlipped || isMatched){
            return;
        } else {
            isFlipped = true;
            color = android.R.color.transparent;
        }
    }

    public void flipCardDown() {
        if (!isFlipped || isMatched){
            return;
        } else {
            isFlipped = false;
            color = R.color.design_default_color_error;
        }
    }

    public int getColor() {
        return color;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getImageId() {
        if (isFlipped) {
            return imageId;
        }else{
            return R.drawable.baseline_question_mark_24;
        }
    }

    @Override
    public String toString() {
        return "MemoryCard{" +
                "imageId=" + imageId +
                ", color=" + color +
                ", isFlipped=" + isFlipped +
                ", isMatched=" + isMatched +
                '}';
    }
}
