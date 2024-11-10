package mr.gov.listerouge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mr.gov.listerouge.R;
import mr.gov.listerouge.models.PersonDetailModel;
import mr.gov.listerouge.models.RedListItem;

public class PersonDetailsAdapter extends  RecyclerView.Adapter<PersonDetailsAdapter.ViewHolder> {

    private List<PersonDetailModel> itemList;

    public PersonDetailsAdapter(List<PersonDetailModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_details_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PersonDetailModel item = itemList.get(position);
        String nni = item.getNni();
        String rquestid = item.getRequestId();
        String nud = item.getNud();
        if (nni != null && !nni.isEmpty()) {
            holder.nniTextView.setText(String.format("NNI: %s", item.getNni()));
        }
       else if (rquestid != null && !rquestid.isEmpty()) {
            holder.request.setText(String.format("RquestID: %s", item.getRequestId()));
        }
        else{
            holder.nud.setText(String.format("NUD: %s", item.getNud()));
        }

        holder.descriptionTextView.setText(String.format("Description: %s", item.getDescription()));
        holder.conduitContactTextView.setText(String.format("Conduit Contact: %s", item.getConduitContact()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nniTextView, descriptionTextView, conduitContactTextView,nud,request;

        public ViewHolder(View itemView) {
            super(itemView);
            nniTextView = itemView.findViewById(R.id.nnilr);
            descriptionTextView = itemView.findViewById(R.id.description);
            conduitContactTextView = itemView.findViewById(R.id.conduitContact);
            nud  = itemView.findViewById(R.id.nud);
            request = itemView.findViewById(R.id.requestid);
        }
    }
}