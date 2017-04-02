package javatry.colorbox.space;

import javatry.colorbox.size.BoxSize;

/**
 * @author jflute
 */
public class DoorBoxSpace extends BoxSpace {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private boolean open;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public DoorBoxSpace(BoxSize size) {
        super(size);
    }

    // ===================================================================================
    //                                                                          Open/Close
    //                                                                          ==========
    public boolean isOpen() {
        return open;
    }

    public void openTheDoor() {
        open = true;
    }
}
