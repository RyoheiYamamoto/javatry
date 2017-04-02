package javatry.colorbox.impl;

import javatry.colorbox.color.BoxColor;
import javatry.colorbox.size.BoxSize;
import javatry.colorbox.space.BoxSpace;
import javatry.colorbox.space.DoorBoxSpace;

/**
 * @author jflute
 */
public class DoorColorBox extends StandardColorBox {

    public DoorColorBox(BoxColor color, BoxSize spaceSize) {
        super(color, spaceSize);
    }

    @Override
    protected BoxSpace createSpace(BoxSize spaceSize) {
        return new DoorBoxSpace(spaceSize);
    }
}
