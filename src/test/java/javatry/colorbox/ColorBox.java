package javatry.colorbox;

import java.util.List;

import javatry.colorbox.color.BoxColor;
import javatry.colorbox.size.BoxSize;
import javatry.colorbox.space.BoxSpace;

/**
 * 俗に言うカラーボックス。
 * @author jflute
 */
public interface ColorBox {

    BoxColor getColor();

    BoxSize getSize();

    int getSpaceCount();

    List<BoxSpace> getSpaceList();
}
