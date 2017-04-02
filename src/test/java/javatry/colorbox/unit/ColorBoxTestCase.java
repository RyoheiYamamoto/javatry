package javatry.colorbox.unit;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javatry.colorbox.ColorBox;
import javatry.colorbox.color.BoxColor;
import javatry.colorbox.impl.CompactColorBox;
import javatry.colorbox.impl.DoorColorBox;
import javatry.colorbox.impl.StandardColorBox;
import javatry.colorbox.size.BoxSize;
import javatry.unit.UnitryTestCase;

/**
 * @author jflute
 */
public abstract class ColorBoxTestCase extends UnitryTestCase {

    protected List<ColorBox> getColorBoxList() {
        List<ColorBox> colorBoxList = new ArrayList<ColorBox>();
        {
            StandardColorBox colorBox = new StandardColorBox(new BoxColor("green"), new BoxSize(40, 50, 30));
            colorBox.getUpperSpace().addContents("にーるずやーどれめでぃーず");
            colorBox.getMiddleSpace().addContents(null);
            colorBox.getLowerSpace().addContents(3);
            colorBoxList.add(colorBox);
        }
        {
            DoorColorBox colorBox = new DoorColorBox(new BoxColor("red"), new BoxSize(50, 30, 40));
            colorBox.getUpperSpace().addContents(926);
            colorBox.getMiddleSpace().addContents(604);
            colorBox.getLowerSpace().addContents("かまくらのいぬ");
            colorBoxList.add(colorBox);
        }
        {
            CompactColorBox colorBox = new CompactColorBox(new BoxColor("blue"), new BoxSize(50, 30, 40));
            colorBox.getUpperSpace().addContents(new File("/tmp/jflute.txt"));
            Map<String, Integer> map = new LinkedHashMap<String, Integer>();
            map.put("じゃがいろ", 198);
            map.put("みはねあいす", 390);
            map.put("こまつなびーがんなん", 480);
            colorBox.getLowerSpace().addContents(map);
            colorBoxList.add(colorBox);
        }
        {
            StandardColorBox colorBox = new StandardColorBox(new BoxColor("yellow"), new BoxSize(40, 50, 30));
            colorBox.getUpperSpace().addContents(toLocalDateTime("2012/06/04 23:59:59"));
            colorBox.getMiddleSpace().addContents(toLocalDate("2012/09/26"));
            List<BigDecimal> decimalList = new ArrayList<BigDecimal>();
            decimalList.add(new BigDecimal(2));
            decimalList.add(new BigDecimal("3.14159"));
            decimalList.add(new BigDecimal("4.753"));
            decimalList.add(new BigDecimal("2.0"));
            decimalList.add(new BigDecimal("1.4"));
            colorBox.getLowerSpace().addContents(decimalList);
            colorBoxList.add(colorBox);
        }
        return colorBoxList;
    }
}
