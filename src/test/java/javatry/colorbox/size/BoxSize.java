package javatry.colorbox.size;

/**
 * ボックスのサイズ。高さや幅や奥行きを保持。
 * @author jflute
 */
public class BoxSize {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int height;
    private final int width;
    private final int depth;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BoxSize(int height, int width, int depth) {
        this.height = height;
        this.width = width;
        this.depth = depth;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    public String toString() {
        return height + ", " + width + ", " + depth;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }
}
