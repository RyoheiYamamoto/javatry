package javatry.colorbox.color;

/**
 * ボックスの色。単に色の名前を保持。
 * @author jflute
 */
public class BoxColor {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final String colorName;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BoxColor(String colorName) {
        this.colorName = colorName;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    public String toString() {
        return colorName;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public String getColorName() {
        return colorName;
    }
}
