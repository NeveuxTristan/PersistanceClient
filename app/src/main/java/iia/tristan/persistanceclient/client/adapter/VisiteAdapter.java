package iia.tristan.persistanceclient.client.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import share.dataObject.Visite;

/**
 * @author Neveux_du_Geniebre on 08/04/2019.
 */
public class VisiteAdapter extends ArrayAdapter {

    private final Context context;
    private final Visite[] values;

    public VisiteAdapter(Context context, Visite[] values)
    {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

}