package javatry.colorbox.impl;

import javatry.colorbox.AbstractColorBox;
import javatry.colorbox.color.BoxColor;
import javatry.colorbox.size.BoxSize;
import javatry.colorbox.space.BoxSpace;

/**
 * @author jflute
 */
public class CompactColorBox extends AbstractColorBox {

    public CompactColorBox(BoxColor color, BoxSize spaceSize) {
        super(color);
        addSpace(new BoxSpace(spaceSize));
        addSpace(new BoxSpace(spaceSize));
    }

    public BoxSpace getUpperSpace() {
        return getSpaceList().get(1);
    }

    public BoxSpace getLowerSpace() {
        return getSpaceList().get(0);
    }
}
