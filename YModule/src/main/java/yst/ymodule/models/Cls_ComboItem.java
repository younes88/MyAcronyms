package yst.ymodule.models;

import java.io.Serializable;


/**
 * <pre>
 *     author: Y.Saadat
 *     desc  :
 * </pre>
 */
public class Cls_ComboItem implements Serializable {
    private static final long serialVersionUID = 5132547736118053528L;
    public int ID;
    public String Text;

    public Cls_ComboItem() {
    }

    public Cls_ComboItem(int ID, String text) {

        this.ID = ID;
        Text = text;
    }
}
