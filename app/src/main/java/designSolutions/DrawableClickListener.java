package designSolutions;


public interface DrawableClickListener {
    enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}

    void onClick(DrawablePosition target);

}
