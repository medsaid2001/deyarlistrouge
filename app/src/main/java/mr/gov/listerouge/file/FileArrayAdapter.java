package mr.gov.listerouge.file;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mr.gov.listerouge.R;


public class FileArrayAdapter extends ArrayAdapter<Option> {
    private Context c;
    private int id;
    private List<Option> items;

    public FileArrayAdapter(Context context, int i, List<Option> list) {
        super(context, i, list);
        this.c = context;
        this.id = i;
        this.items = list;
    }

    public Option getItem(int i) {
        return this.items.get(i);
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = ((LayoutInflater) this.c.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(this.id, (ViewGroup) null);
        }
        Option option = this.items.get(i);

        return view;
    }
}
