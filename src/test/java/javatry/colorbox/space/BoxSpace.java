package javatry.colorbox.space;

import javatry.colorbox.size.BoxSize;

/**
 * @author jflute
 */
public class BoxSpace {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final BoxSize size;
    private Object contents;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public BoxSpace(BoxSize size) {
        this.size = size;
    }

    // ===================================================================================
    //                                                                      Basic Override
    //                                                                      ==============
    @Override
    public String toString() {
        return contents != null ? contents.toString() : "null";
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public BoxSize getSize() {
        return size;
    }

    public Object getContents() {
        return contents;
    }

    public Object addContents(Object contents) {
        Object removed = this.contents;
        this.contents = contents;
        return removed;
    }
}
