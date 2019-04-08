package iia.tristan.persistanceclient.client.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import iia.tristan.persistanceclient.client.item.VisiteItem;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class ManagerVisiteAdapter extends ArrayAdapter {

    private final Context context;
    private final VisiteItem[] values;

    public ManagerVisiteAdapter(Context context, VisiteItem[] values)
    {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }
}
